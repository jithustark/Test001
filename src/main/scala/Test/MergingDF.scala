package Test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._
import com.github.mrpowers.spark.daria.sql.DataFrameExt._

object MergingDF extends App {

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

  val first_camp_df = spark.read.format("csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .load(First_camp)



  // First Camp data Modifications
  val first_camp_df1 = first_camp_df
    //.withColumnRenamed("Health_Camp_ID","First_Camp_ID")
    //.withColumnRenamed("Donation","First_Camp_Donation")
    //.withColumnRenamed("Health_Score","First_Camp_Health_Score")
    .withColumn("Health_Score", round(col("Health_Score"),3))
    .withColumn("Camp_Name",lit("First_Camp"))
    .drop("_c4","Donation")

  first_camp_df1.printSchema()

   first_camp_df1.show(2)


    val second_camp_df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(Second_camp)




val second_camp_df1  =   second_camp_df
   // .withColumnRenamed("Health_Camp_ID","Second_Camp_ID")
    .withColumnRenamed("Health Score","Health_Score")
    .withColumn("Health_Score", round(col("Health_Score"),3))
    .withColumn("Camp_Name",lit("Second_Camp"))

  second_camp_df1.printSchema()

  second_camp_df1.show(2)



  val third_camp_df = spark.read.format("csv")
        .option("header", "true")
        .option("inferSchema", "true")
        .load(Third_camp)



  val third_camp_df1  =   third_camp_df
      //.withColumnRenamed("Health_Camp_ID","Third_Camp_ID")
      .withColumn("Camp_Name",lit("Third_Camp"))
    .withColumn("Health_Score",lit(0))
    .drop("Number_of_stall_visited", "Last_Stall_Visited_Number")

 val third_camp_df2 = third_camp_df1.reorderColumns(Seq("Patient_ID", "Health_Camp_Id","Health_Score","Camp_Name"))

  third_camp_df2.printSchema()
  third_camp_df2.show(2)

  println("Union data")
  val final_df = first_camp_df1.union(second_camp_df1).union(third_camp_df2).distinct()

  //final_df.show(final_df.count().toInt, false)

  final_df.show(10, false)

  final_df.coalesce(1)
    .write
    .mode(SaveMode.Overwrite)
    //.format("csv")
    .option("sep",",")
    .option("header","true")
    .csv("Health_Analysis_Data/ModifiedData/Final_Camps_data.csv")


  // Analysis

  println("Which camp is been most visited")

  println("CAMP Name - No.of Patients not distinct")
  final_df.groupBy("Camp_Name").agg(count("Patient_ID")as("No_of_Patients")).orderBy($"No_of_Patients".desc).show(5)

  println("CAMP Name - No.of Patients distinct")
  final_df.groupBy("Camp_Name").agg(countDistinct("Patient_ID")as("No_of_Patients")).orderBy($"No_of_Patients".desc).show(5)

  println("CampID - No.of Patients not distinct")

  final_df.groupBy("Health_Camp_ID").agg(count("Patient_ID")as("No_of_Patients")).orderBy($"No_of_Patients".desc).show(5)

  println("CAMP ID - No.of Patients distinct")
  final_df.groupBy("Health_Camp_ID").agg(countDistinct("Patient_ID")as("No_of_Patients")).orderBy($"No_of_Patients".desc).show(5)



  println(final_df.count())
}
