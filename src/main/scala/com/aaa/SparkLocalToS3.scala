package com.aaa

object SparkLocalToS3 extends App {
  SparkUtils.withSpark { sparkSession =>
    // Reading file from S3 directly without writing extra code to download files from S3
    val df = sparkSession.sqlContext.read
      .parquet("s3a://some-bucket-name/some-path/some-file.snappy.parquet")
    df.show()

    // Writing output to S3 directly without writing extra code to upload files
    df.write.csv("s3a://some-bucket-name/spark_local_to_s3/csv")
  }
}
