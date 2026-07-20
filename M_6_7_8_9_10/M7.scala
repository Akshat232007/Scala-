import breeze.linalg._

@main def matrixOperation(): Unit =
  val matA = DenseMatrix(
    (10.0, 20.0),
    (30.0, 40.0)
  )

  val matB = DenseMatrix(
    (2.0, 4.0),
    (5.0, 8.0)
  )

  val addition = matA + matB
  val subtraction = matA - matB
  val multiplication = matA * matB
  val division = matA / matB

  println(s"Matrix A:\n$matA\n")
  println(s"Matrix B:\n$matB\n")
  println(s"Addition (A + B):\n$addition\n")
  println(s"Subtraction (A - B):\n$subtraction\n")
  println(s"Element-wise Multiplication (A * B):\n$multiplication\n")
  println(s"Element-wise Division (A / B):\n$division")