ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "scala-interactive-cli-template",
    libraryDependencies ++= Seq(
      "io.github.kitlangton" %% "zio-tui" % "0.2.0"
    ),
    graalVMNativeImageOptions ++= Seq(
      "--no-fallback"
    )
  )
  .enablePlugins(GraalVMNativeImagePlugin)
