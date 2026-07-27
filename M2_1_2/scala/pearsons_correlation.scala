import scala.io.Source
import scala.math

object PearsonCorrelation {

  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("src/main/Resources/vgsales.csv")
    val lines = bufferedSource.getLines().drop(1)

    var xList = List[Double]()
    var yList = List[Double]()

    for (line <- lines) {
      val cols = line.split(",")
      try {
        val naSales = cols(cols.length - 5).toDouble
        val euSales = cols(cols.length - 4).toDouble

        xList = naSales :: xList
        yList = euSales :: yList
      } catch {
        case _: Exception =>
      }
    }
    bufferedSource.close()

    val x = xList
    val y = yList
    val n = x.length.toDouble

    val (meanX, meanY) = (x.sum / n, y.sum / n)
    val num = x.zip(y).map { case (xi, yi) => (xi - meanX) * (yi - meanY) }.sum
    val den = math.sqrt(x.map(xi => math.pow(xi - meanX, 2)).sum * y.map(yi => math.pow(yi - meanY, 2)).sum)
    val r = if (den == 0) 0.0 else num / den

    val relation = if (r > 0.7) "Strong Positive" else if (r > 0) "Weak Positive" else "Negative"

    val df = n - 2
    val tStat = r * math.sqrt(df / (1.0 - math.pow(r, 2)))
    val isSignificant = math.abs(tStat) > 1.96

    println(f"Dataset Size: ${n.toInt}%d records")
    println(f"Pearson Correlation (r): $r%.4f ($relation Relationship)")
    println(f"t-Statistic: $tStat%.4f")
    println(s"Is Significant: $isSignificant")
  }
}
