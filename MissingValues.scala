import com.github.tototoshi.csv._
import java.io.File

object MissingValues {

  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(
      new File("src/main/scala/Data_mobile_usage_among_youngsters.csv")
    )
    val rows = reader.allWithHeaders()
    reader.close()

    if (rows.isEmpty) {
      println("Dataset is empty!")
      return
    }

    val columns = rows.head.keys.toList

    val columnMeans = columns.map { col =>

      val numericValues = rows.flatMap { row =>
        val value = row(col).trim

        if (value.isEmpty || value.equalsIgnoreCase("NA") || value.equalsIgnoreCase("null"))
          None
        else
          scala.util.Try(value.toDouble).toOption
      }

      val mean =
        if (numericValues.nonEmpty)
          numericValues.sum / numericValues.size
        else
          Double.NaN

      col -> mean

    }.toMap

    val updatedRows = rows.map { row =>
      row.map { case (col, value) =>

        val newValue =
          if ((value.trim.isEmpty ||
            value.equalsIgnoreCase("NA") ||
            value.equalsIgnoreCase("null")) &&
            !columnMeans(col).isNaN)
            "%.2f".format(columnMeans(col))
          else
            value

        col -> newValue
      }
    }

    val writer = CSVWriter.open(new File("Data_MissingValues_Handled.csv"))

    writer.writeRow(columns)

    updatedRows.foreach { row =>
      writer.writeRow(columns.map(col => row(col)))
    }

    writer.close()

    println("Missing values replaced successfully.")
    println("New file created: Data_MissingValues_Handled.csv")
  }
}