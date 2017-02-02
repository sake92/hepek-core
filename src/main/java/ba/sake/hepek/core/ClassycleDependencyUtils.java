package ba.sake.hepek.core;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import classycle.Analyser;
import classycle.graph.AtomicVertex;

/**
 * Handy util methods for getting static reverse dependencies of classes. <br>
 * Uses Classycle Analyser internally. <br>
 * Contains only static methods.
 * 
 * @author Sake
 */
public class ClassycleDependencyUtils {

    private ClassycleDependencyUtils() {
        throw new UnsupportedOperationException("Can't instantiate ClassycleDependencyUtils.");
    }

    /**
     * Calculates static reverse dependencies of classes.
     * 
     * @param files
     *            Files/Directories to process.
     * @param directOnly
     *            If true, direct reverse dependencies are calculated for each class. If false, transitive reverse
     *            dependencies are calculated for each class.
     * @return Reverse dependencies of classes.
     */
    public static Map<AtomicVertex, Set<AtomicVertex>> reverseDependencies(final List<File> files,
            final boolean directOnly) {
        final String[] filePaths = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File f = files.get(i);
            filePaths[i] = f.getAbsolutePath();
        }
        final Analyser analyser = new Analyser((String[]) filePaths);
        final AtomicVertex[] classVertices = analyser.getClassGraph();

        if (directOnly) {
            return getDirectRevDeps(classVertices);
        } else {
            return getTransitiveRevDeps(classVertices);
        }
    }

    /* DIRECT REVERSE DEPENDENCIES, simple */
    /**
     * @param classVertices
     *            Classes to analyse.
     * @return Direct reverse dependencies of classes.
     */
    static Map<AtomicVertex, Set<AtomicVertex>> getDirectRevDeps(final AtomicVertex[] classVertices) {
        Map<AtomicVertex, Set<AtomicVertex>> directRevDeps = new HashMap<>(classVertices.length);
        for (AtomicVertex classVertex : classVertices) {
            int reverseDepsCount = classVertex.getNumberOfIncomingArcs();
            Set<AtomicVertex> revDepsOfClass = new HashSet<>(reverseDepsCount);
            for (int i = 0; i < reverseDepsCount; i++) {
                AtomicVertex revDep = (AtomicVertex) classVertex.getTailVertex(i);
                revDepsOfClass.add(revDep);
            }
            directRevDeps.put(classVertex, revDepsOfClass);
        }
        return directRevDeps;
    }

    /* TRANSITIVE REVERSE DEPENDENCIES, semi-simple */
    /**
     * @param classVertices
     *            Classes to analyse.
     * @return Transitive reverse dependencies of classes. <br>
     *         Transitive means (direct deps) + (deps of deps) + (deps of deps of deps) + etc.
     */
    static Map<AtomicVertex, Set<AtomicVertex>> getTransitiveRevDeps(final AtomicVertex[] classVertices) {
        Map<AtomicVertex, Set<AtomicVertex>> directDeps = getDirectRevDeps(classVertices);
        Map<AtomicVertex, Set<AtomicVertex>> result = new HashMap<>(directDeps.keySet().size());
        for (AtomicVertex v : directDeps.keySet()) {
            Set<AtomicVertex> transitiveDeps = getTransitiveRevDepsForClass(v, directDeps, new HashSet<>());
            result.put(v, transitiveDeps);
        }
        return result;
    }

    /**
     * Gets all dependencies of one class.
     * 
     * @param className
     *            Class name for which we find rev-deps.
     * @param deps
     *            Direct dependencies of all classes. See method `getDirectRevDeps`.
     * @param visited
     *            Names of classes which are already traversed. (There could be cycles, that's why)
     * @return All rev-deps of class called `className`.
     */
    static Set<AtomicVertex> getTransitiveRevDepsForClass(final AtomicVertex classVertice,
            final Map<AtomicVertex, Set<AtomicVertex>> directDeps, final Set<AtomicVertex> visited) {
        // direct deps of given class
        final Set<AtomicVertex> classDeps = directDeps.get(classVertice);
        final Set<AtomicVertex> oldVisited = new HashSet<>(visited);
        visited.addAll(classDeps);
        // new deps to visited recursively
        final Set<AtomicVertex> newTransitiveDeps = new HashSet<>();
        for (AtomicVertex v : classDeps) {
            if (!oldVisited.contains(v)) {
                Set<AtomicVertex> transitiveDeps = getTransitiveRevDepsForClass(v, directDeps, visited);
                newTransitiveDeps.addAll(transitiveDeps);
            }
        }

        classDeps.addAll(newTransitiveDeps); // collect all results
        // must use Iterator for removal, bcoz ConcurrentModificationException
        for (final Iterator<AtomicVertex> iterator = classDeps.iterator(); iterator.hasNext();) {
            if (iterator.next().equals(classVertice)) { // class can't depend on itself, LOL..
                iterator.remove();
            }
        }
        return classDeps;
    }

}
