package impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import contracts.GuardContract;
import contracts.PlayerContract;
import enums.Cell;
import enums.Command;
import enums.ItemType;
import mains.GrilleBuilder;
import services.CellContentService;
import services.EngineService;
import services.EnvironnementService;
import services.GuardService;
import services.PlayerService;

public class EngineImpl implements EngineService {

	private EnvironnementService escreen;
	private ArrayList<Point> points_guards_initiaux;
	private ArrayList<Point> points_treasures;
	private Set<GuardService> guards;
	private Set<ItemImpl> treasures;
	private Command nextCommand;
	private PlayerService player;
	private ArrayList<Hole> holes = new ArrayList<>();
	private int pv = 3;
	private int lvl = 1;
	private int nbBombs = 3;
	
	@Override
	public void init(EnvironnementService escreen, ArrayList<Point> guards, ArrayList<Point> treasures) {
		this.escreen = escreen;
		this.points_treasures = new ArrayList<>();
		this.points_guards_initiaux = new ArrayList<>();
		this.guards = new HashSet<GuardService>();
		GuardImpl.cpt = 0;
		for (Point p : guards) {
			GuardService g = new GuardContract(new GuardImpl());
			g.init(p.x, p.y, this);
			this.guards.add(g);
			CellContentService cc = escreen.cellContent(p.x, p.y);
			cc.setCharacter(g);
			this.points_guards_initiaux.add(p);
		}
		this.treasures = new HashSet<>();
		for (Point p : treasures) {
			this.points_treasures.add(p);
			ItemImpl t = new ItemImpl(p.x, p.y,ItemType.Treasure);
			escreen.cellContent(p.x, p.y).setItem(t);
			this.treasures.add(t);
		}
		


	}

	@Override
	public int getPv() {
		return pv;
	}
	
	@Override
	/** Non utilise **/
	public void removeGuard(GuardService g) {
//		GuardService g1 =  null;
//		for (GuardService guard : guards) {
//			if (guard.getId() == g.getId()) {
//				g1 = guard;
//			}
//		}
//		guards.remove(g1);
//		
//		for (Iterator<GuardService> iterator = guards.iterator(); iterator.hasNext();) {
//			GuardService guard = (GuardService) iterator.next();
//			if (guard.getId() == g.getId()) {
//				iterator.remove();
//			}
//		}
	}
	
	@Override 
	public void setPv(int pv) {
		this.pv = pv;
	}
	@Override
	public Command nextCommand() {
		return nextCommand;
	}

	@Override
	public EnvironnementService getEnvironnement() {
		return escreen;
	}

	@Override
	public PlayerService getPlayer() {
		return player;
	}

	@Override
	public Set<GuardService> getGuards() {
		return guards;
	}

	@Override
	public Set<ItemImpl> getTreasures() {
		return treasures;
	}

	@Override 
	public ArrayList<Point> getPointsGuards(){
		return points_guards_initiaux;
	}
	
	@Override 
	public ArrayList<Point> getPointsTreasures(){
		return points_treasures; 
	}

	@Override
	public ArrayList<Hole> getHoles() {
		return holes;
	}

	@Override
	public void setPlayer(PlayerService player) {
		getEnvironnement().cellContent(player.getHeight(), player.getWidth()).setCharacter(player);
		this.player = player;
		player.setEnvironnement(getEnvironnement());
	}

	public void setNextCommand(Command nextCommand) {
		this.nextCommand = nextCommand;
	}

	@Override
	public void addGuard(GuardService g) {
		getEnvironnement().cellContent(g.getHeight(), g.getWidth()).setCharacter(g);
		guards.add(g);
		g.setEnvironnement(getEnvironnement());
	}

	@Override
	public void addItem(ItemImpl item) {
		getEnvironnement().cellContent(item.getHeight(), item.getWidth()).setItem(item);
		treasures.add(item);
	}

	
	@Override
	public void Step() {
		
		@SuppressWarnings("unchecked")
		ArrayList<Hole> holes_copy = (ArrayList<Hole>) this.holes.clone();
		for (Hole hole : holes_copy) {
			hole.setTsc(hole.getTsc() + 1);
			if (hole.getTsc() == 7) {
				if(getEnvironnement().cellContent(hole.getX(), hole.getY()).getCharacter() instanceof GuardContract) {
					GuardService c = (GuardService) getEnvironnement().cellContent(hole.getX(), hole.getY()).getCharacter();
					for (int i=0;i<this.points_guards_initiaux.size();i++) {
						if(i == c.getId()) {
							getEnvironnement().cellContent(c.getHeight(), c.getWidth()).setCharacter(null);
							getEnvironnement().cellContent((int)this.points_guards_initiaux.get(i).getX(), (int)this.points_guards_initiaux.get(i).getY()).setCharacter(c);
							c.setHeight((int) this.points_guards_initiaux.get(i).getX());
							c.setWidth((int) this.points_guards_initiaux.get(i).getY());
						}
					}
				}
				if (getEnvironnement().cellContent(hole.getX(), hole.getY()).getCharacter() != null) {
					if (getEnvironnement().cellContent(hole.getX(), hole.getY()).getCharacter() instanceof PlayerContract) {
						restart(lvl);
						nextCommand = null;
					}
					if (getEnvironnement().cellContent(hole.getX(), hole.getY()).getCharacter() instanceof GuardImpl) {
						GuardService guard = (GuardService) getEnvironnement().cellContent(hole.getX(), hole.getY())
								.getCharacter();
						for (Point g : points_guards_initiaux) {
							if (g.x == guard.getHeight() && g.y == guard.getWidth()) {
								getEnvironnement().cellContent(g.x, g.y).setCharacter(guard);
							}
						}
						getEnvironnement().cellContent(hole.getX(), hole.getY()).setCharacter(null);

					}
				}
				
				getEnvironnement().fill(hole.getX(), hole.getY());
				holes.remove(hole);

			}
		}
		
		for (Iterator<GuardService> iterator = guards.iterator(); iterator.hasNext();) {
			GuardService guard = (GuardService) iterator.next();
			guard.step();
			if (guard.getCatched() == true) {
				break;
			}
			if(guard.getPv() == 0) {
				iterator.remove();
			}
		}
		
//		for (GuardService g : getGuards()) {
//			g.step();
//			if (g.getCatched() == true) {
//				break;
//			}
//		}
		getPlayer().step();
	}
	
	@Override
	public void home() {
		System.out.println("*********************************");
		System.out.println("***          LolRunner         **");
		System.out.println("*********************************" + "\n\n");
	}

	@Override
	public void afficher_grille() {
		
		System.out.println("Nombre de vies : " + pv + "\n");
		System.out.println("Nombre de bombes : " + nbBombs + "\n");
		System.out.println("Score          : " + player.getScore() + "\n\n");
		for (int i = getEnvironnement().getHeight() - 1; i >= 0; i--) {
			for (int j = 0; j < getEnvironnement().getWidth(); j++) {
				if (getEnvironnement().cellContent(i, j).getCharacter() == null) {
					if(getEnvironnement().cellContent(i, j).getItem() != null) {
						System.out.print("| " + getEnvironnement().cellContent(i, j).getItem() + "  ");
					}else {
						System.out.print("| " + "  " + " ");
					}
					
				} else if (getEnvironnement().cellContent(i, j).getCharacter() == player) {
					System.out.print("| " + getEnvironnement().cellContent(i, j).getCharacter() + "  ");
				} else {
					System.out.print("| " + getEnvironnement().cellContent(i, j).getCharacter() + " ");
				}

				if(getEnvironnement().getStateMatrice()[i][j] == Cell.EMP) {
					System.out.print("   " + " ");
				} else {
					System.out.print(getEnvironnement().getStateMatrice()[i][j] + " ");
				}
			}
			System.out.println("\n");
		}

		System.out.println("********************************************************************************************\n");
	}

	@SuppressWarnings("unused")
	@Override
	public void restart(int lvl) {
		switch(lvl) {
		case 1:
			
			/** On nettoie les anciennes references **/
			for (GuardService g : guards) {
				g = null;
			}
			
			for (ItemImpl item : treasures) {
				item = null;
			}
			
			player = null;
			
			System.gc();
			/** Fin du nettoyage **/
			
			ArrayList<Point> guards = new ArrayList<>();
			guards.add(new Point(4,1));
			ArrayList<Point> treasures = new ArrayList<Point>();
			treasures.add(new Point(4,2));
			treasures.add(new Point(4,7));
			//ajouter ce tresor pour augmenter la difficulté
			//treasures.add(new Point(6,0));
			treasures.add(new Point(6,9));
			this.nbBombs = 3;
			GrilleBuilder gb = new GrilleBuilder(10,10);
			EnvironnementService escreen = gb.getGrille();
			init(escreen, guards, treasures);
			PlayerService player = new PlayerContract( new PlayerImpl() );
			player.init(2,2,this);
			setPlayer(player);
			break;
		
		
		case 2:
			
			/** On nettoie les anciennes references **/
			for (GuardService g : this.guards) {
				g = null;
			}
			
			for (ItemImpl item : this.treasures) {
				item = null;
			}
			
			player = null;
			
			System.gc();
			/** Fin du nettoyage **/
			
			ArrayList<Point> guards_p = new ArrayList<>();
			guards_p.add(new Point(7,3));
			guards_p.add(new Point(9,4));
			//guards_p.add(new Point(8,9));
			
			ArrayList<Point> treasures_p = new ArrayList<Point>();
			treasures_p.add(new Point(2,5));
			treasures_p.add(new Point(7,2));
			treasures_p.add(new Point(8,8));
			
			GrilleBuilder gbb = new GrilleBuilder(10,10);
			EnvironnementService escreene = gbb.getGrille_2();
			init(escreene, guards_p, treasures_p);
			this.nbBombs = 3;
			PlayerService player1 = new PlayerContract( new PlayerImpl() );
			player1.init(2,2,this);
			setPlayer(player1);
			break;
		case 3:
			System.out.println("VOUS AVEZ GAGNE !");
			System.exit(0);
	}
		
		
	}

	@Override
	public int getLvl() {
		return lvl;
	}

	@Override
	public void setLvl(int i) {
		this.lvl = i;
	}
	
	@Override
	public int getNbBombs() {
		return this.nbBombs;
	}
	@Override 
	public void decBombs() {
		this.nbBombs--;
	}
}
