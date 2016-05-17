package LGG;

import java.util.Iterator;

import sim.engine.SimState;
import sim.util.Bag;
import sim.util.Double2D;

public class Motility implements Behaviour{
	int ratio;

	public Motility(int motilityRatio) {
		this.ratio = motilityRatio;
	}

	public void Move(Cell cell,Environment eState){
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
				System.out.print("Move cell to:");
				System.out.print(newPosition);
				Environment status = (Environment)eState;
				status.environtment.setObjectLocation(cell, newPosition);
			}
		}
	}
	
	private Double2D assignPosition(SimState state, Double2D cellPosition){
		Double2D movePosition = null;
		Double move = state.random.nextDouble() * 4;	
		int randomI = move.intValue();	
		
		if (randomI == 0){
			movePosition = new Double2D(cellPosition.x+1, cellPosition.y);
		}else if( randomI == 1){
			movePosition = new Double2D(cellPosition.x, cellPosition.y+1);
		}else if(randomI == 2){
			movePosition = new Double2D(cellPosition.x-1, cellPosition.y);
		}else if(randomI == 3){
			movePosition = new Double2D(cellPosition.x, cellPosition.y-1);
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
