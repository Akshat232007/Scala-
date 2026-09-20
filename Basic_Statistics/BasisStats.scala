object BasicStats {
  def main(args: Array[String]): Unit = {
    val nums = List(4, 3 , 5, 7 , 1, 23, 23, 31, 54, 27, 42, 45 , 45 ,34)
    val mean = nums.sum.toDouble / nums.length
    val sorted = nums.sorted
    val mid = sorted.length / 2
    val median = if (sorted.length % 2 != 0) sorted(mid).toDouble
    else (sorted(mid - 1) + sorted(mid)) / 2.0
    val mode = nums.groupBy(identity).view.mapValues(_.size).maxBy(_._2)._1

    println(s"Dataset: $nums")
    println(f"Mean: $mean%.2f")
    println(f"Median: $median%.2f")
    println(s"Mode: $mode")
  }
}
