package ba.sake.hepek.core;

import java.io.File;

/**
 * Abstract path of a resource that will be either: <br>
 * - rendered with "sbt-hepek" or <br>
 * - manually placed there after the processing (by the user).
 *
 * @author Sake
 */
public interface RelativePath {

    /** @return Relative path of a resource. */
    File relPath();

}
