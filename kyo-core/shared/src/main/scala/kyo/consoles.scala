package kyo

import java.io.EOFException

abstract class Console:
    def readln: String < IOs
    def print(s: String): Unit < IOs
    def printErr(s: String): Unit < IOs
    def println(s: String): Unit < IOs
    def printlnErr(s: String): Unit < IOs
end Console

object Console:
    val default: Console =
        new Console:
            val readln =
                IOs {
                    val line = scala.Console.in.readLine()
                    if line == null then
                        throw new EOFException("Consoles.readln failed.")
                    else
                        line
                    end if
                }
            def print(s: String)      = IOs(scala.Console.out.print(s))
            def printErr(s: String)   = IOs(scala.Console.err.print(s))
            def println(s: String)    = IOs(scala.Console.out.println(s))
            def printlnErr(s: String) = IOs(scala.Console.err.println(s))
end Console

opaque type Consoles <: IOs = IOs

object Consoles:

    private val local = Locals.init(Console.default)

    def run[T, S](v: T < (Consoles & S)): T < (S & IOs) =
        v

    def run[T, S](c: Console)(v: T < (Consoles & S)): T < (S & IOs) =
        local.let(c)(v)

    val readln: String < IOs =
        local.use(_.readln)

    private def toString(v: Any): String =
        v match
            case v: String =>
                v
            case v =>
                pprint.apply(v).plainText

    def print[T](v: T): Unit < Consoles =
        local.use(_.print(toString(v)))

    def printErr[T](v: T): Unit < Consoles =
        local.use(_.printErr(toString(v)))

    def println[T](v: T): Unit < Consoles =
        local.use(_.println(toString(v)))

    def printlnErr[T](v: T): Unit < Consoles =
        local.use(_.printlnErr(toString(v)))
end Consoles
