ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """PlayJava""",
    libraryDependencies ++= Seq(
      guice,
      "dev.morphia.morphia" % "morphia-core" % "2.2.7"
    )
  )


