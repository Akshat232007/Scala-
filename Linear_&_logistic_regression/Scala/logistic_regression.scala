import breeze.linalg._
import breeze.numerics._
import breeze.optimize.{DiffFunction, minimize}
import breeze.stats._

import scala.io.Source

object LogisticRegressionExample {
  def main(args: Array[String]): Unit = {

    val file = "src\\main\\Resources\\Loan Dataset.csv"
    val lines = Source.fromFile(file).getLines().drop(1).toArray

    val samples = lines.take(200)
    val numSamples = samples.length
    val numFeatures = 4

    val X_train_raw = DenseMatrix.zeros[Double](numSamples, numFeatures)
    val y_train = DenseVector.zeros[Double](numSamples)

    for (i <- samples.indices) {
      val row = samples(i).split(",").map(_.trim)
      X_train_raw(i, 0) = row(2).toDouble
      X_train_raw(i, 1) = row(10).toDouble
      X_train_raw(i, 2) = row(12).toDouble
      X_train_raw(i, 3) = row(25).toDouble
      y_train(i) = row(26).toDouble
    }

    val means = DenseVector.zeros[Double](numFeatures)
    val stdDevs = DenseVector.zeros[Double](numFeatures)
    val X_train = DenseMatrix.zeros[Double](numSamples, numFeatures)

    for (j <- 0 until numFeatures) {
      val column = X_train_raw(::, j)
      means(j) = mean(column)
      stdDevs(j) = stddev(column)

      if (stdDevs(j) != 0.0) {
        X_train(::, j) := (column - means(j)) / stdDevs(j)
      }
    }

    println("Generated dataset:")
    println(s" Number of samples: ${X_train.rows}")
    println(s" Number of features: ${X_train.cols}")


    val X_with_intercept = DenseMatrix.horzcat(
      DenseMatrix.ones[Double](X_train.rows, 1),
      X_train
    )


    val objective = new DiffFunction[DenseVector[Double]] {
      def calculate(w: DenseVector[Double]): (Double, DenseVector[Double]) = {

        val z = X_with_intercept * w

        val predictions = sigmoid(z)

        val safePreds = predictions.map(p => math.max(math.min(p, 0.999999), 0.000001))

        val loss = (y_train *:* log(safePreds)) + ((1.0 - y_train) *:* log(1.0 - safePreds))
        val totalCost = -sum(loss) / numSamples

        val errors = predictions - y_train
        val gradient = (X_with_intercept.t * errors) / numSamples.toDouble

        (totalCost, gradient)
      }
    }


    val initialWeights = DenseVector.zeros[Double](numFeatures + 1)
    println("\nTraining the model...")
    val trainedWeights = minimize(objective, initialWeights)

    println("\nTrained model weights (coefficients):")
    println(s" Intercept (w0): ${trainedWeights(0)}")
    println(s" Feature 1 weight (Age): ${trainedWeights(1)}")
    println(s" Feature 2 weight (Income): ${trainedWeights(2)}")
    println(s" Feature 3 weight (Credit): ${trainedWeights(3)}")
    println(s" Feature 4 weight (Risk): ${trainedWeights(4)}")


    val newPoint = DenseVector(
      1.0,
      (30.0 - means(0)) / stdDevs(0),
      (600000.0 - means(1)) / stdDevs(1),
      (750.0 - means(2)) / stdDevs(2),
      (0.20 - means(3)) / stdDevs(3)
    )

    val z_new = trainedWeights dot newPoint
    val probability = sigmoid(z_new)
    val predictedClass = if (probability >= 0.5) 1 else 0

    println(s"\nPredicting class for a new point at (Age 30, Inc 600k, Credit 750, Risk 0.20):")
    println(s" Predicted probability: $probability")
    println(s" Predicted class: $predictedClass")


    val newPoint2 = DenseVector(
      1.0,
      (25.0 - means(0)) / stdDevs(0),
      (250000.0 - means(1)) / stdDevs(1),
      (550.0 - means(2)) / stdDevs(2),
      (0.80 - means(3)) / stdDevs(3)
    )

    val z_new2 = trainedWeights dot newPoint2
    val probability2 = sigmoid(z_new2)
    val predictedClass2 = if (probability2 >= 0.5) 1 else 0

    println(s"\nPredicting class for a new point at (Age 25, Inc 250k, Credit 550, Risk 0.80):")
    println(s" Predicted probability: $probability2")
    println(s" Predicted class: $predictedClass2")
  }
}