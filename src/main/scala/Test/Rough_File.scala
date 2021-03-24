package Test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.Row

object Rough_File extends App
{
  // Set the log level to only print errors
  Logger.getLogger("org").setLevel(Level.ERROR)


  // Create a SparkSession using every core of the local machine, named RatingsCounter
  val spark = SparkSession
    .builder
    .appName("Test1")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._


  val data = Seq(
    Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
    Vectors.dense(4.0, 5.0, 0.0, 3.0),
    Vectors.dense(6.0, 7.0, 0.0, 8.0),
    Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
  )

  val df = data.map(Tuple1.apply).toDF("features")
  df.show()
  val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
  println(s"Pearson correlation matrix:\n $coeff1")

  val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
  println(s"Spearman correlation matrix:\n $coeff2")


}
