package com.aaa

object SparkLocalToS3 extends App {
  SparkUtils.withSpark { sparkSession =>
    // Reading file from S3 directly without writing extra code to download files from S3
    val df = sparkSession.sqlContext.read
      .parquet("s3a://some-bucket-name/some-in-path/parquet")
    df.show()

    // Writing output to S3 directly without writing extra code to upload files
    df.write.csv("s3a://some-bucket-name/some-out-path/csv")
  }
}
