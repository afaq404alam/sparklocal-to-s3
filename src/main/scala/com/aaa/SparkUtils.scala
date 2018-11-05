package com.aaa

import com.amazonaws.auth.{
  AWSSessionCredentials,
  DefaultAWSCredentialsProviderChain
}
import org.apache.spark.sql.SparkSession

object SparkUtils {
  def withSpark[T](body: SparkSession => T): T = {
    // Launching spark in local mode
    val sparkSession = SparkSession.builder().master("local[*]").getOrCreate()

    try {
      val sc = sparkSession.sparkContext

      // In this example I have used session based AWS credentials
      val awsCredentials =
        new DefaultAWSCredentialsProviderChain().getCredentials
          .asInstanceOf[AWSSessionCredentials]

      //Passing AWS credentials to spark context
      sc.hadoopConfiguration
        .set("fs.s3a.access.key", awsCredentials.getAWSAccessKeyId)
      sc.hadoopConfiguration
        .set("fs.s3a.secret.key", awsCredentials.getAWSSecretKey)
      sc.hadoopConfiguration.set(
        "fs.s3a.session.token",
        awsCredentials.asInstanceOf[AWSSessionCredentials].getSessionToken)
      sc.hadoopConfiguration.set(
        "fs.s3a.aws.credentials.provider",
        "org.apache.hadoop.fs.s3a.TemporaryAWSCredentialsProvider")

      body(sparkSession)
    } finally {
      sparkSession.stop()
    }
  }
}
