# hepek-core
Core of the [sbt-hepek](https://github.com/sake92/sbt-hepek).  
This project contains two interfaces ([`Renderable`](https://github.com/sake92/hepek-core/blob/master/src/main/java/ba/sake/hepek/core/Renderable.java) and [`RelativePath`](https://github.com/sake92/hepek-core/blob/master/src/main/java/ba/sake/hepek/core/RelativePath.java)) and a single helper class ([`ClassycleDependencyUtils`](https://github.com/sake92/hepek-core/blob/master/src/main/java/ba/sake/hepek/core/ClassycleDependencyUtils.java)). 

Interfaces are **essential** to the sbt-hepek:
- All objects that are meant to be rendered must extend `Renderable`
- All resources that are either `Renderable` or are going to be placed in the `hepekTarget` folder manually must extend `RelativePath`

The `ClassycleDependencyUtils` class has one handy method called `reverseDependencies: Map[AtomicVertex, Set[AtomicVertex]]`. Vertices here are actually Java bytecode classes.  
It is used for optimized rendering of objects. When an object is changed and compiled, this method determines which other objects use it, so that they are rendered again too.

Special thanks to [Classycle](http://classycle.sourceforge.net/)! :)
