package tests;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import enums.Cell;
import impl.EditableScreenImpl;
import services.EditableScreenService;

public class EditableScreenTest {
	
	EditableScreenService s;
	
	@Before
	public void init() {
		
		int height  = 5;
		int width   = 5;
		
		s = new EditableScreenImpl();
		s.init(height, width);
		
		for (int i=0;i<width;i++) {
			s.getStateMatrice()[0][i] = Cell.MTL;
			s.getStateMatrice()[1][i] = Cell.PLT;
		}
	}
	
	/**
	 * 
	 * Objectif de Test: CellNature(int x,int y ) reussi
	 *  
	 * Cas de Test: setNature(x,y)
	 * 				0 <=x < getHeight() and 0 <= y <= getWidth()
	 * 
	 *  Condition Initiale: CellNature(2,2) = EMP
	 *  
	 *  Operation
	 *  
	 *  cellNature(2,2)
	 *  
	 * 
	 */
	@Test
	public void testsetNatureReussi() {
		// condition initiales
		
		int h = 2;
		int w = 2;
		
		//pre conditions
		assertTrue("h < 0 || h > s.getHeight()",h <= s.getHeight() && h >= 0);
		assertTrue("w < 0 || w > s.getWidth()",w <= s.getWidth() && w >= 0);
		
		s.setNature(h, w, Cell.LAD);
		
		//post conditions
		assertTrue("s.cellNature(h,w) == Cell.EMP", s.cellNature(h, w) == Cell.LAD);
	}
	
	/**
	 * 
	 * Objectif de Test: CellNature(int x,int y ) failed
	 *  
	 * Cas de Test: setNature(x,y)
	 * 				(x<0 || x>= getHeight()) and 0 <= y <= getWidth()
	 * 
	 *  Condition Initiale: CellNature(-2,2) = EMP
	 *  
	 *  Operation
	 *  
	 *  cellNature(-2,2)
	 * 
	 * 
	 */
	
	@Test
	public void testsetNatureFail1() {
		// condition initiale 
		
		int h = -2;
		int w = 2;
		
		try {
			s.setNature(h, w, Cell.LAD);
			assertTrue("s.CellNature(h,w) ==Cell.LAD",s.cellNature(h, w) != Cell.LAD);
		}catch(Exception e ) {
		}
		
		
	}
	
	/**
	 * 
	 * Objectif de Test: CellNature(int x,int y ) failed
	 *  
	 * Cas de Test: setNature(x,y)
	 * 				(y<0 || y>= getHeight()) and 0 <= x <= getWidth()
	 * 
	 *  Condition Initiale: CellNature(2,-2) = EMP
	 *  
	 *  Operation
	 *  
	 *  cellNature(2,-2)
	 * 
	 * 
	 */
	
	@Test
	public void testsetNatureFail2() {
		// condition initiale 
		
		int h = 2;
		int w = -2;
		
		try {
			s.setNature(h, w, Cell.LAD);
			assertTrue("s.CellNature(h,w) ==Cell.LAD",s.cellNature(h, w) != Cell.LAD);
		}catch(Exception e ) {
		}
		
		
	}
	
	/**
	 * 
	 * Objectif de Test: PlayableSuccess(int x,int y ) reussi
	 *  
	 * Cas de Test: playable(x,y)
	 * 
	 *  Conditions Initiales: la premiere ligne de la matrice est en MTL et la deuxieme est en PLT  et pas de HOLE dans la matrice
	 *  
	 *  Operation
	 *  
	 *  playable()
	 * 
	 * 
	 */
	
	@Test
	public void testPlayableSuccess() {
		
		try {
			assertTrue("pas jouable",s.Playable() == true);
			
		}catch(Exception e) {
		}
	}
	
	/**
	 * 
	 * Objectif de Test: PlayableSuccess(int x,int y ) failed
	 *  
	 * Cas de Test: playable(x,y)
	 * 
	 *  Conditions Initiales: la premiere ligne de la matrice est en MTL et la deuxieme est en PLT  avec un hole à la case (1,2)
	 *  
	 *  Operation
	 *  
	 *  playable()
	 * 
	 * 
	 */
	
	@Test
	public void testPlayableFail1() {
		
		try {
			s.setNature(1, 2,Cell.HOL);
			assertTrue("jouable",s.Playable() == false);
			s.setNature(1, 2,Cell.EMP);
			
		}catch(Exception e) {
		}
	}
	
}
