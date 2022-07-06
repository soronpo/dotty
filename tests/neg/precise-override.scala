import annotation.precise

object preciseOverrideSamePrecise:
  abstract class Foo:
    def id[@precise T](t: T) : T

  class Bar extends Foo:
    def id[@precise T](t: T): T = ???


object preciseOverrideMorePrecise:
  abstract class Foo:
    def id[T](t: T) : T

  class Bar extends Foo:
    def id[@precise T](t: T): T = ??? // error


object preciseOverrideLessPrecise:
  abstract class Foo:
    def id[@precise T](t: T) : T

  class Bar extends Foo:
    def id[T](t: T): T = ??? // error
