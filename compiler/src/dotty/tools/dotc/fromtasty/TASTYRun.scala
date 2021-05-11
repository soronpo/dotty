package dotty.tools
package dotc
package fromtasty

import io.{JarArchive, AbstractFile, Path}
import core.Contexts._

class TASTYRun(comp: Compiler, ictx: Context) extends Run(comp, ictx) {
  override def compile(files: List[AbstractFile]): Unit = {
    val units = tastyUnits(files)
    compileUnits(units)
  }

  private def tastyUnits(files: List[AbstractFile]): List[TASTYCompilationUnit] =
    val fromTastyIgnoreList = ctx.settings.YfromTastyIgnoreList.value.toSet
    // Resolve class names of tasty and jar files
    val classNames = files.flatMap { file =>
      file.extension match
        case "jar" =>
          JarArchive.open(Path(file.path), create = false).iterator()
            .filter(e => e.extension == "tasty" && !fromTastyIgnoreList(e.name))
            .map(e => e.name.stripSuffix(".tasty").replace("/", "."))
            .toList
        case "tasty" => TastyFileUtil.getClassName(file)
        case _ =>
          report.error(s"File extension is not `tasty` or `jar`: ${file.path}")
          Nil
    }
    classNames.map(new TASTYCompilationUnit(_))
}
