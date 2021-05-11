---
layout: doc-page
title: "Dropped: Wildcard Initializer"
---

The syntax

```scala
  var x: A = _
```

that was used to indicate an uninitialized field, has been dropped.
At its place there is a special value `uninitialized` in the `scala.compiletime` package.
To get an uninitialized field, you now write

```scala
import scala.compiletime.uninitialized

var x: A = uninitialized
```

To enable cross-compilation, `_` is still supported, but it will be dropped in a future 3.x version.
