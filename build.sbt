
organization := "ba.sake"
name := "hepek-core"
description := "Core of hepek"

version := "0.2.0"

scalaVersion := "2.12.10"

compileOrder := CompileOrder.JavaThenScala

libraryDependencies ++= Seq(
  "ba.sake" % "hepek-classycle" % "0.0.1",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

githubOwner := "sake92"
githubRepository := "hepek-core"
publishMavenStyle := true   // mandatory for GH packages!
githubTokenSource := TokenSource.Environment("MY_GITHUB_TOKEN")

crossPaths := false         // isn't Scala
autoScalaLibrary := false   // don't need Scala to compile

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

developers += Developer("sake92", "Sakib Hadžiavdić", "sakib@sake.ba", url("http://sake.ba"))

scmInfo := Some(ScmInfo(url("https://github.com/sake92/hepek-core"), "scm:git:git@github.com:sake92/hepek-core.git"))

homepage := Some(url("http://sake.ba"))
