package Test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object Third_camp_analysis extends App
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


  val First_camp = "Health_Analysis_Data/First_Health_Camp_Attended.csv"
  val Second_camp = "Health_Analysis_Data/Second_Health_Camp_Attended.csv"
  val Third_camp = "Health_Analysis_Data/Third_Health_Camp_Attended.csv"
  val Camp_detail = "Health_Analysis_Data/Health_Camp_Detail.csv"
  val patient_profile = "Health_Analysis_Data/Patient_Profile.csv"


  val third_camp_df = spark.read.format("csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .load(Third_camp)

  third_camp_df.printSchema()

  third_camp_df.show(2)



  val third_camp_df1  =   third_camp_df
    //.withColumnRenamed("Health_Camp_ID","Third_Camp_ID")
    .withColumn("Camp_Name",lit("Third_Camp"))

  third_camp_df1.show(2)

  third_camp_df1.agg(countDistinct("Patient_ID")).show()

  third_camp_df1.agg(countDistinct("Health_Camp_ID")).show()

  third_camp_df1.agg(countDistinct("Last_Stall_Visited_Number")).show()

  println("Most Visited Stall")
  third_camp_df1.groupBy("Last_Stall_Visited_Number").count.orderBy($"count".desc).show()

  /*
  Last_Stall_Visited_Number|count|
+-------------------------+-----+
|                        1| 2497|
|                        2| 1290|
|                        3| 1234|
|                        4|  753|
|                        5|  472|
|                        6|  245|
|                        0|   18|
|                        7|    6|
+-------------------------+-----+

   */


println("Which patient visited most stalls ")

  // Grouping the data
//  val test = third_camp_df1.groupBy("Health_Camp_ID","Patient_ID").count().orderBy($"Health_Camp_ID".asc,$"count".desc)
//
//  test.show()
//
//  val w = Window.partitionBy("Health_Camp_ID").orderBy($"Health_Camp_ID".asc,$"count".desc)
//  val result = test.withColumn("index", row_number().over(w))
//  result.orderBy($"Health_Camp_ID".asc,$"count".desc).filter($"index"<= 2)//.show(false)

println("Which patient visited most stalls answer is one, likes all camps visited once ")






}
