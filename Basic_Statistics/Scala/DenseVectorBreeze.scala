import breeze.linalg._
import scala.util.Random

object DenseVectorBreeze {
  def main(args: Array[String]): Unit = {
    val v1 = DenseVector.fill(5)(Random.nextInt(10).toDouble + 1.0)
    val v2 = DenseVector.fill(5)(Random.nextInt(10).toDouble + 1.0)

    println(s"Vector v1: $v1")
    println(s"Vector v2: $v2")

    val sum = breeze.linalg.sum(v1)
    println(f"Sum of v1: $sum%.2f")

    val mean = breeze.stats.mean(v1)
    println(f"Mean of v1: $mean%.2f")

    val dotProduct = v1 dot v2
    println(f"Dot Product of v1 and v2: $dotProduct%.2f")
  }
}