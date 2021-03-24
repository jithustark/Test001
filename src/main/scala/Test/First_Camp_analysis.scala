package Test
//
//import breeze.linalg.Matrix.castOps
//import breeze.numerics.round
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object First_Camp_analysis extends App
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


    val first_camp_df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(First_camp)

    first_camp_df.printSchema()

    first_camp_df.show(2)

  // First Camp data Modifications
    val first_camp_df1 = first_camp_df
      .withColumnRenamed("Health_Camp_ID","First_Camp_ID")
      .withColumnRenamed("Donation","First_Camp_Donation")
      .withColumnRenamed("Health_Score","First_Camp_Health_Score")
      .withColumn("First_Camp_Health_Score", round(col("First_Camp_Health_Score"),3))
      .withColumn("Camp_Name",lit("First_Camp"))
      .drop("_c4")

  first_camp_df1.show(2)

  first_camp_df1.agg(countDistinct("First_Camp_ID")).show()

println("most funded camp ID in First Camp")
  first_camp_df1.groupBy("First_Camp_ID").agg(sum("First_Camp_Donation")as("Donation_Amt")).orderBy($"Donation_Amt".desc).show(5)

  first_camp_df1.groupBy("Patient_ID").agg(sum("First_Camp_Donation")as("Donation_Amt")).orderBy($"Donation_Amt".desc).show(5)

  /*
  most funded camp ID in First Camp
+-------------+------------+
|First_Camp_ID|Donation_Amt|
+-------------+------------+
|         6543|       29970|
|         6538|       23270|
|         6586|       18410|
|         6537|       17610|
|         6542|       12080|
+-------------+------------+
only showing top 5 rows

+----------+------------+
|Patient_ID|Donation_Amt|
+----------+------------+
|    523092|         760|
|    508494|         730|
|    505115|         700|
|    509188|         580|
|    503827|         570|
+----------+------------+

   */

  println("Finding which patient is giving more to camp ID")
  // Grouping the data
  val test = first_camp_df1.groupBy("First_Camp_ID","Patient_ID").agg(sum("First_Camp_Donation")as("Donation_Amt")).orderBy($"First_Camp_Id".asc,$"Donation_Amt".desc)

  val w = Window.partitionBy("First_Camp_ID").orderBy($"First_Camp_ID".asc,$"Donation_Amt".desc)
  val result = test.withColumn("index", row_number().over(w))
  //result.orderBy($"First_Camp_ID".asc,$"Donation_Amt".desc).filter($"index"<= 1).show(50,false)


  first_camp_df1.filter($"Patient_ID" === 523092).show()


}
