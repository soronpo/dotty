class T

transparent inline given fail1: T with // error
  val cs = scala.compiletime.summonAll[EmptyTuple]
transparent inline given fail2[X]: T with // error
  val cs = scala.compiletime.summonAll[EmptyTuple]
transparent inline given fail3(using DummyImplicit): T with // error
  val cs = scala.compiletime.summonAll[EmptyTuple]

transparent inline given ok1: T = new T:
  val cs = scala.compiletime.summonAll[EmptyTuple]
transparent inline given ok2[X]: T = new T:
  val cs = scala.compiletime.summonAll[EmptyTuple]
transparent inline given ok3(using DummyImplicit): T = new T:
  val cs = scala.compiletime.summonAll[EmptyTuple]
