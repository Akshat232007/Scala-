import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._

import java.io.File
import java.time.LocalDateTime

object IndianClimateLineGraph {
  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src\\main\\Resources\\open-meteo-19.09N72.85E8m.csv"))
    val allRows = reader.all()
    reader.close()

    val dataRows = allRows.drop(4).filter(_.length >= 2)

    val parsedData = dataRows.map { row =>
      val dt = LocalDateTime.parse(row(0))
      val temp = row(1).toDouble
      (dt, temp)
    }.filter { case (dt, _) =>
      dt.getYear == 2025
    }

    val dailyAverages = parsedData
      .groupBy { case (dt, _) => dt.getDayOfYear }
      .map { case (day, dailyRecords) =>
        val avgTemp = dailyRecords.map(_._2).sum / dailyRecords.size
        (day, avgTemp)
      }
      .toSeq
      .sortBy(_._1)

    val dates = DenseVector(
      dailyAverages.map(_._1.toDouble).toArray
    )

    val temperatures = DenseVector(
      dailyAverages.map(_._2).toArray
    )

    val f = Figure("Indian Climate Temperature Trend 2025")
    val p = f.subplot(0)

    p += plot(dates, temperatures)
    p.title = "Average Daily Temperature Trend (2025)"
    p.xlabel = "Day of the Year (1 to 365)"
    p.ylabel = "Average Temperature (°C)"

    f.refresh()
  }
}