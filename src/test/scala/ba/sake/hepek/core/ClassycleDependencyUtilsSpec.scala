package ba.sake.hepek.core

import java.io.File
import java.{ util => ju }
import scala.collection.JavaConverters._
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import classycle.ClassAttributes
import classycle.graph.AtomicVertex

class ClassycleDependencyUtilsSpec extends FlatSpec with Matchers {
  import ClassycleDependencyUtils._

  // create new class vertex
  def cv(name: String): AtomicVertex = {
    val attrs = ClassAttributes.createClass(name, name, 0) // we dont care about size or source
    new AtomicVertex(attrs)
  }

  // class vertex to class name
  def cv2cn(cv: AtomicVertex): String = cv.getAttributes.asInstanceOf[ClassAttributes].getName

  def mapAsScala(revDeps: ju.Map[AtomicVertex, ju.Set[AtomicVertex]]) = {
    revDeps.asScala.map {
      case (k, v) =>
        k -> v.asScala
    }
  }

  /* TEST DATA */
  val aClass = cv("A")
  val bClass = cv("B")
  val cClass = cv("C")
  val vertices: Array[AtomicVertex] = Array(aClass, bClass, cClass)

  aClass.addIncomingArcTo(bClass) // b dependsOn a
  bClass.addIncomingArcTo(cClass) // c dependsOn => therefore, c dependsOn a (transitively)

  "ClassycleDependencyUtils" should "get direct rev-deps of a class" in {
    val directDeps = mapAsScala(getDirectRevDeps(vertices))
    val revdepsOfC = directDeps(cClass)
    revdepsOfC shouldBe empty
    val revdepsOfB = directDeps(bClass)
    revdepsOfB should contain theSameElementsAs Set(cClass)
    revdepsOfB should not contain (bClass)
    val revdepsOfA = directDeps(aClass)
    revdepsOfA should contain theSameElementsAs Set(bClass)
    revdepsOfA should not contain (aClass) // should not contain itself!
  }
  it should "get transitive rev-deps of a class" in {
    val revDeps = mapAsScala(getTransitiveRevDeps(vertices))
    val revdepsOfC = revDeps(cClass)
    revdepsOfC shouldBe empty
    val revdepsOfB = revDeps(bClass)
    revdepsOfB should contain theSameElementsAs Set(cClass)
    revdepsOfB should not contain (bClass)
    val revdepsOfA = revDeps(aClass)
    revdepsOfA should contain theSameElementsAs Set(bClass, cClass)
    revdepsOfA should not contain (aClass) // should not contain itself!
  }
  it should "get rev-deps of a class files" in {
    // NOTE: There is a cyclic dependency A->B->C->A
    // All classes depend on all other classes...
    val classesDir = new File("target/scala-2.11/test-classes/ba/sake/hepek/core/testclasses") // TODO is there a better way?
    val revDeps = mapAsScala(reverseDependencies(new ju.ArrayList(ju.Arrays.asList(classesDir)), false)) // false means transitive deps

    revDeps.keySet.find(cv => cv2cn(cv).contains("ba.sake.hepek.core.testclasses.A")).map { a =>
      revDeps(a).map(cv2cn) should contain theSameElementsAs Set("ba.sake.hepek.core.testclasses.B", "ba.sake.hepek.core.testclasses.C")
    }
    revDeps.keySet.find(cv => cv2cn(cv).contains("ba.sake.hepek.core.testclasses.B")).map { b =>
      revDeps(b).map(cv2cn) should contain theSameElementsAs Set("ba.sake.hepek.core.testclasses.A", "ba.sake.hepek.core.testclasses.C")
    }
    revDeps.keySet.find(cv => cv2cn(cv).contains("ba.sake.hepek.core.testclasses.C")).map { c =>
      revDeps(c).map(cv2cn) should contain theSameElementsAs Set("ba.sake.hepek.core.testclasses.B", "ba.sake.hepek.core.testclasses.A")
    }

  }

}