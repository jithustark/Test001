package Test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.plans.logical.Distinct
import org.apache.spark.sql.functions._

object Health_Camp_Analysis extends App
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

  val camp_detail_df = spark.read.format("csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .load(Camp_detail)

  camp_detail_df.printSchema()
  camp_detail_df.show(2)


  /*
  Converting the Date
  val df = spark.sparkContext.parallelize(Seq("06 Jul 2018")).toDF("dateCol")
    df.withColumn("Date",to_date(unix_timestamp(df.col("dateCol"), "dd MMM yyyy").cast("timestamp"))).show()
   */




  val camp_detail_df1 = camp_detail_df
    .withColumn("Start_Date_Camp",to_date(unix_timestamp(col("Camp_Start_Date"), "dd-MMM-yy").cast("timestamp")))
    .withColumn("End_Date_Camp",to_date(unix_timestamp(col("Camp_End_Date"), "dd-MMM-yy").cast("timestamp")))
    .withColumn("No_of_Days",datediff(col("End_Date_Camp"), col("Start_Date_Camp")))
    .withColumn("Start_Month",date_format(to_date($"Start_Date_Camp", "yyyy-mm-dd"),"MM"))
    .withColumn("End_Month",date_format(to_date($"End_Date_Camp", "yyyy-mm-dd"),"MM"))
    .withColumn("Start_Year",date_format(to_date($"Start_Date_Camp", "yyyy-mm-dd"),"yyyy"))
    .withColumn("End_Year",date_format(to_date($"End_Date_Camp", "yyyy-mm-dd"),"yyyy"))
    .drop("Camp_Start_Date","Camp_End_Date", "Category1","Category2","Category3")


  camp_detail_df1.show(2)

  println("Unique Health Camp Id's")
  camp_detail_df1.agg(countDistinct("Health_Camp_ID")as("Unique_Health_Camp_Id")).show()
  /*
  |Unique_Health_Camp_Id|
+---------------------+
|                   65|

   */

println("Which health camp stayed Long, printing top 5")
  camp_detail_df1.groupBy("Health_Camp_ID").agg(sum("No_of_Days")as("Days")).orderBy($"Days".desc).show(5)
  /*
  Health_Camp_ID|Days|
    +--------------+----+
  |          6543| 771|
  |          6537| 771|
  |          6534| 751|
  |          6582| 580|
  |          6576| 445

   */

  println("Which month is most started, printing top 5")

  camp_detail_df1.groupBy("Start_Month").agg(count("Health_Camp_ID")as("No_of_camps")).orderBy($"No_of_camps".desc).show(5)

  println("Which month is most End, printing top 5")

  camp_detail_df1.groupBy("End_Month").agg(count("Health_Camp_ID")as("No_of_camps")).orderBy($"No_of_camps".desc).show(5)

  println("Which year is most started, printing top 5")

  camp_detail_df1.groupBy("Start_Year").agg(count("Health_Camp_ID")as("No_of_camps")).orderBy($"No_of_camps".desc).show(5)

  println("Which year is most End, printing top 5")

  camp_detail_df1.groupBy("End_Year").agg(count("Health_Camp_ID")as("No_of_camps")).orderBy($"No_of_camps".desc).show(5)


  /*
  Which month is most started, printing top 5
+-----------+-----------+
|Start_Month|No_of_camps|
+-----------+-----------+
|         08|         10|
|         11|          8|
|         09|          8|
|         01|          6|
|         02|          6|
+-----------+-----------+
only showing top 5 rows

Which month is most End, printing top 5
+---------+-----------+
|End_Month|No_of_camps|
+---------+-----------+
|       08|          9|
|       11|          8|
|       09|          8|
|       02|          6|
|       12|          5|
+---------+-----------+
only showing top 5 rows

Which year is most started, printing top 5
+----------+-----------+
|Start_Year|No_of_camps|
+----------+-----------+
|      2006|         21|
|      2005|         19|
|      2004|         16|
|      2003|          7|
|      2007|          2|
+----------+-----------+

Which year is most End, printing top 5
+--------+-----------+
|End_Year|No_of_camps|
+--------+-----------+
|    2005|         19|
|    2006|         18|
|    2004|         14|
|    2007|          9|
|    2003|          5|
+--------+-----------+


   */


}
