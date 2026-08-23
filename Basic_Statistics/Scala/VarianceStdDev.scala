import scala.util.Random

object VarianceStdDev {
  def main(args: Array[String]): Unit = {
    val data = Seq.fill(10)(Random.nextInt(100).toDouble)
    val mean = data.sum / data.length
    val variance = data.map(x => math.pow(x - mean, 2)).sum / data.length
    val stdDev = math.sqrt(variance)

    println(s"Random Dataset: $data")
    println(f"Variance: $variance%.2f")
    println(f"Standard Deviation: $stdDev%.2f")
  }
}