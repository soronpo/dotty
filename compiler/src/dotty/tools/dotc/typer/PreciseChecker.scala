package dotty.tools.dotc
package typer

import dotty.tools.dotc.ast.{ Trees, tpd }
import core.*
import Types.*, Contexts.*, Trees.*
import Decorators.*

object PreciseChecker:
  def check(tparams: List[tpd.TypeDef], applied: Type, allowMorePreciseDef: Boolean)(using Context): Unit =
    applied match
      case tpe@AppliedType(_, args) =>
        val appliedParamPreciseList = tpe.tyconTypeParams.map(_.paramPrecise)
        val tdefParamPreciseMap = tparams.view.map(p => (p.name, p.symbol.paramPrecise)).toMap

        def label(precise: Boolean): String = if (precise) "precise" else "imprecise"
        args.view.zipWithIndex.foreach {
          case (a: TypeRef, i) if a.symbol.name.isTypeName =>
            val paramName = a.symbol.name.asTypeName
            val appliedParamPrecise = appliedParamPreciseList(i)
            tdefParamPreciseMap.get(paramName).foreach { tdefParamPrecise =>
              val preciseMismatch =
                if allowMorePreciseDef then !tdefParamPrecise && appliedParamPrecise
                else tdefParamPrecise != appliedParamPrecise
              if preciseMismatch then
                val pos = tparams.find(_.name == paramName).get.srcPos
                report.error(em"${label(tdefParamPrecise)} type parameter $paramName occurs in ${label(appliedParamPrecise)} position in $tpe", pos)
            }
          case _ =>
        }
      case _ =>

  def checkClass(tree: tpd.Template)(using Context): Unit =
    val tparams = tree.constr.leadingTypeParams
    tree.parents.view.map(_.tpe).foreach(check(tparams, _, true))

  def checkLambda(tree: tpd.LambdaTypeTree)(using Context): Unit =
    check(tree.tparams, tree.body.tpe, false)
