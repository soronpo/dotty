import annotation.precise

object preciseTypeAliasExtendSamePrecise:
  trait FooInv[@precise T]:
    val value: T
  class FooInvExtend[@precise A](val value : A) extends FooInv[A]
  type FooInvAlias[@precise A] = FooInv[A]

  trait FooCov[@precise +T]:
    val value: T
  class FooCovExtend[@precise +A](val value : A) extends FooCov[A]
  type FooCovAlias[@precise +A] = FooCov[A]

  trait FooCon[@precise -T]
  class FooConExtend[@precise -A] extends FooCon[A]
  type FooConAlias[@precise -A] = FooCon[A]


object preciseTypeAliasExtendMorePrecise:
  trait FooInv[T]:
    val value: T
  class FooInvExtend[@precise A](val value : A) extends FooInv[A]
  type FooInvAlias[@precise A] = FooInv[A] // error

  trait FooCov[+T]:
    val value: T
  class FooCovExtend[@precise +A](val value : A) extends FooCov[A]
  type FooCovAlias[@precise +A] = FooCov[A] // error

  trait FooCon[-T]
  class FooConExtend[@precise -A] extends FooCon[A]
  type FooConAlias[@precise -A] = FooCon[A] // error


object preciseTypeAliasExtendLessPrecise:
  trait FooInv[@precise T]:
    val value: T
  class FooInvExtend[A](val value : A) extends FooInv[A] // error
  type FooInvAlias[A] = FooInv[A] // error

  trait FooCov[@precise +T]:
    val value: T
  class FooCovExtend[+A](val value : A) extends FooCov[A] // error
  type FooCovAlias[+A] = FooCov[A] // error

  trait FooCon[@precise -T]
  class FooConExtend[-A] extends FooCon[A] // error
  type FooConAlias[-A] = FooCon[A] // error
