import breeze.linalg._

@main def matrixSlicing(): Unit =
  val myMatrix = DenseMatrix(
    (1.0, 2.0, 3.0),
    (4.0, 5.0, 6.0),
    (7.0, 8.0, 9.0)
  )

  println(s"Original Matrix:\n$myMatrix\n")

  val subMatrix = myMatrix(0 to 1, 1 to 2)
  println(s"Extracted Sub-Matrix:\n$subMatrix\n")

  val simpleRowSums = sum(subMatrix(*, ::))
  val simpleColSums = sum(subMatrix(::, *))

  println(s"Row sums: $simpleRowSums")
  println(s"Column sums: $simpleColSums")