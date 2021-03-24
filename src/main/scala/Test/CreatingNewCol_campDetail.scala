package Test

import Data.DataConfig._
import breeze.linalg.InjectNumericOps
import com.sun.jmx.mbeanserver.Util.cast
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

import java.time.LocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CreatingNewCol_campDetail extends App {
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

//  camp_detail_df1.write
//    .format("csv")
//    .option("header", "true")
//    .option("inferSchema","true")
//    .mode("overwrite")
//    .save("Health_Analysis_Data/ModifiedData/Health_Camp_Detail.csv")

  camp_detail_df1.write.mode(SaveMode.Overwrite).csv("Health_Analysis_Data/ModifiedData/Health_Camp_Detail")




}