package LGG;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public abstract class Molecule implements Steppable{
	public Double2D position;
	
	@Override
	public void step(SimState state) {
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
		Environment t = (Environment)state;
		if(movePosition.x < 0)movePosition = new Double2D(movePosition.x+2, movePosition.y);
		if(movePosition.x >= t.gridWidth) movePosition = new Double2D(movePosition.x-2, movePosition.y);
		if(movePosition.y  < 0) movePosition = new Double2D(movePosition.x, movePosition.y+2);
		if(movePosition.y >= t.gridHeight) movePosition = new Double2D(movePosition.x, movePosition.y-2);
		
		this.position = movePosition;
		t.environtment.setObjectLocation(this, movePosition);
		
	}
	
}
