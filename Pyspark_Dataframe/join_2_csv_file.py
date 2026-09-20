from pyspark.sql import SparkSession
import csv

spark = SparkSession.builder \
    .appName("JoinCSVFiles") \
    .master("local[*]") \
    .getOrCreate()

orders = spark.read.csv(
    "Resources/List of Orders.csv",
    header=True,
    inferSchema=True
)

details = spark.read.csv(
    "Resources/Order Details.csv",
    header=True,
    inferSchema=True
)

print("List of Orders:")
orders.show()

print("Order Details:")
details.show()

result = orders.join(
    details,
    orders["Order ID"] == details["Order ID"],
    "inner"
)

print("Joined Data:")
result.show()

# Save joined data using Python
data = result.collect()

with open("Resources/Joined_Orders.csv", "w", newline="") as file:
    writer = csv.writer(file)

    writer.writerow(result.columns)

    for row in data:
        writer.writerow(row)

print("Joined data saved successfully.")

spark.stop()