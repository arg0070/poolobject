package ubu.gii.dass.c01.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

public class ReusablePoolTest {

	List<Reusable> pool = new ArrayList<Reusable>();

	/**
	 * ReusablePool utiliza el patron Singleton. Comprueba que en sucesivas
	 * llamadas de getInstance, se devuelve la misma instancia.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool instance = ReusablePool.getInstance();
		assertEquals("No se cumple el patrón Singleton.", instance,
				ReusablePool.getInstance());
	}

	/**
	 * Comprueba que el método lanza su excepción cuando no hay mas reusables.
	 * 
	 * @throws NotFreeInstanceException
	 *             No hay más reusables.
	 */
	@Test(expected = NotFreeInstanceException.class)
	public void testAcquireReusable() throws NotFreeInstanceException {
		ReusablePool instance = ReusablePool.getInstance();

		while (true) {
			Reusable reusable = instance.acquireReusable();
			pool.add(reusable);
		}
	}

	/**
	 * Se ejecuta antes de cada test, para asegurar que ReusablePool mantiene el
	 * número inicial de Reusable.
	 */
	@Before
	public void setUp() {
		for (Reusable r : pool) {
			ReusablePool.getInstance().releaseReusable(r);
		}
	}

	@Test
	public void testReleaseReusable() {
		fail("Not yet implemented");
	}

}
