name := "Test001"

version := "0.1"

val catsVersion = "2.1.1"


scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.0",
  "org.apache.spark" %% "spark-sql" % "3.0.0",
  "org.apache.spark" %% "spark-mllib" % "3.0.0",
  "org.apache.spark" %% "spark-streaming" % "3.0.0",
  "org.twitter4j" % "twitter4j-core" % "4.0.4",
  "org.twitter4j" % "twitter4j-stream" % "4.0.4",
  "org.scalanlp" %% "breeze" % "1.0",
  "org.scalanlp" %% "breeze-natives" % "1.0",
  "com.crealytics" %% "spark-excel" % "0.13.7",
  "com.github.mrpowers" %% "spark-daria" % "1.0.0",
  "org.typelevel" %% "cats-core" % catsVersion


)

scalacOptions ++= Seq(
  "-language:higherKinds"
)