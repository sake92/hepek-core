# hepek-core
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ba.sake/hepek-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ba.sake/hepek-core)


## Usage
- Add to deps:
```scala
libraryDependencies ++= Seq(
  "ba.sake" % "hepek-core" % latestVersion
)
```
- Code:
```scala
val filesJavaList = new java.util.ArrayList[File]()
filesJavaList.add(new File("myClass.class"))
filesJavaList.add(new File("myClassesFolder"))

// java.util.Map[AtomicVertex, Set[AtomicVertex]]
val classRevDeps = ClassycleDependencyUtils.reverseDependencies(filesJavaList, false)
```


# What is it?
Core of the [sbt-hepek](https://github.com/sake92/sbt-hepek).
This project contains two interfaces ([`Renderable`](https://github.com/sake92/hepek-core/blob/master/src/main/java/ba/sake/hepek/core/Renderable.java)
 and [`RelativePath`](https://github.com/sake92/hepek-core/blob/master/src/main/java/ba/sake/hepek/core/RelativePath.java))
 and a single helper class ([`ClassycleDependencyUtils`](https://github.com/sake92/hepek-core/blob/master/src/main/java/ba/sake/hepek/core/ClassycleDependencyUtils.java)).


Interfaces are **essential** to the sbt-hepek:
- All objects that are meant to be rendered must extend `Renderable`
- `RelativePath` is used for getting a relative path to a `Renderable` or another file (css, js or whatever)

The `ClassycleDependencyUtils` class has one handy method called `reverseDependencies` that returns `Map[AtomicVertex, Set[AtomicVertex]]`.
Vertices here are actually Java bytecode classes.

It is used for optimized rendering of objects. 
When an object is *changed and compiled*, this method determines **which other objects use it**, so that they are rendered again too.

Special thanks to [Classycle](http://classycle.sourceforge.net/)! :)
