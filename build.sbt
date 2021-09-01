name := "scala-bootcamp"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.16"

libraryDependencies ++= Seq (
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.mockito" %% "mockito-scala" % "1.15.0" % "test",
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.3",
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test
)