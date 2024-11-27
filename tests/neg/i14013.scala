object Foo1 {
  case class Bar(i: Int)

  private implicit class BarOps(bar: Bar) {
    def twice = Bar(bar.i * 2)
  }
}

class Foo {
  def bar = Foo.Bar(1).twice // error
}

object App extends App {
  println((new Foo).bar)
}