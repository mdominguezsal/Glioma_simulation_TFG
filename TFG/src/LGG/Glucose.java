package LGG;

import sim.engine.SimState;
import sim.util.Double2D;



public class Glucose extends Molecule{
	private static final long serialVersionUID = 1L;
	boolean move = true;
	
	public Glucose(Environment status) {
		position = new Double2D((status.random.nextDouble() * status.getGridWidth() + 1), (status.random.nextDouble() * status.getGridHeigh() + 1));		
	}



}
