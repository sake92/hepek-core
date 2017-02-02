organization := "ba.sake"

name := "hepek-core"

version := "0.0.1"

scalaVersion := "2.11.8"

description := "Core of hepek"

libraryDependencies ++= Seq(
  "org.specs2" % "classycle" % "1.4.3",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

compileOrder := CompileOrder.JavaThenScala

// publish as Java library
publishMavenStyle := true

crossPaths := false

autoScalaLibrary := false
