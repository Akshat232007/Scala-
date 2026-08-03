import scala.io.Source

object frequency_and_cummulative_frequency {
  def main(args: Array[String]): Unit = {

    val lines = Source.fromFile("src/main/Resources/StudentsPerformance.csv").getLines().drop(1).toList


    val data = lines.map(line => line.split(",")(0).replace("\"", ""))


    val frequencies = data.groupBy(identity).mapValues(_.size).toList.sortBy(_._1)


    var cumFreq = 0

    println("Category   / Frequency / Cumulative")

    for ((item, count) <- frequencies) {
      cumFreq += count
      println(f"$item%-10s | $count%-9d | $cumFreq%-10d")
    }
  }
}