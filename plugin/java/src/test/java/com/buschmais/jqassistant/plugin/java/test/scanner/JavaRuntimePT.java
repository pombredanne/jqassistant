package com.buschmais.jqassistant.plugin.java.test.scanner;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Assume;
import org.junit.Test;

import com.buschmais.jqassistant.core.store.api.descriptor.Descriptor;
import com.buschmais.jqassistant.plugin.common.impl.descriptor.ArtifactDescriptor;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;

public class JavaRuntimePT extends AbstractPluginIT {

	/**
	 * The list of primitive types.
	 */
	public static final Class<?>[] PRIMITIVE_TYPES = new Class<?>[] { void.class, boolean.class, short.class, int.class, float.class,
			double.class, long.class };

	/**
	 * Scans the rt.jar of the Java Runtime Environment specified by the
	 * environment variable java.home.
	 *
	 * @throws IOException
	 *             If scanning fails.
	 */
	@Test
	public void javaRuntime() throws IOException {
		String javaHome = System.getProperty("java.home");
		Assume.assumeNotNull("java.home is not set.", javaHome);
		File runtimeJar = new File(javaHome + "/lib/rt.jar");
		Assume.assumeTrue("Java Runtime JAR not found: " + runtimeJar.getAbsolutePath(), runtimeJar.exists());
		Iterable<Descriptor> iterable = getArtifactScanner().scanArchive(runtimeJar);
		Iterator<Descriptor> iterator = iterable.iterator();
		Descriptor descriptor;
		do {
			int count = 0;
			store.beginTransaction();
			do {
				if (iterator.hasNext()) {
					descriptor = iterator.next();
					count++;
				} else {
					descriptor = null;
				}
			} while (descriptor != null && count < 10);
			store.commitTransaction();
		} while (descriptor != null);
		// long expectedTypeCount = classScannerPlugin.getScannedClasses() +
		// PRIMITIVE_TYPES.length;
		// assertThat(query("MATCH a-[:CONTAINS]->t:TYPE RETURN COUNT(DISTINCT t) as types").getColumn("types"),
		// hasItem(expectedTypeCount));
	}

}
