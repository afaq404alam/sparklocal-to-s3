
val baseSettings = Seq(
  organization := "com.aaa",
  name := "sparklocal-to-s3",
  version := "0.1",
  scalaVersion := "2.11.12",
  resolvers += "Amazon".at("http://redshift-maven-repository.s3-website-us-east-1.amazonaws.com/release/")
)

val versions = new {
  val awssdk = "1.11.442"
  val hadoopAws = "2.8.0"
  val spark = "2.2.0"
}

lazy val root = project
  .in(file("."))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "org.apache.hadoop" % "hadoop-aws" % versions.hadoopAws,
      "org.apache.spark" %% "spark-sql" % versions.spark,
      "org.apache.spark" %% "spark-core" % versions.spark,
      "com.amazonaws" % "aws-java-sdk" % versions.awssdk
    )
  )