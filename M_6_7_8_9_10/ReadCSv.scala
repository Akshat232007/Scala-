import com.github.tototoshi.csv._
import java.io.File
import scala.util.Try
import scala.math._

object ReadCSV {

  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/student_performance_data.csv"))

    val allRows = reader.allWithHeaders()

    reader.close()

    if (allRows.isEmpty) {
      println("CSV file is empty.")
      return
    }

    val numericCols = allRows.head.keys.filter { col =>
      Try(allRows.head(col).trim.toDouble).isSuccess
    }.toList

    println("===== Student Dataset Statistics =====")
    println()

    println("Numeric Columns:")
    println(numericCols.mkString(", "))
    println()

    for (col <- numericCols) {

      val values = allRows.flatMap { row =>
        row.get(col).flatMap(v => Try(v.trim.toDouble).toOption)
      }

      if (values.nonEmpty) {

        val count = values.size
        val sum = values.sum
        val mean = sum / count
        val minimum = values.min
        val maximum = values.max
        val stdDev = sqrt(values.map(x => pow(x - mean, 2)).sum / count)

        println(s"Column : $col")
        println(s"Count  : $count")
        println(f"Mean   : $mean%.2f")
        println(f"Min    : $minimum%.2f")
        println(f"Max    : $maximum%.2f")
        println(f"StdDev : $stdDev%.2f")
        println("------------------------------------")
      }
    }
  }
}