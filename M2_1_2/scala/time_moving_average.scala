import scala.io.Source
import scala.math

object time_moving_average {

  def main(args: Array[String]): Unit = {
    val filePath = "src/main/Resources/Cleaned_NSUT.csv"
    val stream = getClass.getResourceAsStream("/Cleaned_NSUT.csv")

    val bufferedSource = if (stream != null) Source.fromInputStream(stream) else Source.fromFile(filePath)
    val lines = bufferedSource.getLines().drop(1)

    var aqiList = List[Double]()

    for (line <- lines) {
      val cols = line.split(",")
      try {
        val aqi = cols(11).toDouble
        aqiList = aqi :: aqiList
      } catch {
        case _: Exception =>
      }
    }
    bufferedSource.close()

    val data = aqiList.reverse
    val k = 7

    val sma = data.sliding(k).map(win => win.sum / k).toList

    val weights = (1 to k).toList
    val weightSum = weights.sum.toDouble
    val wma = data.sliding(k).map { win =>
      win.zip(weights).map { case (v, w) => v * w }.sum / weightSum
    }.toList

    val alpha = 2.0 / (k + 1)
    val initialEma = data.take(k).sum / k
    val ema = data.drop(k).foldLeft(List(initialEma)) { (acc, current) =>
      val nextEma = alpha * current + (1 - alpha) * acc.head
      nextEma :: acc
    }.reverse

    println(f"Dataset Total Records: ${data.length}")
    println(f"First calculated SMA ($k-day): ${sma.head}%.2f")
    println(f"First calculated WMA ($k-day): ${wma.head}%.2f")
    println(f"First calculated EMA ($k-day): ${ema.head}%.2f")
  }
}