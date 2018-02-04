package ba.sake.hepek.core;

/**
 * Implementing classes are rendered to a file in target folder, <br>
 * specified by {@link RelativePath#relPath()} method.
 *
 * @author Sake
 */
public abstract class Renderable extends RelativePath {

    /** @return Content of this resource. */
    public abstract String render();

}
