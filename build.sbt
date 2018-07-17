
organization := "ba.sake"
name := "hepek-core"
description := "Core of hepek"

version := "0.1.1"

scalaVersion := "2.12.4"

compileOrder := CompileOrder.JavaThenScala

libraryDependencies ++= Seq(
  "ba.sake" % "hepek-classycle" % "0.0.1",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

publishMavenStyle := true   // publish as Java library
crossPaths := false         // isn't Scala
autoScalaLibrary := false   // don't need Scala to compile

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

developers += Developer("sake92", "Sakib Hadžiavdić", "sakib@sake.ba", url("http://sake.ba"))

scmInfo := Some(ScmInfo(url("https://github.com/sake92/hepek-core"), "scm:git:git@github.com:sake92/hepek-core.git"))

homepage := Some(url("http://sake.ba"))
