package LGG;

import sim.engine.SimState;
import sim.util.Double2D;

public class Oxygen extends Molecule{
	private static final long serialVersionUID = 1L;
	Environment e;
	
	public Oxygen(Environment status) {
		position = new Double2D((status.random.nextDouble() * status.getGridWidth()), (status.random.nextDouble() * status.getGridHeigh()));		
		
	}


}
