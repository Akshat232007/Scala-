from pyspark.sql import SparkSession
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.classification import LogisticRegression
from pyspark.ml.evaluation import MulticlassClassificationEvaluator

spark = SparkSession.builder.appName("LoanPrediction").getOrCreate()

data = spark.read.csv("Resources/Loan Approval Prediction.csv", header=True, inferSchema=True)

features = data.columns
features.remove("loan_status")

assembler = VectorAssembler(
    inputCols=features,
    outputCol="features"
)
data = assembler.transform(data)
data = data.select("features", "loan_status")

train, test = data.randomSplit([0.8, 0.2], seed=42)

lr = LogisticRegression(
    featuresCol="features",
    labelCol="loan_status"
)

model = lr.fit(train)
predictions = model.transform(test)
predictions.select("loan_status", "prediction").show(10)

evaluator = MulticlassClassificationEvaluator(
    labelCol="loan_status",
    predictionCol="prediction",
    metricName="accuracy"
)

accuracy = evaluator.evaluate(predictions)

print("Accuracy:", accuracy)

spark.stop()