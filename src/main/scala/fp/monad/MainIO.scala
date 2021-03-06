package fp.monad

import fp.monad.Monad._
import java.util.Scanner

object MainIO extends App {

  class IO[A](line: => A) {
    def run(): A = this.line
  }

  object IO {
    def apply[A](value: => A): IO[A] = new IO(value)

    def output (line: String): IO[Unit] = IO(println(line))

    def input (): IO[String] = IO (new Scanner(System.in).nextLine())
  }

  implicit object ioMonad extends Monad[IO] {
    override def unit[A](a: A): IO[A] = IO(a)

    override def flatMap[A, B](m: IO[A])(f: A => IO[B]): IO[B] = IO(f(m.run()).run())

    override def map[A, B](m: IO[A])(f: A => B): IO[B] = IO(f(m.run()))
  }

  val x: String = "test line"

  val io: IO[String] = for {
    _ <- IO.output(x)
    line <- IO.input()
    _ <- IO.output(line)
  } yield line

  io.run()
}