package ba.sake.hepek.core;

import java.util.List;

/**
 * List of renderable classes are rendered to corresponding files in target folder.
 *
 * @author Sake
 */
public abstract class MultiRenderable {

    /** @return Content of this resource. */
    public abstract List<Renderable> renderables();

}
