package decorators;

import services.EngineService;
import services.EnvironnementService;
import services.PlayerService;

public class PlayerDecorator implements PlayerService {
	
	private final PlayerService delegate;
	
	public PlayerDecorator(PlayerService delegate) {
		this.delegate = delegate;
	}

	@Override
	public int getHeight() {
		return delegate.getHeight();
	}

	@Override
	public int getWidth() {
		return delegate.getWidth();
	}

	@Override
	public void goLeft() {
		delegate.goLeft();
	}

	@Override
	public void goRight() {
		delegate.goRight();
	}

	@Override
	public void goUp() {
		delegate.goUp();
	}

	@Override
	public void goDown() {
		delegate.goDown();
	}

	@Override
	public EnvironnementService getEnvi() {
		return delegate.getEnvi();
	}

	@Override
	public EngineService getEngine() {
		return delegate.getEngine();
	}

	@Override
	public void step() {
		delegate.step();
	}

	@Override
	public void setHeight(int hgt) {
		delegate.setHeight(hgt);
	}

	@Override
	public void setWidth(int wdt) {
		delegate.setWidth(wdt);
	}

	@Override
	public void setEnvironnement(EnvironnementService es) {
		delegate.setEnvironnement(es);
		
	}

	@Override
	public int getScore() {
		return delegate.getScore();
	}

	@Override
	public void setScore(int score) {
		delegate.setScore(score);
	}

	@Override
	public int getTresorsTrouves() {
		return delegate.getTresorsTrouves();
	}

	@Override
	public void incTresors() {
		delegate.incTresors();
	}

	@Override
	public void init(int height, int width, EngineService eng) {
		delegate.init(height, width, eng);
	}
}
