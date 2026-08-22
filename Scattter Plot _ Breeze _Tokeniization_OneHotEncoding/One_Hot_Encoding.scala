import com.github.tototoshi.csv.*

import java.io.File
//Encoding the Penguin CSv File Based On their Species , Island and sex
//in new csv file
object OneHotEncodingAll {
  def main(args: Array[String]): Unit = {
    //Reading the csv file
    val reader = CSVReader.open(new File("src\\main\\Resources\\penguins_size.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    //Extracting the data Specifing column  to encode by taking distinct values and sorting it.
    //and match their value in csv file to give them an binary number repersentaion  1 or 0 where 1 means
    //data is whether the category is present in the penguin found
    //and 0 means the category is not present in the penguin
    // species , island and sex of penguins.

    val speciesCategories = data.map(row => row("species")).distinct.sorted
    val islandCategories = data.map(row => row("island")).distinct.sorted
    val sexCategories = data.map(row => row("sex")).distinct.sorted


    //Extraxt each row Data from CSv file
    val newData = data.map { row =>
      val currentSpecies = row("species")
      val currentIsland = row("island")
      val currentSex = row("sex")


      //Converting the csv fie text to encoding
      val speciesOneHot = speciesCategories.map { cat =>
        val newColName = "species_" + cat
        val isMatch = if (cat == currentSpecies) "1" else "0"
        newColName -> isMatch
      }.toMap

      val islandOneHot = islandCategories.map { cat =>
        val newColName = "island_" + cat
        val isMatch = if (cat == currentIsland) "1" else "0"
        newColName -> isMatch
      }.toMap

      val sexOneHot = sexCategories.map { cat =>
        val newColName = "sex_" + cat
        val isMatch = if (cat == currentSex) "1" else "0"
        newColName -> isMatch
      }.toMap

      val allOneHotColumns = speciesOneHot ++ islandOneHot ++ sexOneHot

      val rowWithoutOldColumns = row - "species" - "island" - "sex"
      val finalRow = rowWithoutOldColumns ++ allOneHotColumns

      finalRow
    }
      //Wrtiing Data into new CSv File named penguins_encoded_all.csv
    val headers = newData.head.keys.toList
    val writer = CSVWriter.open(new File("penguins_encoded_all.csv"))

    writer.writeRow(headers)

    newData.foreach { row =>
      val rowDataAsList = headers.map(headerName => row(headerName))
      writer.writeRow(rowDataAsList)
    }
//End of program
    writer.close()
    println("Success! Fully one-hot encoded file written to penguins_encoded_all.csv")
  }
}