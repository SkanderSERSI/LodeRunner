package tests;

import static org.junit.Assert.assertTrue;
import java.awt.Point;
import java.util.ArrayList;
import org.junit.Test;
import contracts.EngineContract;
import contracts.EnvironnementContract;
import contracts.PlayerContract;
import enums.Cell;
import enums.Command;
import enums.ItemType;
import impl.EngineImpl;
import impl.EnvironnementImpl;
import impl.PlayerImpl;
import services.EngineService;
import services.EnvironnementService;
import services.PlayerService;

public class PlayerTest {

	EngineService eng;
	
	public void init1() {
		EnvironnementService env = new EnvironnementContract(new EnvironnementImpl());
		env.init(10, 10);
		for (int i=0;i<env.getWidth();i++) {
			env.setNature(0, i, Cell.MTL);
			env.setNature(1, i, Cell.PLT);
		}
		ArrayList<Point> guards = new ArrayList<>();
		guards.add(new Point(2,0));
		
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(2,1));
		
		eng = new EngineContract(new EngineImpl());
		eng.init(env, guards, treasures);
		PlayerService p = new PlayerContract(new PlayerImpl());
		p.init(2, 4, eng);
		eng.setPlayer(p);
		
	}
	
	
	public void init2() {
		EnvironnementService env = new EnvironnementContract(new EnvironnementImpl());
		env.init(10, 10);
		for (int i=0;i<env.getWidth();i++) {
			env.setNature(0, i, Cell.MTL);
			env.setNature(1, i, Cell.PLT);
		}
		ArrayList<Point> guards = new ArrayList<>();
		guards.add(new Point(2,8));
		
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(2,7));
		
		eng = new EngineContract(new EngineImpl());
		eng.init(env, guards, treasures);
		PlayerService p = new PlayerContract(new PlayerImpl());
		p.init(2, 4, eng);
		eng.setPlayer(p);
		
	}
	
	// Test post-condition
	// Objectif de test : BombLeft
	@Test
	public void testBombLeftReussi() {
		 init1();
		
		eng.setNextCommand(Command.BombL);
		
		eng.Step();
		assertTrue(" bombe pas larguee",eng.getEnvironnement().cellContent(eng.getPlayer().getHeight(), eng.getPlayer().getWidth()-1).getItem().nature() ==  ItemType.Bomb );
	}
	
	// Test post-condition
	@Test
	public void testBombRightReussi() {
		
		init1();
		eng.setNextCommand(Command.BombR);
		
		eng.Step();
		assertTrue(" bombe pas larguee",eng.getEnvironnement().cellContent(eng.getPlayer().getHeight(), eng.getPlayer().getWidth()+1).getItem().nature() ==  ItemType.Bomb );
	}
	
	// Test Transition
	@Test
	public void testBombing_Guard_And_Recup_Tresor_Left_Reussi() {
		
		init1();
		eng.setNextCommand(Command.BombL);
		eng.Step();
		eng.setNextCommand(Command.Right);
		eng.Step();
		eng.setNextCommand(Command.Right);
		eng.Step();
		eng.setNextCommand(Command.BombL);
		eng.Step();
		eng.setNextCommand(Command.Right);
		eng.Step();
		
		
		assertTrue(" garde pas release treasure ou pas mort",eng.getEnvironnement().cellContent(2, 5).getItem().nature() ==  ItemType.Treasure
				&& !(eng.getEnvironnement().cellContent(2, 5) instanceof PlayerContract));

		
	}
	
	// Test Transition
	@Test
	public void testBombing_Guard_And_Recup_Tresor_Right_Reussi() {
		init2();
		
		eng.setNextCommand(Command.BombR);
		eng.Step();
		eng.setNextCommand(Command.Left);
		eng.Step();
		eng.setNextCommand(Command.Left);
		eng.Step();
		eng.setNextCommand(Command.BombR);
		eng.Step();
		eng.setNextCommand(Command.Left);
		eng.Step();
		
		assertTrue(" garde pas release treasure ou pas mort",eng.getEnvironnement().cellContent(2, 3).getItem().nature() ==  ItemType.Treasure
				&& !(eng.getEnvironnement().cellContent(2, 3) instanceof PlayerContract));

		
	}
    // Test Transition : Objectif tuer garde
	 //Conditions initiales : L0 = Guard.hgt = 2 and Guard.wdt = 0 
	 //and Player.hgt = 2 and Player.wdt = 4
	 //Opérations :
	/**
	 * Transition : L0 = NextCommand(BombL), Step() : Guard.Behavior = Right
	 * Transition : L1 = NextCommand(Right), Step() : Guard.Behavior = Right
	 * Transition : L2 = NextCommand(Right), Step() : Guard.Behavior = Right
	 * Transition : L3 = NextCommand(BombL), Step() : Guard.Behavior = Right
	 * Transition : L4 = NextCommand(Right), Step() = Guard.Behavior = Right
	 * Transition : L5 = NextCommand(Right), Step() = Guard = null
	 */
	// Oracle : Guard(L5) = null
	@Test
	public void testBombingGuardLeftReussi() {
		init1();
		
		eng.setNextCommand(Command.BombL);
		eng.Step();
		eng.setNextCommand(Command.Right);
		eng.Step();
		eng.setNextCommand(Command.Right);
		eng.Step();
		eng.setNextCommand(Command.BombL);
		eng.Step();
		eng.setNextCommand(Command.Right);
		eng.Step();
		
		assertTrue(" garde pas tue",eng.getGuards().size() == 0 );

		
	}
	
		// Test Transition : Objectif tuer garde
		// Conditions initiales : L0 = Guard.hgt = 2 and Guard.wdt = 8 
		// and Player.hgt = 2 and Player.wdt = 4
		// Opérations :
		/**
		 * Transition : L0 = NextCommand(BombR), Step() : Guard.Behavior = Left
		 * Transition : L1 = NextCommand(Left), Step() : Guard.Behavior = Left
		 * Transition : L2 = NextCommand(Left), Step() : Guard.Behavior = Left
		 * Transition : L3 = NextCommand(BombR), Step() : Guard.Behavior = Left
		 * Transition : L4 = NextCommand(Left), Step() : Guard.Behavior = Left
		 * Transition : L5 = NextCommand(left), Step() : Guard = null
		 */
		// Oracle : Guard(L5) = null
		@Test
		public void testBombingGuardRightReussi() {
			init2();
			
			eng.setNextCommand(Command.BombR);
			eng.Step();
			eng.setNextCommand(Command.Left);
			eng.Step();
			eng.setNextCommand(Command.Left);
			eng.Step();
			eng.setNextCommand(Command.BombR);
			eng.Step();
			eng.setNextCommand(Command.Left);
			eng.Step();
			
			assertTrue("garde pas tue",eng.getGuards().size() == 0 );

			
		}

}
