lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """PlayJava""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.8",
    libraryDependencies ++= Seq(
      guice,
      "dev.morphia.morphia" % "morphia-core" % "2.2.7",
      "com.enragedginger" %% "akka-quartz-scheduler" % "1.9.2-akka-2.6.x"
    ),
    javacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-parameters",
      "-Xlint:unchecked",
      "-Xlint:deprecation",
      "-Werror"
    ),
    // Make verbose tests
    (Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
  )