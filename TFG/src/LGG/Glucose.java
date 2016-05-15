package LGG;

import sim.engine.SimState;
import sim.util.Double2D;



public class Glucose extends Molecule{
	private static final long serialVersionUID = 1L;
	boolean move = true;
	
	public Glucose(Environment environment) {
		position = new Double2D((environment.random.nextDouble() * environment.gridWidth + 1), (environment.random.nextDouble() * environment.gridHeight + 1));		
	}



}
