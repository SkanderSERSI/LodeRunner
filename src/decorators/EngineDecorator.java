package decorators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;
import enums.Command;
import impl.Hole;
import impl.ItemImpl;
import services.EngineService;
import services.EnvironnementService;
import services.GuardService;
import services.PlayerService;

public class EngineDecorator implements EngineService {

	private EngineService delegate;

	public EngineDecorator(EngineService delegate) {
		this.delegate = delegate;
	}

	@Override
	public Command nextCommand() {
		return delegate.nextCommand();
	}

	@Override
	public EnvironnementService getEnvironnement() {
		return delegate.getEnvironnement();
	}

	@Override
	public PlayerService getPlayer() {
		return delegate.getPlayer();
	}

	@Override
	public Set<GuardService> getGuards() {
		return delegate.getGuards();
	}

	@Override
	public Set<ItemImpl> getTreasures() {
		return delegate.getTreasures();
	}

	@Override
	public void Step() {
		delegate.Step();
	}

	@Override
	public void init(EnvironnementService escreen, ArrayList<Point> guards, ArrayList<Point> treasures) {
		delegate.init(escreen, guards, treasures);
	}

	@Override
	public ArrayList<Hole> getHoles() {
		return delegate.getHoles();
	}

	@Override
	public void setPlayer(PlayerService p) {
		delegate.setPlayer(p);

	}

	@Override
	public void afficher_grille() {
		delegate.afficher_grille();

	}

	@Override
	public void setNextCommand(Command nextCommand) {
		delegate.setNextCommand(nextCommand);

	}

	@Override
	public void addGuard(GuardService g) {
		delegate.addGuard(g);
	}

	@Override
	public void addItem(ItemImpl item) {
		delegate.addItem(item);
	}

	@Override
	public ArrayList<Point> getPointsGuards() {
		return delegate.getPointsGuards();
	}

	@Override
	public ArrayList<Point> getPointsTreasures() {
		return delegate.getPointsTreasures();
	}

	@Override
	public int getPv() {
		return delegate.getPv();
	}

	@Override
	public void setPv(int pv) {
		delegate.setPv(pv);
	}

	@Override
	public void home() {
		delegate.home();
	}

	@Override
	public void restart(int lvl) {
		delegate.restart(lvl);
	}

	@Override
	public int getLvl() {
		return delegate.getLvl();
	}

	@Override
	public void setLvl(int i) {
		delegate.setLvl(i);
	}

	@Override
	public int getNbBombs() {
		return delegate.getNbBombs();
	}

	@Override
	public void decBombs() {
		delegate.decBombs();
	}

	@Override
	public void removeGuard(GuardService g) {
		delegate.removeGuard(g);
	}

}
