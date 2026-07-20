import com.github.tototoshi.csv._
import java.io.File

object Filter {
  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/Hospital_ER_Data.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val threshold = 60

    val filteredRows = data.filter { row =>
      row.get("Patient Age").exists(value =>
        value.nonEmpty && value.toInt > threshold
      )
    }

    println(s"\nTotal Rows with Patient Age > $threshold: ${filteredRows.length}\n")

    filteredRows.foreach { row =>
      println(row.values.mkString(", "))
    }
  }
}