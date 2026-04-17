name := "sample-scala"
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test,
  "org.playframework" %% "play-slick" % "6.2.0",
  "org.postgresql" % "postgresql" % "42.7.1",
  "org.flywaydb" % "flyway-core" % "9.22.3",
  "org.playframework" %% "play-json" % "3.0.6"
)
