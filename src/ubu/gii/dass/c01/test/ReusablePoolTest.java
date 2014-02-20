package ubu.gii.dass.c01.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ubu.gii.dass.c01.ReusablePool;

public class ReusablePoolTest {

	/**
	 * ReusablePool utiliza el patron Singleton. Comprobamos que en sucesivas
	 * llamadas de getInstance, se devuelve la misma instancia.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool instance = ReusablePool.getInstance();
		assertEquals(instance, ReusablePool.getInstance());
	}

	@Test
	public void testAcquireReusable() {
		fail("Not yet implemented");
	}

	@Test
	public void testReleaseReusable() {
		fail("Not yet implemented");
	}

}
