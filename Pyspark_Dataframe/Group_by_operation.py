from pyspark.sql import SparkSession
from pyspark.sql.functions import avg

spark = SparkSession.builder \
    .appName("F1GroupByAverage") \
    .master("local[*]") \
    .getOrCreate()

df = spark.read.csv(
    "Resources/F1_2025_RaceResults.csv",
    header=True,
    inferSchema=True
)

print("F1 Race Results:")
df.show()

result = df.groupBy("Team").agg(
    avg("Points").alias("Average_Points")
)

print("Average Points by Team:")
result.show()

spark.stop()