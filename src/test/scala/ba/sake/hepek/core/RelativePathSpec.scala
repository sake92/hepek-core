package ba.sake.hepek.core

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.nio.file.Paths

class RelativePathSpec extends FlatSpec with Matchers {

  "RelativePath" should "refer to other RelativePath properly" in {
    val rp1 = new TestRelPath("index.html")
    val rp2 = new TestRelPath("styles/main.css")

    (rp1.relTo(rp1)) shouldBe "index.html" // refer to itself
    (rp1.relTo(rp2)) shouldBe "styles/main.css"
    (rp2.relTo(rp1)) shouldBe "../index.html"
  }

}

class TestRelPath(fileName: String) extends RelativePath {
  override def relPath = Paths.get(fileName)
}