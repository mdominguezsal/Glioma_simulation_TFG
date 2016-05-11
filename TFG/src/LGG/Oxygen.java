package LGG;

import sim.engine.SimState;
import sim.util.Double2D;

public class Oxygen extends Molecule{
	private static final long serialVersionUID = 1L;
	public Double2D position;
	
	public Oxygen(Environment environment) {
		position = new Double2D((environment.random.nextDouble() * environment.gridWidth + 1), (environment.random.nextDouble() * environment.gridHeight + 1));		
		
	}

	@Override
	public void step(SimState state) {
		boolean movePos = state.random.nextBoolean();
		boolean moveNeg = state.random.nextBoolean();

		Double2D movePosition = new Double2D(position.x, position.y);
		
		Double2D other = new Double2D(1,1);
		
		if (movePos){
			movePosition = this.position.add(other);
		}else if (moveNeg){
			movePosition = this.position.subtract(other);
		}
		position = movePosition;
		Environment t = (Environment)state;
		t.environtment.setObjectLocation(this, position);
	}

}
