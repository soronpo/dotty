package tests

package snippetTestcase3

class SnippetTestcase3:
  /** Text on line 0.
   *
   * SNIPPET(OUTERLINEOFFSET:10,OUTERCOLUMNOFFSET:6,INNERLINEOFFSET:4,INNERCOLUMNOFFSET:2)
   * ERROR(LINE:11,COLUMN:8)
   * ```scala sc:fail
   * 2 + List()
   * ```
   */
  def a = 3
  /**
   * Text on line 1.
   *
   * SNIPPET(OUTERLINEOFFSET:20,OUTERCOLUMNOFFSET:6,INNERLINEOFFSET:4,INNERCOLUMNOFFSET:2)
   * ERROR(LINE:21,COLUMN:8)
   * ```scala sc:fail
   * 2 + List()
   * ```
   */
  def b = 3
  /**
   *
   * Text on line 2.
   *
   * SNIPPET(OUTERLINEOFFSET:31,OUTERCOLUMNOFFSET:6,INNERLINEOFFSET:4,INNERCOLUMNOFFSET:2)
   * ERROR(LINE:32,COLUMN:8)
   * ```scala sc:fail
   * 2 + List()
   * ```
   */
  def c = 3
