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
		System.out.print("ratio:");
		System.out.print(randomI == this.ratio);
		System.out.print("\n");
		if(randomI == this.ratio){
			Double2D newPosition = assignPosition(eState, cellPosition);
			System.out.print("check:");
			System.out.print(cellInPosition(newPosition, eState));
			System.out.print("\n");
			if(!cellInPosition(newPosition, eState)){
				System.out.print("Proliferate cell to:");
				System.out.print(newPosition);
				Environment status = (Environment)eState;
				Cell newCell = new Cell(cell.getCellState(),newPosition);
				status.environtment.setObjectLocation(newCell, newPosition);
				status.schedule.scheduleRepeating(newCell);
			}
		}
	}
	
	
	private Double2D assignPosition(SimState state, Double2D cellPosition){
		Double2D movePosition = null;
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
	}

	
	private boolean cellInPosition(Double2D position, Environment state){
		Bag b = state.environtment.getObjectsAtLocation(position);	
		if(b == null) return false;
		System.out.print("\n");
		Iterator i =b.iterator();
		while(i.hasNext()){
			Object obj = i.next();
			if(obj.getClass() == Cell.class){
				return true;
			}
		}
		return false;
	}
}
