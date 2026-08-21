import breeze.linalg._
import breeze.stats.distributions.Rand
import scala.io.Source

object Kmeans {
  def euclideanDistance(a: DenseVector[Double], b: DenseVector[Double]): Double = {
    norm(a - b)
  }

  def main(args: Array[String]): Unit = {
    val lines = Source.fromFile("src\\main\\Resources\\plant_growth_data.csv").getLines().drop(1).toArray

    val data = DenseMatrix(lines.map { line =>
      val values = line.split(",")
      Array(
        values(1).toDouble,
        values(4).toDouble,
        values(5).toDouble
      )
    }: _*)

    val numFeatures = data.cols
    val k = 2
    val maxIterations = 100

    var centroids = DenseMatrix.zeros[Double](k, numFeatures)

    val randomIndices = Array(
      (Rand.uniform.get() * data.rows).toInt,
      (Rand.uniform.get() * data.rows).toInt
    )

    for (i <- 0 until k) {
      centroids(i, ::) := data(randomIndices(i), ::)
    }

    println(s"\nInitial centroids:\n$centroids")

    var assignments = DenseVector.zeros[Int](data.rows)
    var previousAssignments = DenseVector.zeros[Int](data.rows)
    var iteration = 0
    var converged = false

    while (iteration < maxIterations && !converged) {
      println(s"\n--- Iteration ${iteration + 1} ---")

      for (i <- 0 until data.rows) {
        val point = data(i, ::).t
        var minDistance = Double.MaxValue
        var closestCentroidIndex = -1

        for (j <- 0 until k) {
          val centroid = centroids(j, ::).t
          val dist = euclideanDistance(point, centroid)

          if (dist < minDistance) {
            minDistance = dist
            closestCentroidIndex = j
          }
        }

        assignments(i) = closestCentroidIndex
      }

      if (assignments == previousAssignments) {
        converged = true
      } else {
        previousAssignments = assignments.copy
      }

      val newCentroids = DenseMatrix.zeros[Double](k, numFeatures)
      val clusterCounts = DenseVector.zeros[Int](k)

      for (i <- 0 until data.rows) {
        val clusterId = assignments(i)
        newCentroids(clusterId, ::) := newCentroids(clusterId, ::) + data(i, ::)
        clusterCounts(clusterId) += 1
      }

      for (i <- 0 until k) {
        if (clusterCounts(i) > 0) {
          newCentroids(i, ::) := newCentroids(i, ::) / clusterCounts(i).toDouble
        }
      }

      centroids = newCentroids
      println(s"Updated centroids:\n$centroids")
      iteration += 1
    }

    println("\n--- Final Results ---")
    println(s"K-means algorithm converged in $iteration iterations.")
    println(s"Final centroids:\n$centroids")
    println(s"Final cluster assignments:\n$assignments")
  }
}