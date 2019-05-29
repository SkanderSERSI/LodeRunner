package tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import contracts.EnvironnementContract;
import exceptions.PreconditionError;
import impl.CellContent;
import impl.EnvironnementImpl;
import services.EnvironnementService;

public class EnvironnementTest {
	
	EnvironnementService env;
	
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
		env = new EnvironnementContract(new EnvironnementImpl());
		env.init(h, w);
		
		assertTrue("h != s.getHeight()",env.getHeight() == h);
		assertTrue("w != s.getWidth()",env.getWidth() == w);
		
		for(int i =0;i<h;i++) {
			for(int j=0;j<w;j++) {
				assertTrue("La cell n'est pas EMP",env.getCharItems()[i][j] instanceof CellContent);
			}
		}
		
		
	}
	
	/**
	 * Objectif de Test init(int h,int w) reussi
	 * Cas de Test: s.init(int h,int w)
	 * 				0 >= w || 0 >= h
	 * 
	 * Condition initiale:Aucune
	 * 
	 * Operation init(5,5)
	 */
	@Test 
	public void testInitFail1() {
		
		int h = -5;
		int w = 5;
		env = new EnvironnementContract(new EnvironnementImpl());
		
		try{
			env.init(h, w);
		}catch(PreconditionError e) {
			assertTrue(e.getMessage().equals("Precondition failed: Erreur h et w negatifs"));
		}
		
		
	}
	/**
	 * Objectif de Test init(int h,int w) reussi
	 * Cas de Test: s.init(int h,int w)
	 * 				0 >= w || 0 >= h
	 * 
	 * Condition initiale:Aucune
	 * 
	 * Operation init(5,5)
	 */
	@Test 
	public void testInitFail2() {
		
		int h = 5;
		int w = -5;
		env = new EnvironnementContract(new EnvironnementImpl());
		
		try{
			env.init(h, w);
		}catch(PreconditionError e) {
			assertTrue(e.getMessage().equals("Precondition failed: Erreur h et w negatifs"));
		}
		
		
	}

}
