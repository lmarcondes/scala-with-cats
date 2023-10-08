val scala2Version = "2.13.11"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-with-cats-book",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala2Version,
    // scalacOptions ++= Seq(
    //   "-Xfatal-warnings",
    // ),
    libraryDependencies ++= List(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.typelevel" %% "cats-core" % "2.1.0",
      // "ch.epfl.scala" %% "scala-debug-adapter" % "3.0.5"
    )
  )
