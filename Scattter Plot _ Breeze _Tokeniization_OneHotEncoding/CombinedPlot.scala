import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CombinedPlot {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("src/main/Resources/Indias_Electricity_Consumption_Dataset.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val parsedData = data.flatMap { row =>
      try {
        val date = LocalDate.parse(row("Dates"), dateFormat)
        val consumption = row("Total Consumption").toDouble
        Some((date, consumption))
      } catch {
        case _: Throwable => None
      }
    }.sortBy(_._1)

    val x = DenseVector((0 until parsedData.length).map(_.toDouble).toArray)
    val y = DenseVector(parsedData.map(_._2).toArray)

    val fig = Figure("India Electricity Consumption - Line + Scatter Plot")
    val plt = fig.subplot(0)

    plt += plot(x, y, name = "Total Consumption Line", colorcode = "blue")
    plt += plot(x, y, '.', name = "Total Consumption Points", colorcode = "red")

    plt.xlabel = "Time (Days)"
    plt.ylabel = "Total Consumption"
    plt.title = "India Electricity Consumption - Line + Scatter : 2013 to 2024."

    fig.refresh()
  }
}