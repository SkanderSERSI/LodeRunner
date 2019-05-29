package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import enums.Cell;
import impl.ScreenImpl;
import services.ScreenService;

public class ScreenTest {

	ScreenService s;
	
	@Before
	public void init() {
		
		int height  = 5;
		int width   = 5;
		
		s = new ScreenImpl();
		s.init(height, width);
		
		for (int i=0;i<s.getWidth();i++) {
			s.getStateMatrice()[0][i] = Cell.MTL;
			s.getStateMatrice()[1][i] = Cell.PLT;
		}
		
		s.getStateMatrice()[1][4] = Cell.MTL;
	}
	
	/**
	 * Objectif de Test dig(int h,int w) reussi
	 * Cas de Test: s.dig(int h,int w)
	 * 				0<=w && 0<= h
	 * 
	 * Condition initiale: Aucune
	 * 
	 * Operation dig(1,2)
	 */
	@Test
	public void testDigSuccess() {
		try {
			
			s.dig(1, 2);
			
			assertTrue("cellNature(1,2) != HOL",s.cellNature(1, 2) == Cell.HOL);
		}catch(Exception e) {
			assertFalse(e.toString(),true);
		}
	}
	
	/**
	 * Objectif de Test dig(int h,int w) failed
	 * Cas de Test: s.dig(int h,int w)
	 * 				0<=w && 0<= h
	 * 
	 * Condition initiale: la premiere ligne de la matrice est en MTL et la deuxieme est en PLT
	 * sauf sa derniere case (1,4) qui est en MTL, le reste des cases de la matrice sont en EMP
	 * 
	 * Operation dig(1,4)
	 */
	@Test
	public void testDigFail1() {
		try {
			s.dig(1, 4);
			
			assertTrue("cellNature(1,4) ==  Cell.HOL",s.cellNature(1, 4) != Cell.HOL);
		}catch(Exception e) {
			assertFalse(e.toString(),true);
		}
	}
	
	
	/**
	 * Objectif de Test fill(int h,int w) reussi
	 * Cas de Test: s.fill(int h,int w)
	 * 				0<=w && 0<= h
	 * 
	 * Condition initiale: la premiere ligne de la matrice est en MTL et la deuxieme est en PLT
	 * sauf sa derniere case (1,4) qui est en MTL et la case (1,2) est en HOL, le reste des cases de la matrice sont en EMP
	 * 
	 * Operation fill(1,2)
	 */
	
	
	
	@Test
	public void testFillSuccess() {
		// condition initiale
		s.dig(1, 2);
		try {
			
			s.fill(1, 2);
			assertTrue("cellNature(1,2) !=  Cell.PLT",s.cellNature(1, 2) == Cell.PLT);
		}catch(Exception e) {
			assertFalse(e.toString(),true);
		}
	}
	
	/**
	 * Objectif de Test fill(int h,int w) failed
	 * Cas de Test: s.fill(int h,int w)
	 * 				0<=w && 0<= h
	 * 
	 * Condition initiale: la premiere ligne de la matrice est en MTL et la deuxieme est en PLT
	 * sauf sa derniere case (1,4) qui est en MTL, le reste des cases de la matrice sont en EMP
	 * 
	 * Operation fill(4,2)
	 */
	
	@Test
	public void testFillFail1() {
		// condition initiale
		try {
			
			s.fill(4, 2);
			assertTrue("cellNature(4,2) == Cell.PLT",s.cellNature(4, 2) == Cell.EMP);
		}catch(Exception e) {
			assertFalse(e.toString(),true);
		}
	}
	
	/**
	 * Objectif de Test init(int h,int w) reussi
	 * Cas de Test: s.init(int h,int w)
	 * 				0<w && 0< h
	 * 
	 * Condition initiale:Aucune
	 * 
	 * Operation init(5,5)
	 */
	@Test 
	public void testInitSuccess() {
		
		int h = 5;
		int w = 5;
		assertTrue("h<=0", h>0);
		assertTrue("w<=0", w>0);
		
		s.init(h, w);
		
		assertTrue("h != s.getHeight()",s.getHeight() == h);
		assertTrue("w != s.getWidth()",s.getWidth() == w);
		
		for(int i =0;i<h;i++) {
			for(int j=0;j<w;j++) {
				assertTrue("La cell n'est pas EMP",s.cellNature(i, j) ==  Cell.EMP);
			}
		}
		
		
	}
	
	/**
	 * Objectif de Test init(int h,int w) reussi
	 * Cas de Test: s.init(int h,int w)
	 * 				h<0 et w>0
	 * 
	 * Condition initiale:Aucune
	 * 
	 * Operation init(-5,5)
	 */
	
	public void testInitFail1() {
		int h = -5;
		int w = 5;
		
		
		try {
			s.init(h, w);
			fail();
		} catch(Exception e) {
			assertTrue("getHeight() == -5",s.getHeight() != -5);
			assertTrue("getWidth() == 5",s.getHeight() != 5);
		}
	}
	
	/**
	 * Objectif de Test init(int h,int w) reussi
	 * Cas de Test: s.init(int h,int w)
	 * 				w<0 && 0< h
	 * 
	 * Condition initiale:Aucune
	 * 
	 * Operation init(5,-5)
	 */
	
	public void testInitFail2() {
		int h = 5;
		int w = -5;

		
		try {
			s.init(h, w);
			fail();
		} catch(Exception e) {
			assertTrue("getHeight() == 5",s.getHeight() != 5);
			assertTrue("getWidth() == -5",s.getHeight() != -5);
		}
	}
	
}
