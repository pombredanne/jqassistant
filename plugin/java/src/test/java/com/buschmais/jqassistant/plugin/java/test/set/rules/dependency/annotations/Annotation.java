package com.buschmais.jqassistant.plugin.java.test.set.dependency.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * An annotation with dependencies.
 */
@Retention(RUNTIME)
public @interface Annotation {

	String value();

	Class<?>[] classValues();
}
