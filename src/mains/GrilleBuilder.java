package mains;

import contracts.EnvironnementContract;
import enums.Cell;
import impl.EnvironnementImpl;
import services.EnvironnementService;

public class GrilleBuilder {
	private int width;
	private int height;
	
	
	
	public GrilleBuilder(int width, int height) {

		this.width = width;
		this.height = height;
		
	}
	
	public EnvironnementService getGrille() {
		EnvironnementService env = new EnvironnementContract( new EnvironnementImpl() );
		env.init(this.height, this.width);
		for (int i=0;i<width;i++) {
			env.setNature(0, i, Cell.MTL);
			env.setNature(1, i, Cell.PLT);
		}
		
		env.setNature(2, 1, Cell.LAD);
		env.setNature(3, 1, Cell.LAD);
		env.setNature(4, 3, Cell.LAD);
		env.setNature(5, 3, Cell.LAD);
		
		for (int i=2;i<width-2;i++) {
			env.setNature(3, i, Cell.PLT);
		}
		
		env.setNature(2, 8, Cell.LAD);
		env.setNature(3, 8, Cell.LAD);
		env.setNature(4, 6, Cell.LAD);
		env.setNature(5, 6, Cell.LAD);
		
		env.setNature(6, 2, Cell.HDR);
		env.setNature(5, 1, Cell.PLT);
		env.setNature(5, 0, Cell.PLT);
		
		env.setNature(5, 4, Cell.MTL);
		env.setNature(5, 5, Cell.MTL);
		
		env.setNature(6, 7, Cell.HDR);
		env.setNature(5, 8, Cell.PLT);
		env.setNature(5, 9, Cell.PLT);
		
		return env;
	}
	
	public EnvironnementService getGrille_2() {
		EnvironnementService env = new EnvironnementContract( new EnvironnementImpl() );
		env.init(this.height,this.width);
		for (int i=0;i<width;i++) {
			env.setNature(0, i, Cell.MTL);
			env.setNature(1, i, Cell.PLT);
		}	
		env.setNature(2, 0, Cell.LAD);
		env.setNature(3, 0, Cell.LAD);
		
		env.setNature(3, 1, Cell.PLT);
		env.setNature(3, 2, Cell.PLT);
		env.setNature(3, 3, Cell.PLT);
		
		env.setNature(2, 4, Cell.LAD);
		env.setNature(3, 4, Cell.LAD);
		
		env.setNature(4, 1, Cell.LAD);
		env.setNature(5, 1, Cell.LAD);
		env.setNature(6, 1, Cell.LAD);
		
		env.setNature(6, 2, Cell.PLT);
		env.setNature(6, 3, Cell.PLT);
		env.setNature(6, 4, Cell.PLT);
		
		env.setNature(4, 4, Cell.HDR);
		env.setNature(4, 5, Cell.HDR);
		env.setNature(4, 6, Cell.HDR);
		
		env.setNature(4, 7, Cell.LAD);
		env.setNature(5, 7, Cell.LAD);
		env.setNature(6, 7, Cell.LAD);
		env.setNature(7, 7, Cell.LAD);
		env.setNature(8, 7, Cell.LAD);
		
		env.setNature(8, 6, Cell.PLT);
		env.setNature(8, 5, Cell.PLT);
		env.setNature(8, 4, Cell.PLT);
		env.setNature(8, 3, Cell.PLT);
		
		env.setNature(7, 8, Cell.PLT);
		env.setNature(7, 9, Cell.PLT);
		
		return env;
	}
	
	

}