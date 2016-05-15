package LGG;

import sim.engine.SimState;
import sim.util.Double2D;

public class Oxygen extends Molecule{
	private static final long serialVersionUID = 1L;
	Environment e;
	
	public Oxygen(Environment environment) {
		position = new Double2D((environment.random.nextDouble() * environment.gridWidth), (environment.random.nextDouble() * environment.gridHeight));		
		
	}


}
