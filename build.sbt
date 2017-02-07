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

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT")) 
    Some("snapshots" at nexus + "content/repositories/snapshots") 
  else 
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pomExtra := (
  <url>http://sake.ba</url>
  <scm>
    <url>scm:git:git@github.com:sake92/hepek-core.git</url>
    <connection>scm:git:git@github.com:sake92/hepek-core.git</connection>
  </scm>
  <developers>
    <developer>
      <id>sake92</id>
      <name>Sakib Hadžiavdić</name>
      <url>http://sake.ba</url>
    </developer>
  </developers>
)

pomIncludeRepository := { _ => false }
