name := "sample-scala"
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"

// Keep all Jackson modules on the same minor version.
// Play / Pekko can otherwise end up with jackson-databind 2.15.x but jackson-module-scala 2.14.x,
// which fails fast at runtime when Pekko registers the Scala module.
val jacksonVersion = "2.15.2"

dependencyOverrides ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "com.fasterxml.jackson.module" % "jackson-module-parameter-names" % jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion,
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-cbor" % jacksonVersion
)

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test,
  "org.playframework" %% "play-slick" % "6.2.0",
  "org.postgresql" % "postgresql" % "42.7.1",
  "org.flywaydb" % "flyway-core" % "9.22.3",
  "org.playframework" %% "play-json" % "3.0.6"
)
