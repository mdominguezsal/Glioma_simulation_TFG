package LGG;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
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
		Double move = state.random.nextDouble() * 4;

		int i = move.intValue();
		System.out.print(i);
	
		if (i == 0){
			movePosition = new Double2D(position.x+1, position.y);
		}else if( i == 1){
			movePosition = new Double2D(position.x, position.y+1);
		}else if(i==2){
			 movePosition = new Double2D(position.x-1, position.y);
		}else if(i==3){
			movePosition = new Double2D(position.x, position.y-1);
		}
		Environment status = (Environment)state;
		if(movePosition.x < 0)movePosition = new Double2D(movePosition.x+2, movePosition.y);
		if(movePosition.x >= status.getGridWidth()) movePosition = new Double2D(movePosition.x-2, movePosition.y);
		if(movePosition.y  < 0) movePosition = new Double2D(movePosition.x, movePosition.y+2);
		if(movePosition.y >= status.getGridHeigh()) movePosition = new Double2D(movePosition.x, movePosition.y-2);
		
		this.position = movePosition;
		status.environtment.setObjectLocation(this, movePosition);
		
	}
	
	public void removeMolecule(){
		stopObject = true;
	}
	
	public void setStop(Stoppable stoppable){
		this.stopObj = stoppable;
	}
	

}
