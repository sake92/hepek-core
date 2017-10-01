package ba.sake.hepek.core;

/**
 * Implementing classes should be rendered to a file specified by {@link RelativePath#relPath()} method.
 *
 * @author Sake
 */
public interface Renderable extends RelativePath {

    /** @return Content of this resource. */
    String render();

}
