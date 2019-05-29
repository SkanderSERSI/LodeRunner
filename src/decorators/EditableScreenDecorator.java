package decorators;

import enums.Cell;
import services.EditableScreenService;

public class EditableScreenDecorator implements EditableScreenService {
	
	private final EditableScreenService delegate; 
	
	public EditableScreenDecorator(EditableScreenDecorator delegate){
		this.delegate = delegate;
	}

	@Override
	public boolean Playable() {
		return delegate.Playable();
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		delegate.setNature(x, y, c);
		
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
		return delegate.cellNature(x,y);
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

}
