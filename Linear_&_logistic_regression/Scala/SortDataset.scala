import scala.io.Source

object SortDataset {
  def main(args: Array[String]): Unit = {
    val filePath = "src\\main\\Resources\\nba.csv"

    val source = Source.fromFile(filePath)
    val lines = source.getLines().toList
    source.close()

    val header = lines.head
    val dataRows = lines.tail

    val sortedTop5 = dataRows
      .map { line =>
        val columns = line.split(",", -1)
        val salaryStr = columns(8)
        val salary = if (salaryStr.nonEmpty) salaryStr.toDouble else 0.0
        (line, salary)
      }
      .sortBy { case (_, salary) => salary }(Ordering[Double].reverse)
      .take(5)

    println(header)
    sortedTop5.foreach { case (line, _) => println(line) }
  }
}
