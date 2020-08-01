import sbt._

object Dependencies {
  val circeVersion = "0.13.0"

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-literal",
    "io.circe" %% "circe-refined"
  ).map(_ % circeVersion)

  lazy val refined = Seq("eu.timepit" %% "refined" % "0.9.13")

  lazy val scalaTest = Seq(
    "org.scalatest" %% "scalatest" % "3.1.1" % Test
  )

  lazy val dependencies: Seq[ModuleID] = circe ++ scalaTest ++ refined
}
