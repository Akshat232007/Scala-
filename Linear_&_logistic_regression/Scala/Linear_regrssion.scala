import breeze.linalg._
import scala.io.Source

object LinearRegressionExample {
  def main(args: Array[String]): Unit = {

    val file = "src\\main\\Resources\\historical_prices.csv"

    val data = Source.fromFile(file).getLines().drop(1).toVector

    val x = DenseVector(
      data.indices.map(i => (i + 1).toDouble).toArray
    )

    val y = DenseVector(
      data.map(row => row.split(",")(4).toDouble).toArray
    )

    println("Original data:")
    println(s" x: $x")
    println(s" y: $y")

    val ones = DenseVector.ones[Double](x.length)

    val X = DenseMatrix.horzcat(
      ones.asDenseMatrix.t,
      x.asDenseMatrix.t
    )

    val coefficients = X \ y

    println("\nLinear Regression Coefficients:")
    println(s" Intercept (c): ${coefficients(0)}")
    println(s" Slope (m): ${coefficients(1)}")

    val newX = x.length + 1

    val predictedY =
      coefficients(0) + coefficients(1) * newX

    println(s"\nPredicting TCS closing price for day $newX:")
    println(s" Predicted Closing Price: $predictedY")
  }
}