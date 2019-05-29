package mains;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;
import contracts.EngineContract;
import contracts.PlayerContract;
import enums.Command;
import impl.EngineImpl;
import impl.PlayerImpl;
import services.EngineService;
import services.EnvironnementService;
import services.PlayerService;

public class Main {

	public static void main(String[] args) {
		
		
		// Creation du level 1
		GrilleBuilder gb = new GrilleBuilder(10,10);
		EnvironnementService screen = gb.getGrille();
		ArrayList<Point> guards = new ArrayList<>();
		guards.add(new Point(4,1));
		ArrayList<Point> treasures = new ArrayList<Point>();
		treasures.add(new Point(4,2));
		treasures.add(new Point(4,7));
		//ajouter ce tresor pour augmenter la difficult�
		//treasures.add(new Point(6,0));
		treasures.add(new Point(6,9));
	
		// Initialisation de depart et debut de la partie
		EngineService engine  = new EngineContract( new EngineImpl() );
		PlayerService player = new PlayerContract( new PlayerImpl() );
		player.init(2,2,engine);
		engine.init(screen, guards, treasures);
		engine.setNextCommand(null);
		engine.setPlayer(player);
		engine.home();
		engine.afficher_grille();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir une commande : \n");
		String cmd_ch = sc.nextLine();
		Command cmd = null;
		
		while(!cmd_ch.equals("quit")) {
			switch (cmd_ch) {
			case "s":
				cmd = Command.Down;
				break;
			case "z":
				cmd = Command.Up;
				break;
			case "q":
				cmd = Command.Left;
				break;
			case "d":
				cmd = Command.Right;
				break;
			case "e":
				cmd = Command.DigR;
				break;
			case "a":
				cmd = Command.DigL;
				break;
			case "b":
				cmd = Command.BombL;
				break;
			case "n":
				cmd = Command.BombR;
				break;
			}
			
			engine.setNextCommand(cmd);
			engine.Step();
			
			engine.afficher_grille();
			
			if (engine.getPv() == 0) {
				break;
			}
			System.out.println("Veuillez saisir une commande : \n");
			cmd_ch = sc.nextLine();
			cmd = null;
		}
		
		System.out.println("\n***************** GAME OVER ******************");
		sc.close();
	}
}
