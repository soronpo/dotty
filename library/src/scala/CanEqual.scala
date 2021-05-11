package scala

import annotation.implicitNotFound
import scala.collection.{Seq, Set}

/** A marker trait indicating that values of type `L` can be compared to values of type `R`. */
@implicitNotFound("Values of types ${L} and ${R} cannot be compared with == or !=")
sealed trait CanEqual[-L, -R]

/** Companion object containing a few universally known `CanEqual` instances.
 *  CanEqual instances involving primitive types or the Null type are handled directly in
 *  the compiler (see Implicits.synthesizedCanEqual), so they are not included here.
 */
object CanEqual {
  /** A universal `CanEqual` instance. */
  object derived extends CanEqual[Any, Any]

  /** A fall-back instance to compare values of any types.
   *  Even though this method is not declared as given, the compiler will
   *  synthesize implicit arguments as solutions to `CanEqual[T, U]` queries if
   *  the rules of multiversal equality require it.
   */
  def canEqualAny[L, R]: CanEqual[L, R] = derived

  // Instances of `CanEqual` for common Java types
  given canEqualNumber: CanEqual[Number, Number] = derived
  given canEqualString: CanEqual[String, String] = derived

  // The next three definitions can go into the companion objects of classes
  // Seq and Set. For now they are here in order not to have to touch the
  // source code of these classes
  given canEqualSeq[T, U](using eq: CanEqual[T, U]): CanEqual[Seq[T], Seq[U]] = derived
  given canEqualSet[T, U](using eq: CanEqual[T, U]): CanEqual[Set[T], Set[U]] = derived
}
