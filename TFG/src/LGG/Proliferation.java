package LGG;

import java.util.Iterator;

import sim.engine.SimState;
import sim.util.Bag;
import sim.util.Double2D;

public class Proliferation implements Behaviour{
	int ratio;
		
	public Proliferation(int proliferationRatio) {
		this.ratio = proliferationRatio;
	}

	public void proliferate(Cell cell,Environment eState){
		Double2D cellPosition = cell.getPosition();
		Double randomD = eState.random.nextDouble() * 2;
		int randomI = randomD.intValue();
		System.out.print("proliferate:");
		//System.out.print(randomI == this.ratio);
		//System.out.print("\n");
		//if(randomI == this.ratio){
		Double2D newPosition = eState.newCopyCellPosition(cell.getPosition());
			if(!eState.cellInPosition(newPosition, this.hashCode(), cell.getRadium())){

				Environment status = (Environment)eState;
				Cell newCell = new Cell(cell.getCellState(),newPosition);
				status.environment.setObjectLocation(newCell, newPosition);
				status.schedule.scheduleRepeating(newCell);
			//}
		}
	}
	
	
	/*private Double2D assignPosition(Environment state, Double2D cellPosition){
		Double2D movePosition = state.getNewPosition(cellPosition);
		Double move = state.random.nextDouble() * 4;	
		int randomI = move.intValue();	
		
		if (randomI == 0){
			movePosition = new Double2D(cellPosition.x+5, cellPosition.y);
		}else if( randomI == 1){
			movePosition = new Double2D(cellPosition.x, cellPosition.y+5);
		}else if(randomI == 2){
			movePosition = new Double2D(cellPosition.x-5, cellPosition.y);
		}else if(randomI == 3){
			movePosition = new Double2D(cellPosition.x, cellPosition.y-5);
		}	

		return movePosition;
	}*/

	/*
	private boolean cellInPosition(Double2D position, Environment state){
		Bag b = state.environment.getNeighborsExactlyWithinDistance(position, radium);
		if(b == null) return false;
		System.out.print("\n");
		Iterator i = b.iterator();
		while(i.hasNext()){
			Object obj = i.next();
			if(obj.getClass() == Cell.class){
				return true;
			}
		}
		return false;
	}*/
}
