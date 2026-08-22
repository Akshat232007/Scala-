import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object IndianTempandAQIScattterPlot {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("src\\main\\Resources\\Indian_Climate_Dataset_2024_2025.csv"))
    //Reading only first 500 Data entries present in the Dataset
    val data = reader.allWithHeaders().take(500)
    reader.close()
    //In Scatter Plot we make a relationship between 2 variables.
    //In which one is an independent and other one is an dependent variable.
    //Taking Temperature because Independent because its independent in the following dataset.
    //Taking AQI as dependent variable  because AQI changes due to temperature.

    val x = DenseVector(data.map(_("Temperature_Avg (°C)").toDouble).toArray)
    val y = DenseVector(data.map(_("AQI").toDouble).toArray)

    val fig = Figure()
    val plt = fig.subplot(0)

    plt.title = "Temperature_Avg (°C) vs AQI"
    plt.xlabel = "Temperature_Avg (°C)"
    plt.ylabel = "AQI"

    plt += plot(x, y, '.', name = "Indian Temperature and AQI Proportionality", colorcode = "black")

    fig.refresh()
  }
}