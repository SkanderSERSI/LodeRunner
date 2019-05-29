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
import impl.EngineImpl;
import impl.EnvironnementImpl;
import impl.PlayerImpl;
import services.EngineService;
import services.EnvironnementService;
import services.GuardService;
import services.PlayerService;

public class EngineTest {
	
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
		guards.add(new Point(2,1));
		
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(2,5));
		
		eng = new EngineContract(new EngineImpl());
		eng.init(env, guards, treasures);
		
		PlayerService player = new PlayerContract(new PlayerImpl());
		player.init(2, 3, eng);
		eng.setPlayer(player);
		
	}
	
	public void init2() {
		EnvironnementService env = new EnvironnementContract(new EnvironnementImpl());
		env.init(10, 10);
		for (int i=0;i<env.getWidth();i++) {
			env.setNature(0, i, Cell.MTL);
			env.setNature(1, i, Cell.PLT);
		}
		ArrayList<Point> guards = new ArrayList<>();
		guards.add(new Point(2,0));
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(2,5));
		
		
		eng = new EngineContract(new EngineImpl());
		eng.init(env, guards, treasures);
		
		PlayerService player = new PlayerContract(new PlayerImpl());
		player.init(2, 2, eng);
		eng.setPlayer(player);
		
	}
	
	public void init3() {
		EnvironnementService env = new EnvironnementContract(new EnvironnementImpl());
		env.init(10, 10);
		for (int i=0;i<env.getWidth();i++) {
			env.setNature(0, i, Cell.MTL);
			env.setNature(1, i, Cell.PLT);
		}
		ArrayList<Point> guards = new ArrayList<>();
		guards.add(new Point(2,0));
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(2,5));
		
		eng = new EngineContract(new EngineImpl());
		eng.init(env, guards, treasures);
		
		PlayerService player = new PlayerContract(new PlayerImpl());
		player.init(2, 2, eng);
		eng.setPlayer(player);
		
	}
	
	public void init4() {
		EnvironnementService env = new EnvironnementContract(new EnvironnementImpl());
		env.init(10, 10);
		for (int i=0;i<env.getWidth();i++) {
			env.setNature(0, i, Cell.MTL);
			env.setNature(1, i, Cell.PLT);
		}
		ArrayList<Point> guards = new ArrayList<>();
		guards.add(new Point(2,0));
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(2,5));
		
		eng = new EngineContract(new EngineImpl());
		eng.init(env, guards, treasures);
		
		PlayerService player = new PlayerContract(new PlayerImpl());
		player.init(2, 2, eng);
		eng.setPlayer(player);	
	}
	
	public void init5() {
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
		treasures.add(new Point(2,6));
		
		eng = new EngineContract(new EngineImpl());
		eng.init(env, guards, treasures);
		
		PlayerService player = new PlayerContract(new PlayerImpl());
		player.init(2, 2, eng);
		eng.setPlayer(player);	
	}
	
	
	/**
	 * 
	 * Objectif de Test: step() reussi, pas de chevauchement entre les gardes
	 *  
	 * Cas de Test: step()
	 * 				
	 * 
	 *  Condition Initiale: Tout cases de la 1ere ligne sont en MTL, ceux de la deuxieme ligne en PLT 
	 *  - le 1er garde est en position (2,0), le 2eme garde en (2,1)  et le joueur en (2,3).
	 *  
	 *  
	 *  Operation
	 *  step()
	 *  
	 *  oracle:
	 *  - le 1er garde va aller en (2,1) essayer de se deplacer à droite il ne pourra pas, puisqu'il 
	 *  y'a deja un guard, le deuxieme réussi à se deplacer à droite en (2,2) et le joueur se deplace en (2,4).
	 */
	
	@Test
	public void TestStepChevauchement() {
		init1();
		eng.setNextCommand(Command.Right);		
		eng.Step();
		assertTrue("Il y a encore un joueur",eng.getEnvironnement().cellContent(2, 0).getCharacter() instanceof GuardService);
		
	}
	
	/**
	 * 
	 * Objectif de Test: step() garde mange le player
	 *  
	 * Cas de Test: step()
	 * 				
	 * 
	 *  Condition Initiale: Tout cases de la 1ere ligne sont en MTL, ceux de la deuxieme ligne en PLT 
	 *  le garde est en position (2,0) et le joueur en (2,2)
	 
	 *  Operation
	 *  step()
	 *  
	 *  oracle:  Le joueur va se deplacer en (2,3), ensuite il va essayer de se deplacer en bas il ne pourra pas, le garde va le suivre
	 *  le manger et prendre sa place.
	 */
	
	@Test
	public void TestStepKill() {
		init2();
		int pv = eng.getPv();
		eng.setNextCommand(Command.Right);
		eng.Step();
		eng.setNextCommand(Command.Down);
		eng.Step();
		eng.Step();
		
		assertTrue("Il y a encore un joueur",eng.getPv() == pv-1);		
	}
	
	/**
	 * 
	 * Objectif de Test: step() reussi, le garde suit le joueur
	 *  
	 * Cas de Test: step()
	 * 				
	 * 
	 *  Condition Initiale: Tout cases de la 1ere ligne sont en MTL, ceux de la deuxieme ligne en PLT
	 *  le garde est en position (2,0) et le joueur en (2,2)
	 *  le joueur se deplace à droite, le garde doit faire de même
	 *  
	 *  Operation
	 *  step()
	 */
	
	@Test
	public void TestStepFollowPlayer() {
		init3();
		eng.setNextCommand(Command.Right);
		int old_width_guard=0;
		for(GuardService g:eng.getGuards()) {
			old_width_guard = g.getWidth();
		}
		int old_width_player = eng.getPlayer().getWidth();
		eng.Step();
		assertTrue("le joueur s'est pas deplacé à droite",eng.getPlayer().getWidth() == (old_width_player +1));
		
		for (GuardService g : eng.getGuards()) {
			assertTrue("le gardien s'est pas deplacé à droite",g.getWidth() == (old_width_guard +1));
		}
		
	}
	
	/**
	 * 
	 * Objectif de Test: step() reussi garde tombe dans un trou en suivant le joueur
	 *  
	 * Cas de Test: step()
	 * 				
	 * 
	 *  Condition Initiale: Tout cases de la 1ere ligne sont en MTL, ceux de la deuxieme ligne en PLT 
	 *  le garde est en position (2,0) et le joueur en (2,2), 
	 *  
	 *  
	 *  Operation
	 *  step()
	 *  
	 *  oracle:le joueur fait un trou à gauche, le garde va suivre le joueur et tombe dans le trou.
	 */
	
	@Test
	public void TestStepTombeDansLeTrouReussi() {
		init4();
		eng.setNextCommand(Command.DigL);
		eng.Step();
		eng.Step();
		
		assertTrue("le gardien n'est pas tombé dans un trou",eng.getEnvironnement().cellContent(1, 1).getCharacter() != null);
		
	}
	
	/**
	 * Transition
	 * Objectif de Test: step() reussi le joueur prend le tresor après que le garde l'ai relaché en tombant dans un trou
	 *  
	 * Cas de Test: step()
	 * 				
	 * 
	 *  Condition Initiale: Tout cases de la 1ere ligne sont en MTL, ceux de la deuxieme ligne en PLT 
	 *  le garde est en position (2,0) et le joueur en (2,2),  un tresor en (2,1) .. 
	 
	 *  
	 *  Operation
	 *  step()
	 *  oracle: *  le joueur fait un trou à gauche, le garde va suivre le joueur et tombe dans le trou
	 *  en lachant le tresor dans la case du dessus, le joueur va à droite et se saisit du tresor.
	 */
	
	@Test
	public void TestStepJoueurRecupTresorSurGardeTombe() {
		init5();
		eng.setNextCommand(Command.DigL);
		eng.Step();
		// le gardien va recupérer  le tresor et tombe dans un trou en lachant le tresor dans la case au dessus de lui
		eng.Step();
		eng.setNextCommand(Command.Left);
		// le joueur recupére le tresor
		eng.Step();
		 
		assertTrue("le tresor n'a pas ete recupere", eng.getPlayer().getTresorsTrouves() == 1);
		
	}
	
	
	
}
