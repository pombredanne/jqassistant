package com.buschmais.jqassistant.core.store.api.descriptor;

import com.buschmais.cdo.neo4j.api.annotation.Relation;

import java.util.Set;

/**
 * Abstract base implementation of an
 * {@link FullQualifiedNameDescriptor} which contains other
 * {@link Descriptor}s.
 */
public interface ParentDescriptor extends FullQualifiedNameDescriptor {

    /**
     * Return the contained descriptors.
     *
     * @return The contained descriptors.
     */
    @Relation("CONTAINS")
    public Set<Descriptor> getContains();
}
