package LGG;

import java.util.Iterator;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Double2D;

public abstract class Molecule implements Steppable{
	private static final long serialVersionUID = 1L;
	public Double2D position;
	boolean stopObject = false;
	private Stoppable stopObj;
	
	@Override
	public void step(SimState state) {
		if (stopObject){
			stopObj.stop();
			return;
		}
		Double2D movePosition = null;
		Double move = state.random.nextDouble() * 8;

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
		}
		
		Environment status = (Environment)state;
		if(movePosition.x < 0) movePosition.add(new Double2D(2, 0));
		if(movePosition.x >= status.getGridWidth()) movePosition.subtract(new Double2D(2,0));
		if(movePosition.y  < 0) movePosition.add(new Double2D(0, 2));
		if(movePosition.y >= status.getGridHeigh()) movePosition.subtract(new Double2D(0,2));

		if(!this.findObstacle(movePosition, status)){
			this.position = movePosition;
			status.environment.setObjectLocation(this, movePosition);	
		}else{
			this.position = movePosition.multiply(2);
		}
	}
	
	private boolean findObstacle(Double2D newPosition,  Environment state){
		Bag objectsList = state.environment.getNeighborsExactlyWithinDistance(newPosition, 10);
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
