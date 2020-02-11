import scala.compiletime.ops._
import scala.annotation.infix
import scala.language.implicitConversions

object twoface {
  protected object std {
    type Int = scala.Int
    type Boolean = scala.Boolean
    type String = java.lang.String
  }
  export TwoFace.Int.extensions._
  export TwoFace.Boolean.extensions._
  object TwoFace {
    opaque type Int[T <: std.Int] = std.Int
    object Int {
      //constructors
      protected[TwoFace] def create[T <: std.Int](value : std.Int) : Int[T] = value
      def apply[T <: std.Int & Singleton](value : T) : Int[T] = create[T](value)
      //conversions
      given fromValue[T <: std.Int & Singleton] as Conversion[T, Int[T]] = t => create[T](t)
      given fromWideValue(using DummyImplicit) as Conversion[std.Int, Int[std.Int]] = t => create[std.Int](t)
      given toValue[T <: std.Int](using ValueOf[T]) as Conversion[Int[T], T] = _ => valueOf[T]
      given toWideValue[T <: std.Int] as Conversion[Int[T], std.Int] = _.asInstanceOf[std.Int]
      given toWideTwoFace[T <: std.Int] as Conversion[Int[T], Int[std.Int]] = t => create[std.Int](t)
      //operations
      object extensions {
        def [L <: std.Int, R <: std.Int](left : Int[L]) + (right : Int[R]) : Int[int.+[L, R]] = Int.create[int.+[L, R]](left + right)
        def [L <: std.Int, R <: std.Int](left : Int[L]) - (right : Int[R]) : Int[int.-[L, R]] = Int.create[int.-[L, R]](left - right)
        def [L <: std.Int, R <: std.Int](left : Int[L]) === (right : Int[R]) : Boolean[any.==[L, R]] = Boolean.create[any.==[L, R]](left == right)
      }
    }

    opaque type Boolean[T <: std.Boolean] = std.Boolean
    object Boolean {
      //constructors
      protected[TwoFace] def create[T <: std.Boolean](value : std.Boolean) : Boolean[T] = value
      def apply[T <: std.Boolean & Singleton](value : T) : Boolean[T] = create[T](value)
      //conversions
      given fromValue[T <: std.Boolean & Singleton] as Conversion[T, Boolean[T]] = t => create[T](t)
      given fromWideValue(using DummyImplicit) as Conversion[std.Boolean, Boolean[std.Boolean]] = t => create[std.Boolean](t)
      given toValue[T <: std.Boolean](using ValueOf[T]) as Conversion[Boolean[T], T] = _ => valueOf[T]
      given toWideValue[T <: std.Boolean] as Conversion[Boolean[T], std.Boolean] = _.asInstanceOf[std.Boolean]
      given toWideTwoFace[T <: std.Boolean] as Conversion[Boolean[T], Boolean[std.Boolean]] = t => create[std.Boolean](t)
      //operations
      object extensions {
        def [L <: std.Boolean, R <: std.Boolean](left : Boolean[L]) === (right : Boolean[R]) : Boolean[any.==[L, R]] = Boolean.create[any.==[L, R]](left == right)
      }
    }
  }

  object Checked {
    opaque type Int[T <: std.Int] = std.Int
  }
}

import twoface._

val oneCT = TwoFace.Int(1)
val one : Int = 1
val oneRT :  TwoFace.Int[Int] = TwoFace.Int(one)

val b : TwoFace.Boolean[true] = oneCT === oneCT

val twoCT : TwoFace.Int[2] = oneCT + oneCT
val a = TwoFace.Int(1) + 1
val twoCT2 : TwoFace.Int[2] = a
val test1 : 1 = oneCT
val testOne : Int = oneRT
val twoRT1 = oneCT + oneRT
val twoRT2 = oneRT + oneCT
val twoRT3 = oneRT + oneRT

def fromInt[T <: Int](t : TwoFace.Int[T]) : TwoFace.Int[T] = t

val tf1 : TwoFace.Int[1] = fromInt(1)
val tfOne : TwoFace.Int[Int] = fromInt(one)


object Test {
  class Vec[S <: Int](val size : TwoFace.Int[S]) {
    @infix def concat [RS <: Int](that : Vec[RS]) = new Vec(this.size + that.size)
    // @infix def == [RS <: Int](that : Vec[RS]) = this.size === that.size
  }

  val one : Int = 1
  val v1 : Vec[1] = new Vec(1)
  val v2 : Vec[2] = new Vec(2)
  val v3 : Vec[3] = v1 concat v2
  val v3a = v1 concat v2
  val v3b : Vec[3] = v3a
  // val b1 : true = v1 === v1

  val vOne : Vec[Int] = new Vec(one)
  val vecs = for(i <- 1 to 3) yield (new Vec(i) concat v1)
}

