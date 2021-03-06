package com.buschmais.jqassistant.plugin.java.impl.store.resolver;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.descriptor.FullQualifiedNameDescriptor;
import com.buschmais.jqassistant.core.store.api.descriptor.ParentDescriptor;
import com.buschmais.jqassistant.plugin.java.impl.store.descriptor.SignatureDescriptor;

/**
 * Abstract resolver providing functionality to resolve a descriptor hierarchy
 * from a full qualified name.
 * <p>
 * A full qualified name consists of the full qualified name of the parent
 * descriptor and the signature of the descriptor.
 * </p>
 *
 * @param <P> The type of the parent descriptor.
 * @param <T> The type of the descriptor to be resolved.
 */
public abstract class AbstractDescriptorResolver<P extends ParentDescriptor & SignatureDescriptor, T extends FullQualifiedNameDescriptor & SignatureDescriptor> {

    public static final String EMPTY_NAME = "";
    private final Store store;
    /**
     * The parent resovler.
     */
    private final AbstractDescriptorResolver<?, P> parentResolver;

    /**
     * Constructor.
     *
     * @param store          The store.
     * @param parentResolver The parent resolver instance.
     */
    protected AbstractDescriptorResolver(Store store, AbstractDescriptorResolver<?, P> parentResolver) {
        this.store = store;
        this.parentResolver = parentResolver;
    }

    /**
     * Constructor.
     *
     * @param store The store.
     */
    @SuppressWarnings("unchecked")
    protected AbstractDescriptorResolver(Store store) {
        this.store = store;
        this.parentResolver = (AbstractDescriptorResolver<?, P>) this;
    }

    /**
     * Resolve a descriptor from a given parent descriptor and a local
     * signature.
     *
     * @param parent    The parent descriptor.
     * @param signature The signature.
     * @return The descriptor.
     */
    public T resolve(P parent, String signature) {
        StringBuffer fullQualifiedName = new StringBuffer(parent.getFullQualifiedName());
        fullQualifiedName.append(getSeparator());
        fullQualifiedName.append(signature);
        return resolve(fullQualifiedName.toString());
    }

    /**
     * Resolve a descriptor from a given full qualified name.
     *
     * @param fullQualifiedName The full qualified name.
     * @return The descriptor.
     */
    public T resolve(String fullQualifiedName) {
        return resolve(fullQualifiedName, getBaseType());
    }

    /**
     * Resolve a descriptor from a given full qualified name.
     *
     * @param fullQualifiedName The full qualified name.
     * @param concreteType      The concrete type to use if an instance is created.
     * @return The descriptor.
     */
    public T resolve(String fullQualifiedName, Class<? extends T> concreteType) {
        T descriptor = store.find(getBaseType(), fullQualifiedName);
        if (descriptor != null) {
            if (getBaseType().equals(concreteType)) {
                return descriptor;
            } else {
                return store.migrate(descriptor, concreteType);
            }
        } else {
            P parent = null;
            String signature;
            if (!EMPTY_NAME.equals(fullQualifiedName)) {
                int separatorIndex = fullQualifiedName.lastIndexOf(getSeparator());
                String parentName;
                if (separatorIndex != -1) {
                    signature = fullQualifiedName.substring(separatorIndex + 1, fullQualifiedName.length());
                    parentName = fullQualifiedName.substring(0, separatorIndex);
                } else {
                    signature = fullQualifiedName;
                    parentName = EMPTY_NAME;
                }
                parent = parentResolver.resolve(parentName);
            } else {
                signature = EMPTY_NAME;
            }
            descriptor = store.create(concreteType, fullQualifiedName);
            descriptor.setSignature(signature);
            if (parent != null) {
                parent.getContains().add(descriptor);
            }
            return descriptor;
        }
    }

    /**
     * Return the descriptor type which is resolved by this instance.
     */
    protected abstract Class<T> getBaseType();

    protected abstract char getSeparator();
}
