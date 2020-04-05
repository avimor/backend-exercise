name := """backend-exercise"""
organization := "com.argus"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, DockerPlugin)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.redisson" % "redisson" % "3.12.4"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.argus.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.argus.binders._"

dockerBaseImage := "anapsix/alpine-java"
dockerExposedPorts += 9000
dockerUpdateLatest := true
dockerUsername := Some("avim")
