object test {
  def foo(s: Int*): Unit = {
    s.toList match {
      case t: List[Int] => foo(t*)
      //case _ =>  // unreachable code
    }
  }
}
