package tests;

import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import org.junit.Test;
import contracts.EngineContract;
import contracts.PlayerContract;
import enums.Command;
import impl.EngineImpl;
import impl.PlayerImpl;
import mains.GrilleBuilder;
import services.EngineService;
import services.EnvironnementService;
import services.PlayerService;

public class CharacterTest {

	EngineService engine;
	PlayerService player;
	
	public void init1() {
		
		// Creation du level 1
		GrilleBuilder gb = new GrilleBuilder(10,10);
		EnvironnementService screen = gb.getGrille();
		ArrayList<Point> guards = new ArrayList<>();
		guards.add(new Point(4,1));
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(4,2));
	
		// Initialisation de depart et debut de la partie
		engine  = new EngineContract( new EngineImpl() );
		player = new PlayerContract( new PlayerImpl() );
		player.init(2,2,engine);
		engine.init(screen, guards, treasures);
		engine.setNextCommand(null);
		engine.setPlayer(player);
	}
	
	private void init2() {
		// Creation du level 1
			GrilleBuilder gb = new GrilleBuilder(10,10);
			EnvironnementService screen = gb.getGrille();
			ArrayList<Point> guards = new ArrayList<>();
			guards.add(new Point(4,1));
			ArrayList<Point> treasures = new ArrayList<Point>();
			treasures.add(new Point(4,2));
		
			// Initialisation de depart et debut de la partie
			engine  = new EngineContract( new EngineImpl() );
			player = new PlayerContract( new PlayerImpl() );
			player.init(5,6,engine);
			engine.init(screen, guards, treasures);
			engine.setNextCommand(null);
			engine.setPlayer(player);
		
	}
	
	/**
	 * Objectif de Test goLeft() reussi
	 * Cas de Test: player.goLeft()
	 * 
	 * Condition initiale: Environnement jouable 
	 * 	+ PLT sur la 2e ligne + case libre à gauche du joueur en position (2,2)
	 * 
	 * Operation player.left
	 * Oracle:
	 * Joueur deplacé en (2,1)
	 */
	@Test
	public void goLeftTestReussi() {
		init1();
		engine.setNextCommand(Command.Left);
		int wdt = player.getWidth();
		player.goLeft();
		assertTrue("Le joueur n'est pas aller à gauche", engine.getPlayer().getWidth() == wdt - 1);
	}
	
	/**
	 * Objectif de Test goRight() reussi
	 * Cas de Test: player.goRight()
	 * 
	 * Condition initiale: Environnement jouable 
	 * 	+ PLT sur la 2e ligne + case libre à droite du joueur en position (2,2)
	 * 
	 * Operation player.right
	 * Oracle:
	 * Joueur deplacé en (2,3)
	 */
	@Test
	public void goRightTestReussi() {
		init1();
		engine.setNextCommand(Command.Right);
		int wdt = player.getWidth();
		player.goRight();
		assertTrue("Le joueur n'est pas aller à droite", engine.getPlayer().getWidth() == wdt + 1);
	}
	
	/**
	 * Objectif de Test goDown() reussi
	 * Cas de Test: player.goDown()
	 * 
	 * Condition initiale: Environnement jouable 
	 * 	+ PLT sur la 2e ligne + une LAD sur la case (5,6) et (4,6), joueur sur (5,6)
	 * 
	 * Operation player.Down
	 * Oracle:
	 * Joueur deplacé en (4,6)
	 */
	@Test
	public void goDownTestReussi() {
		init2();
		engine.setNextCommand(Command.Down);
		player.goDown();
		assertTrue("Le joueur n'est pas aller en bas", engine.getEnvironnement().cellContent(4, 6).getCharacter() instanceof PlayerContract );
	}
	
	/**
	 * Objectif de Test goUp() reussi
	 * Cas de Test: player.goUp()
	 * 
	 * Condition initiale: Environnement jouable 
	 * 	+ PLT sur la 2e ligne + une LAD sur la case (5,6) et (4,6), joueur sur (5,6)
	 * 
	 * Operation player.Up
	 * Oracle:
	 * Joueur deplacé en (6,6)
	 */
	@Test
	public void goUpTestReussi() {
		init2();
		engine.setNextCommand(Command.Up);
		player.goUp();
		assertTrue("Le joueur n'est pas aller en haut", engine.getEnvironnement().cellContent(6, 6).getCharacter() instanceof PlayerContract );
	}


	
	
}
