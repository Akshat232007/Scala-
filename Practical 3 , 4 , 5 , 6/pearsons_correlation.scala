import scala.io.Source
import scala.math

object Pearson {

  def main(args: Array[String]): Unit = {
    val filePath = "src/main/Resources/vgsales.csv"
    val stream = getClass.getResourceAsStream("/vgsales.csv")

    val bufferedSource = if (stream != null) Source.fromInputStream(stream) else Source.fromFile(filePath)
    val lines = bufferedSource.getLines().drop(1)

    val colNames = List("NA_Sales", "EU_Sales", "JP_Sales", "Other_Sales", "Global_Sales")
    var dataRows = List[List[Double]]()

    for (line <- lines) {
      val cols = line.split(",")
      try {
        val row = List(
          cols(cols.length - 5).toDouble,
          cols(cols.length - 4).toDouble,
          cols(cols.length - 3).toDouble,
          cols(cols.length - 2).toDouble,
          cols(cols.length - 1).toDouble
        )
        dataRows = row :: dataRows
      } catch {
        case _: Exception =>
      }
    }
    bufferedSource.close()

    val n = dataRows.length.toDouble
    val numCols = colNames.length

    val columns = (0 until numCols).map(i => dataRows.map(row => row(i))).toList

    println(f"Dataset Size: ${n.toInt}%d records\n")
    println(f"${"Variable 1"}%-15s ${"Variable 2"}%-15s ${"Pearson r"}%-12s ${"t-Stat"}%-12s ${"Relationship"}%-20s ${"Significant?"}")
    println("-" * 90)

    for (i <- 0 until numCols) {
      for (j <- i + 1 until numCols) {
        val x = columns(i)
        val y = columns(j)

        val meanX = x.sum / n
        val meanY = y.sum / n

        val num = x.zip(y).map { case (xi, yi) => (xi - meanX) * (yi - meanY) }.sum
        val den = math.sqrt(x.map(xi => math.pow(xi - meanX, 2)).sum * y.map(yi => math.pow(yi - meanY, 2)).sum)

        val r = if (den == 0) 0.0 else num / den
        val df = n - 2
        val tStat = r * math.sqrt(df / (1.0 - math.pow(r, 2)))
        val isSignificant = math.abs(tStat) > 1.96

        val relation = if (r > 0.7) "Strong Positive" else if (r > 0.4) "Moderate Positive" else if (r > 0) "Weak Positive" else "Negative"

        println(f"${colNames(i)}%-15s ${colNames(j)}%-15s $r%-12.4f $tStat%-12.4f $relation%-20s $isSignificant")
      }
    }
  }
}