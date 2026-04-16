name := "sample-scala"
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.18"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test,
  "com.typesafe.play" %% "play-slick" % "5.1.0",
  "org.postgresql" % "postgresql" % "42.7.1",
  "org.flywaydb" % "flyway-core" % "9.22.3",
  "com.typesafe.play" %% "play-json" % "2.10.0-RC7"
)

RoutesKeys.routesImport += "com.example.binders._"
