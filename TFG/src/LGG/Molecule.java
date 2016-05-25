package LGG;

import java.util.Iterator;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Double2D;
import java.awt.Color;

public class Molecule implements Steppable{
	private static final long serialVersionUID = 1L;
	public Double2D position;
	boolean stopObject = false;
	private Stoppable stopObj;
	private Double radium = (double) 5;
	
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
		if(movePosition.y >= eState.getGridHeigh()) movePosition.subtract(new Double2D(0,2));

		//if(!this.findObstacle(movePosition, eState)){
			this.position = movePosition;
			eState.environment.setObjectLocation(this, movePosition);	
		//}else{
		//	this.position =  new Double2D (this.position.x - 2*(movePosition.x), this.position.y - 2*(movePosition.y));
		//}
	}
	
	protected boolean findObstacle(Double2D newPosition,  Environment state){
		Bag objectsList = state.environment.getNeighborsExactlyWithinDistance(newPosition, radium);
				//.getObjectsAtLocation(newPosition);	
		if(objectsList == null) return false;
		Iterator<Object> i = objectsList.iterator();
		while(i.hasNext()){
			Object obj = i.next();
			if(obj.getClass() == Cell.class){
				return true;
			}
		}
		return false;
	}
	public void removeMolecule(){
		stopObject = true;
	}
	
	public void setStop(Stoppable stoppable){
		this.stopObj = stoppable;
	}

	

}
