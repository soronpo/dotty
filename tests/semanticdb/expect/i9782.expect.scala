// LazyRef
trait Txn/*<-_empty_::Txn#*/[T/*<-_empty_::Txn#[T]*/ <: Txn/*->_empty_::Txn#*/[T/*->_empty_::Txn#[T]*/]]

trait Elem/*<-_empty_::Elem#*/[T/*<-_empty_::Elem#[T]*/ <: Txn/*->_empty_::Txn#*/[T/*->_empty_::Elem#[T]*/]]

trait Obj/*<-_empty_::Obj#*/[T/*<-_empty_::Obj#[T]*/ <: Txn/*->_empty_::Txn#*/[T/*->_empty_::Obj#[T]*/]] extends Elem/*->_empty_::Elem#*/[T/*->_empty_::Obj#[T]*/]

trait Copy/*<-_empty_::Copy#*/[In/*<-_empty_::Copy#[In]*/ <: Txn/*->_empty_::Txn#*/[In/*->_empty_::Copy#[In]*/], Out/*<-_empty_::Copy#[Out]*/ <: Txn/*->_empty_::Txn#*/[Out/*->_empty_::Copy#[Out]*/]] {
  def copyImpl/*<-_empty_::Copy#copyImpl().*/[Repr/*<-_empty_::Copy#copyImpl().[Repr]*/[~/*<-_empty_::Copy#copyImpl().[Repr][`~`]*/ <: Txn/*->_empty_::Txn#*/[~/*->_empty_::Copy#copyImpl().[Repr][`~`]*/]] <: Elem/*->_empty_::Elem#*/[~/*->_empty_::Copy#copyImpl().[Repr][`~`]*/]](in/*<-_empty_::Copy#copyImpl().(in)*/: Repr/*->_empty_::Copy#copyImpl().[Repr]*/[In/*->_empty_::Copy#[In]*/]): Repr/*->_empty_::Copy#copyImpl().[Repr]*/[Out/*->_empty_::Copy#[Out]*/]

  def apply/*<-_empty_::Copy#apply().*/[Repr/*<-_empty_::Copy#apply().[Repr]*/[~/*<-_empty_::Copy#apply().[Repr][`~`]*/ <: Txn/*->_empty_::Txn#*/[~/*->_empty_::Copy#apply().[Repr][`~`]*/]] <: Elem/*->_empty_::Elem#*/[~/*->_empty_::Copy#apply().[Repr][`~`]*/]](in/*<-_empty_::Copy#apply().(in)*/: Repr/*->_empty_::Copy#apply().[Repr]*/[In/*->_empty_::Copy#[In]*/]): Repr/*->_empty_::Copy#apply().[Repr]*/[Out/*->_empty_::Copy#[Out]*/] = {
    val out/*<-local0*/ = copyImpl/*->_empty_::Copy#copyImpl().*/[Repr/*->_empty_::Copy#apply().[Repr]*/](in/*->_empty_::Copy#apply().(in)*/)
    (in/*->_empty_::Copy#apply().(in)*/, out/*->local0*/) match {
      case (inObj/*<-local1*/: Obj/*->_empty_::Obj#*/[In/*->_empty_::Copy#[In]*/], outObj/*<-local2*/: Obj/*->_empty_::Obj#*/[Out/*->_empty_::Copy#[Out]*/]) =>     // problem here
        println/*->scala::Predef.println(+1).*/("copy the attributes")
      case _ =>
    }
    out/*->local0*/
  }
}
