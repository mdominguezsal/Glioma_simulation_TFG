package LGG;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Double2D;

public class Molecule implements Steppable{
	private static final long serialVersionUID = 1L;
	public Double2D position;
	boolean stopObject = false;
	private Stoppable stopObj;
		
	public void step(SimState state) {
		Environment eState = ((Environment)state);
		if (stopObject){
			stopObj.stop();
			return;
		}
		
		Double2D movePosition = eState.newPosition(position);
		
		//Valid movement (not outside the grid)
		if(movePosition.x < 0) movePosition.add(new Double2D(2, 0));
		if(movePosition.x >= eState.getGridWidth()) movePosition.subtract(new Double2D(2,0));
		if(movePosition.y  < 0) movePosition.add(new Double2D(0, 2));
		if(movePosition.y >= eState.getGridHeight()) movePosition.subtract(new Double2D(0,2));

		this.position = movePosition;
		eState.environment.setObjectLocation(this, movePosition);	
	}

	public void removeMolecule(){
		stopObject = true;
	}
	
	public void setStop(Stoppable stoppable){
		this.stopObj = stoppable;
	}
}
