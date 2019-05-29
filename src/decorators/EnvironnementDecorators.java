package decorators;

import services.EnvironnementService;
import enums.Cell;
import services.CellContentService;

public class EnvironnementDecorators implements EnvironnementService {
	private final EnvironnementService delegate;

	public EnvironnementDecorators(EnvironnementService delegate) {
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
	public Cell cellNature(int x, int y) {
		return delegate.cellNature(x, y);
	}

	@Override
	public void init(int h, int w) {
		delegate.init(h, w);

	}

	@Override
	public void dig(int u, int v) {
		delegate.dig(u, v);

	}

	@Override
	public void fill(int u, int v) {
		delegate.fill(u, v);

	}

	@Override
	public Cell[][] getStateMatrice() {
		return delegate.getStateMatrice();
	}

	@Override
	public CellContentService cellContent(int x, int y) {
		return delegate.cellContent(x, y);
	}

	@Override
	public boolean Playable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		delegate.setNature(x, y, c);
		
	}

	@Override
	public CellContentService[][] getCharItems() {
		return delegate.getCharItems();
	}

}
