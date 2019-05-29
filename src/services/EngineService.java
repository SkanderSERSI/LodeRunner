package services;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;
import enums.Command;
import impl.Hole;
import impl.ItemImpl;

public interface EngineService {

	void init(EnvironnementService escreen, ArrayList<Point> guards, ArrayList<Point> treasures);
	Command nextCommand();
	EnvironnementService getEnvironnement();
	PlayerService getPlayer();
	Set<GuardService> getGuards();
	Set<ItemImpl> getTreasures();
	void Step();

	public ArrayList<Hole> getHoles();
	public void setPlayer(PlayerService p);
	public void addGuard(GuardService g);
	public void addItem(ItemImpl item);
	public void afficher_grille();
	public void setNextCommand(Command nextCommand);
	ArrayList<Point> getPointsGuards();
	ArrayList<Point> getPointsTreasures();
	int getPv();
	void setPv(int pv);
	void home();
	void restart(int lvl);
	int getLvl();
	void setLvl(int i);
	int getNbBombs();
	void decBombs();
	void removeGuard(GuardService g);
}
