package impl;

import services.CellContentService;
import services.EnvironnementService;

public class EnvironnementImpl extends EditableScreenImpl implements EnvironnementService{
	
	protected CellContentService[][] char_items;
	
	public EnvironnementImpl() {
		super();
	}

	public void init(int height, int width) {
		super.init(height, width);
		char_items = new CellContent[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				char_items[i][j] = new CellContent();
			}
		}
	}

	@Override
	public CellContentService cellContent(int x, int y) {
		
		return char_items[x][y];
	}
	
	public CellContentService[][] getCharItems(){
		return char_items;
	}

}
