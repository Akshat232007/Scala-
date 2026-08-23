import breeze.linalg._
import scala.util.Random

object MatrixTranspose {
  def main(args: Array[String]): Unit = {
    val rows = 3
    val cols = 3

    val randData = Array.fill(rows * cols)(Random.nextInt(10) + 1)
    val randMatrix = new DenseMatrix(rows, cols, randData)

    println(s"Random Integer Matrix:\n$randMatrix")

    val transposed = randMatrix.t
    println(s"\nTransposed Matrix: \n$transposed")

    val doubleMatrix = randMatrix.map(_.toDouble)
    val determinant = det(doubleMatrix)

    println(f"\nDeterminant: $determinant%.2f")
  }
}