package com.buschmais.jqassistant.plugin.java.impl.store.resolver;

import com.buschmais.jqassistant.core.store.api.Store;

public class DescriptorResolverFactory {

    private PackageDescriptorResolver packageDescriptorResolver;

    private TypeDescriptorResolver typeDescriptorResolver;

    private MethodDescriptorResolver methodDescriptorResolver;

    private ConstructorDescriptorResolver constructorDescriptorResolver;

    private FieldDescriptorResolver fieldDescriptorResolver;

    public DescriptorResolverFactory(Store store) {
        packageDescriptorResolver = new PackageDescriptorResolver(store);
        typeDescriptorResolver = new TypeDescriptorResolver(store, packageDescriptorResolver);
        methodDescriptorResolver = new MethodDescriptorResolver(store, typeDescriptorResolver);
        constructorDescriptorResolver = new ConstructorDescriptorResolver(store, typeDescriptorResolver);
        fieldDescriptorResolver = new FieldDescriptorResolver(store, typeDescriptorResolver);
    }

    public TypeDescriptorResolver getTypeDescriptorResolver() {
        return typeDescriptorResolver;
    }

    public MethodDescriptorResolver getMethodDescriptorResolver() {
        return methodDescriptorResolver;
    }

    public FieldDescriptorResolver getFieldDescriptorResolver() {
        return fieldDescriptorResolver;
    }

    public ConstructorDescriptorResolver getConstructorDescriptorResolver() {
        return constructorDescriptorResolver;
    }
}
