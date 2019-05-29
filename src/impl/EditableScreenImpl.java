package impl;

import enums.Cell;
import services.EditableScreenService;

public class EditableScreenImpl extends ScreenImpl implements EditableScreenService  {

	public EditableScreenImpl() {
		super();
	}
	public void init(int height, int width) {
		super.init(height, width);
	}

	@Override
	public boolean Playable() {
		boolean res =  true;
		for (int i = 0; i < super.getHeight(); i++) {
			for (int j = 0; j < super.getWidth(); j++) {
				if (screen[i][j] == Cell.HOL) {
					res = false;
					
					return res;
				}
			}
		}
		
		for (int i = 0;i<super.getWidth();i++) {
			if (screen[0][i] != Cell.MTL) {
				res = false;
				
				return res;
			}
		}
		return res;
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		screen[x][y]  = c;	
	}
	

}
