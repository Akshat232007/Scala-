import breeze.linalg.{DenseVector, euclideanDistance}
import scala.io.Source

object Euvclidean {
  case class DataPoint(features: DenseVector[Double], label: String)

  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("src/main/Resources/column_2C.csv")
    val dataset = source.getLines().drop(1).map { line =>
      val cols = line.split(",")
      val features = DenseVector(cols.take(6).map(_.toDouble))
      DataPoint(features, cols(6))
    }.toList
    source.close()

    val newPointFeatures = DenseVector(50.0, 15.0, 35.0, 35.0, 110.0, 5.0)
    println(s"\nNew data point to classify: ${newPointFeatures}")

    var minDistance = Double.MaxValue
    var predictedLabel = ""

    for (point <- dataset) {
      val dist = euclideanDistance(newPointFeatures, point.features)

      if (dist < minDistance) {
        minDistance = dist
        predictedLabel = point.label
      }
    }

    println("\nClassification Result:")
    println(s" The nearest neighbor is at a distance of: $minDistance")
    println(s" The predicted label for the new point is: $predictedLabel")
  }
}