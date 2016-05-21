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
	protected Color color;
	
	public void step(SimState state) {
		Environment eState = ((Environment)state);
		if (stopObject){
			stopObj.stop();
			return;
		}
		
		Double2D movePosition = eState.getNewPosition(position);
		/*Double move = state.random.nextDouble() * 8;

		int i = move.intValue();
	
		if (i == 0){
			movePosition = new Double2D(position.x+1, position.y);
		}else if( i == 1){
			movePosition = new Double2D(position.x, position.y+1);
		}else if(i==2){
			 movePosition = new Double2D(position.x-1, position.y);
		}else if(i==3){
			movePosition = new Double2D(position.x, position.y-1);
		}else if(i==4){
			movePosition = new Double2D(position.x+1, position.y+1);
		}else if(i==5){
			movePosition = new Double2D(position.x-1, position.y-1);
		}else if(i==6){
			movePosition = new Double2D(position.x-1, position.y+1);
		}else if(i==7){
			movePosition = new Double2D(position.x+1, position.y-1);
		}*/
		
		//Environment eState = (Environment)state;
		if(movePosition.x < 0) movePosition.add(new Double2D(2, 0));
		if(movePosition.x >= eState.getGridWidth()) movePosition.subtract(new Double2D(2,0));
		if(movePosition.y  < 0) movePosition.add(new Double2D(0, 2));
		if(movePosition.y >= eState.getGridHeigh()) movePosition.subtract(new Double2D(0,2));

		if(!this.findObstacle(movePosition, eState)){
			this.position = movePosition;
			eState.environment.setObjectLocation(this, movePosition);	
		}else{
			this.position = movePosition.multiply(2);
		}
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
