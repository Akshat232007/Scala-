import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object TemperatureHistogram {
  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src\\main\\Resources\\Indian_Climate_Dataset_2024_2025.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    //We use Histogram to display the distribution of data
    //An histogram group the temperature values into ranegs adn shows how many values fall in each
    //others raneg

    //here x axis represents te Average temperature ranegs
    //annd y axis shows frequencies which represents how many value fall into the ranges we haev

    val temperature = DenseVector(
      data.map(_("Temperature_Avg (°C)").toDouble).toArray
    )

    val f = Figure("Temperature Histogram")

    val p = f.subplot(0)

    // Bin means the number of groups or intervals in the histogram.
    // 5 means the data is divided into 5 groups.
    // 10 means the data is divided into 10 groups.
    // 15 means the data is divided into 15 groups.

    p += hist(temperature, 5)
    p.title = "Temperature Histogram - 5 Bins"
    p.xlabel = "Average Temperature (°C)"
    p.ylabel = "Frequency"

    f.refresh()

    val f2 = Figure("Temperature Histogram - 10 Bins")

    val p2 = f2.subplot(0)

    p2 += hist(temperature, 10)
    p2.title = "Temperature Histogram - 10 Bins"
    p2.xlabel = "Average Temperature (°C)"
    p2.ylabel = "Frequency"

    f2.refresh()

    val f3 = Figure("Temperature Histogram - 15 Bins")

    val p3 = f3.subplot(0)

    p3 += hist(temperature, 15)
    p3.title = "Temperature Histogram - 15 Bins"
    p3.xlabel = "Average Temperature (°C)"
    p3.ylabel = "Frequency"

    f3.refresh()
  }
}