package services;

public interface EnvironnementService extends EditableScreenService {

	/* Observators */
	/** pre: y>=0 && y<width() && x>0 && x<height()
	 */
	public CellContentService cellContent(int x,int y);
	
	public CellContentService[][] getCharItems();
	
}
