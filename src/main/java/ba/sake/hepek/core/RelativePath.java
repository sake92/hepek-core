package ba.sake.hepek.core;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Abstract relative path of a resource in hepek's target folder. <br>
 * Used for: <br>
 * - rendering Renderable with "sbt-hepek" and other tools <br>
 * - referring to other files used by Renderables.
 *
 * @author Sake
 */
public abstract class RelativePath {

    /** @return Relative path of this resource. */
    public abstract Path relPath();

    public String relTo(RelativePath other) {
        // put both paths ON THE SAME LEVEL, in the "dummy-root" (imaginary) folder
        String dummyRoot = "dummy-root";
        Path p1 = Paths.get(dummyRoot, relPath().toString());
        Path p2 = Paths.get(dummyRoot, other.relPath().toString());
        Path relativePath = p1.getParent().relativize(p2);
        return relativePath.toString().replaceAll("\\\\", "/");
    }
}
