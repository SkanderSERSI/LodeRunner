package impl;

import enums.Cell;
import services.ScreenService;

public class ScreenImpl implements ScreenService{

	private int height;
	private  int width;
	
	protected Cell[][] screen;
	
	public ScreenImpl() {}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Cell cellNature(int x, int y) {
		return screen[x][y];
	}

	@Override
	public void init(int h, int w) {
		this.height = h;
		this.width  = w;
		screen = new Cell[h][w];
		for (int i = 0; i <h; i++) {
			for (int j = 0; j < w; j++) {
				screen[i][j] =  Cell.EMP;
			}
		}
	}

	@Override
	public void dig(int u, int v) {
		if (screen[u][v] == Cell.PLT)
			screen[u][v] =  Cell.HOL;
	}

	@Override
	public void fill(int u, int v) {
		if (screen[u][v] == Cell.HOL)
			screen[u][v] = Cell.PLT;	
	}

	@Override
	public Cell[][] getStateMatrice() {
		return screen;
			
	}
	

}
