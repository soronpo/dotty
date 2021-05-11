abstract class Lens[S, T] {
  def get(s: S): T
  def set(t: T, s: S) :S
}

import scala.quoted.*

object Lens {
  def apply[S, T](_get: S => T)(_set: T => S => S): Lens[S, T] = new Lens {
    def get(s: S): T = _get(s)
    def set(t: T, s: S): S = _set(t)(s)
  }

  def impl[S: Type, T: Type](getter: Expr[S => T])(using Quotes): Expr[Lens[S, T]] = {
    implicit val toolbox: scala.quoted.staging.Compiler = scala.quoted.staging.Compiler.make(this.getClass.getClassLoader)
    import quotes.reflect.*
    import util.*
    // obj.copy(field = value)
    def setterBody(obj: Expr[S], value: Expr[T], field: String): Expr[S] =
      Select.overloaded(obj.asTerm, "copy", Nil, NamedArg(field, value.asTerm) :: Nil, TypeBounds.empty).asExprOf[S]

    // exception: Term.of(getter).underlyingArgument
    getter.asTerm match {
      case Inlined(
        None, Nil,
        Block(
          DefDef(_, TermParamClause(param :: Nil) :: Nil, _, Some(Select(o, field))) :: Nil,
          Lambda(meth, _)
        )
      ) if o.symbol == param.symbol =>
        '{
          val setter = (t: T) => (s: S) => ${ setterBody('s, 't, field) }
          apply($getter)(setter)
        }
      case _ =>
        report.error("Unsupported syntax. Example: `GenLens[Address](_.streetNumber)`")
        '{???}
    }
  }
}

object GenLens {
  /** case class Address(streetNumber: Int, streetName: String)
   *
   *  GenLens[Address](_.streetNumber)   ~~>
   *
   *  Lens[Address, Int](_.streetNumber)(n => a => a.copy(streetNumber = n))
   */

  def apply[S] = new MkGenLens[S]
  class MkGenLens[S] {
    inline def apply[T](get: => (S => T)): Lens[S, T] = ${ Lens.impl('get) }
  }
}
