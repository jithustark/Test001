package Test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.Imputer
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.mllib.stat._
import org.apache.spark.mllib.linalg._


object Patient_Analysis extends App
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


  val patient_profile = "Health_Analysis_Data/Patient_Profile.csv"

  val patient_df = spark.read.format("csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .load(patient_profile)

  // Changing the Data Types
  val patient_df1 = patient_df
    .withColumn("Education_Score",col("Education_Score").cast(IntegerType))
    .withColumn("Age",col("Age").cast(IntegerType))
    .withColumn("Income",col("Income").cast(IntegerType))

// Filling the Nan values of  "Education_Score","Age","Income" columns with mean

  val imputer = new Imputer()
    .setStrategy("mean")
    .setMissingValue(0)
    .setInputCols(Array("Education_Score","Age","Income"))
    .setOutputCols(Array("Education_Score","Age","Income"))

  val model = imputer.fit(patient_df1)
  val data = model.transform(patient_df1)
  data.printSchema()
  data.show(5)


// Reading the complete data of camps

  val camp_file = "Health_Analysis_Data/ModifiedData/Final_Camps_data.csv/part-00000-ba0f9778-c56c-45ac-bbb8-b3ce90d46e53-c000.csv"
  val camp_df = spark.read.format("csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .load(camp_file)

  val camp_df1 = camp_df.withColumnRenamed("Patient_ID","Camp_Patient_ID")
  camp_df1.show(2)

  println("Length of Data :",data.count())
  println("Length of Camp Data :",camp_df1.count())


  val final_data = data.join(camp_df1, data.col("Patient_ID") === camp_df1.col("Camp_Patient_ID"),"inner").drop("Camp_Patient_ID")

  println("Length of Final Data :",final_data.count())
  final_data.show(5)

  println("\n Based on age groups which best Health score are liked most")
  val interval = 20
  val final_data1 = final_data.withColumn("Age-range", $"Age" - ($"Age" % interval))
    .withColumn("Age-range", concat($"Age-range", lit(" - "), $"Age-range" + interval)) //optional one

  // Grouping the data
//  val test = final_data1.groupBy("Age-range").agg(round(sum("Health_Score"),2)as("Health Score")).orderBy($"Age-range".asc,$"Health Score".desc)
//
//  val w = Window.partitionBy("Age-range").orderBy($"Age-range".asc,$"Health Score".desc)
//  val result = test.withColumn("index", row_number().over(w))
//  result.orderBy($"Age-range".asc,$"Health Score".desc).filter($"index"<= 2).show(false)

  final_data1.groupBy("Age-range").agg(round(sum("Health_Score"),2)as("Health Score")).orderBy($"Health Score".desc).show()

println("Showing top internet used")
  val test = final_data1.groupBy("Camp_Name").agg(sum("LinkedIn_Shared")as("LinkedIn_Shared"),sum("Twitter_Shared")as("Twitter_Shared"),
    sum("Facebook_Shared")as("Facebook_Shared"))

  test.withColumn("Total_Online", col("LinkedIn_Shared") + col("Twitter_Shared") + col("Facebook_Shared")).orderBy($"Total_Online".desc).show()


  println("Showing top internet used Health Camp")
  val test1 = final_data1.groupBy("Camp_Name","Health_Camp_ID").agg(sum("LinkedIn_Shared")as("LinkedIn_Shared"),sum("Twitter_Shared")as("Twitter_Shared"),
    sum("Facebook_Shared")as("Facebook_Shared"))

  val test2 = test1.withColumn("Total_Online", col("LinkedIn_Shared") + col("Twitter_Shared") + col("Facebook_Shared")).orderBy($"Total_Online".desc)

  val w = Window.partitionBy("Camp_Name").orderBy($"Camp_Name".asc,$"Total_Online".desc)
  val result = test2.withColumn("index", row_number().over(w))
  result.orderBy($"Camp_Name".asc,$"Total_Online".desc).filter($"index"<= 2).show(50,false)

  /*
  Based on age groups which best Health score are liked most
+---------+------------+
|Age-range|Health Score|
+---------+------------+
|  40 - 60|     6312.48|
|  20 - 40|      651.83|
|  60 - 80|       587.3|
| 80 - 100|         0.1|
+---------+------------+

Showing top internet used
+-----------+---------------+--------------+---------------+------------+
|  Camp_Name|LinkedIn_Shared|Twitter_Shared|Facebook_Shared|Total_Online|
+-----------+---------------+--------------+---------------+------------+
| First_Camp|            871|           669|            672|        2212|
|Second_Camp|            517|           419|            421|        1357|
| Third_Camp|            406|           332|            339|        1077|
+-----------+---------------+--------------+---------------+------------+

+-----------+--------------+---------------+--------------+---------------+------------+-----+
|Camp_Name  |Health_Camp_ID|LinkedIn_Shared|Twitter_Shared|Facebook_Shared|Total_Online|index|
+-----------+--------------+---------------+--------------+---------------+------------+-----+
|First_Camp |6586          |94             |69            |70             |233         |1    |
|First_Camp |6538          |80             |63            |68             |211         |2    |
|Second_Camp|6534          |110            |98            |101            |309         |1    |
|Second_Camp|6529          |100            |83            |82             |265         |2    |
|Third_Camp |6527          |183            |148           |151            |482         |1    |
|Third_Camp |6578          |107            |87            |89             |283         |2    |
+-----------+--------------+---------------+--------------+---------------+------------+-----+

   */

  val dummy = final_data1.select("Health_Camp_ID", "LinkedIn_Shared").filter($"Health_Camp_ID" === 6586)
  //dummy.show()
  println(dummy.agg(sum("LinkedIn_Shared")).first.get(0))

}
