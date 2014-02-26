package ubu.gii.dass.test.c01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

public class ReusablePoolTest {

	private List<Reusable> pool = new ArrayList<Reusable>();

	/**
	 * ReusablePool utiliza el patron Singleton. Comprueba que en sucesivas
	 * llamadas de getInstance, se devuelve la misma instancia.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool instance = ReusablePool.getInstance();
		assertEquals("No se cumple el patron Singleton.", instance,
				ReusablePool.getInstance());
	}

	/**
	 * Comprueba que el metodo lanza su excepcion cuando no hay mas reusables.
	 * 
	 * @throws NotFreeInstanceException
	 *             No hay mas reusables.
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
	 * numero inicial de Reusable.
	 */
	@Before
	public void setUp() {
		for (Reusable r : pool) {
			ReusablePool.getInstance().releaseReusable(r);
		}

		pool.clear();
	}

	/**
	 * Valida el funcionamiento de releaseReusable. Se comprueban los siguientes comportamientos:
	 * 
	 * - releaseReusable devuelve un objeto al pool.
	 * - releaseReusable no permite duplicar elementos en el pool.
	 * - releaseReusable permite introducir elementos nulos.
	 */
	@Test
	public void testReleaseReusable() {
		ReusablePool reusablePool = ReusablePool.getInstance();
		int sizePool;
		/**
		 * Obtiene todos los reusables.
		 */
		try {
			while (true) {
				Reusable reusable = reusablePool.acquireReusable();
				pool.add(reusable);
			}
		} catch (NotFreeInstanceException e) {

		}

		sizePool = pool.size();

		/**
		 * Devuelve el ultimo reusable.
		 */
		if (!pool.isEmpty()) {
			reusablePool.releaseReusable(pool.remove(pool.size() - 1));
		}

		try {
			Reusable reusable = reusablePool.acquireReusable();
			pool.add(reusable);
		} catch (NotFreeInstanceException e) {
			fail("Deberia haber un reusable disponible en el pool.");
		}

		/**
		 * Comprueba que no se pueden introducir elementos repetidos.
		 */
		for (int i = 0; i < sizePool; i++) {
			Reusable reusable = pool.remove(0);
			reusablePool.releaseReusable(reusable);
			reusablePool.releaseReusable(reusable);
		}

		try {
			while (true) {
				Reusable reusable = reusablePool.acquireReusable();
				pool.add(reusable);
			}
		} catch (NotFreeInstanceException e) {

		}

		if (pool.size() != sizePool) {
			fail("ReusablePool permite tener elementos repetidos.");
		}

		/**
		 * Comprueba que no se pueden devolver elementos nulos al pool.
		 */
		reusablePool.releaseReusable(null);
		try {
			Reusable reusable = reusablePool.acquireReusable();
			if(reusable == null) {
				fail("ReusablePool permite introducir elementos nulos");
			}
		} catch (NotFreeInstanceException e) {

		}

	}

}
