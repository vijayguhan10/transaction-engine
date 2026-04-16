error id: file://<WORKSPACE>/build.sbt:
file://<WORKSPACE>/build.sbt
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 194
uri: file://<WORKSPACE>/build.sbt
text:
```scala
name := """sample-scala"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.18"

libraryDependenci@@es += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routename := "slick-flyway-app"

scalaVersion := "2.13.18"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.9.0",
  "com.typesafe.play" %% "play-slick" % "5.1.0",
  "org.postgresql" % "postgresql" % "42.7.1",
  "org.flywaydb" % "flyway-core" % "9.22.3",
  "com.typesafe.play" %% "play-json" % "2.10.0-RC7"
)s.RoutesKeys.routesImport += "com.example.binders._"

```


#### Short summary: 

empty definition using pc, found symbol in pc: 