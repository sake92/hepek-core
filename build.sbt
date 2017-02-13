organization := "ba.sake"

name := "hepek-core"

version := "0.0.2-SNAPSHOT"

scalaVersion := "2.11.8"

description := "Core of hepek"

libraryDependencies ++= Seq(
  "org.specs2" % "classycle" % "1.4.3",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

compileOrder := CompileOrder.JavaThenScala

publishMavenStyle := true

// publish as Java library
crossPaths := false

autoScalaLibrary := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

developers += Developer("sake92", "Sakib Hadžiavdić", "sakib@sake.ba", url("http://sake.ba"))

scmInfo := Some(ScmInfo(url("https://github.com/sake92/hepek-core"), "scm:git:git@github.com:sake92/hepek-core.git"))

homepage := Some(url("http://sake.ba")) // url in maven
