package com.buschmais.jqassistant.plugin.java.impl.store.descriptor;

import com.buschmais.cdo.neo4j.api.annotation.Label;
import com.buschmais.cdo.neo4j.api.annotation.Property;
import com.buschmais.cdo.neo4j.api.annotation.Relation;
import com.buschmais.jqassistant.core.store.api.descriptor.FullQualifiedNameDescriptor;
import com.buschmais.jqassistant.core.store.api.descriptor.NamedDescriptor;

import java.util.Set;

/**
 * Describes a method of a Java class.
 */
@Label(value = "METHOD", usingIndexOf = FullQualifiedNameDescriptor.class)
public interface MethodDescriptor extends FullQualifiedNameDescriptor, SignatureDescriptor, NamedDescriptor, DependentDescriptor, AnnotatedDescriptor, AccessModifierDescriptor {

    @Relation("HAS")
    public Set<ParameterDescriptor> getParameters();

    @Relation("THROWS")
    public Set<TypeDescriptor> getDeclaredThrowables();

    @Relation("READS")
    public Set<FieldDescriptor> getReads();

    @Relation("WRITES")
    public Set<FieldDescriptor> getWrites();

    @Relation("INVOKES")
    public Set<MethodDescriptor> getInvokes();

    @Property("ABSTRACT")
    public Boolean isAbstract();

    void setAbstract(Boolean isAbstract);

    @Property("NATIVE")
    public Boolean isNative();

    public void setNative(Boolean nativeMethod);
}
