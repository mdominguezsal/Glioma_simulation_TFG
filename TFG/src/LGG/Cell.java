package LGG;

import java.util.Random;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class Cell implements Steppable{
	int x; 
	int y;
	public Double2D position;

	public Cell(Environment environment) { 
		Random rand = new Random();
		double x = (rand.nextDouble() * environment.gridWidth + 1);
		double y = (rand.nextDouble() * environment.gridHeight + 1);
		position = new Double2D(x, y);
		System.out.println("x: "+x+" y: "+y);
		// TODO Auto-generated constructor stub
	}
	
	public Cell(Double2D p) {
		this.position = p;
		//System.out.println("new cell at x: "+x+" y: "+y);
		// TODO Auto-generated constructor stub
	}

	public void step(SimState state) {
		Environment t = (Environment)state;

		Expand(t);
		Move(t);
	}

	private void Expand(Environment state){
	boolean copyPos = state.random.nextBoolean();;
		//boolean copyPosX = state.random.nextBoolean();
		//boolean copyPosY = state.random.nextBoolean();
		//boolean copyNegY = state.random.nextBoolean();
		Double2D copy = new Double2D(x,y);
		
		Double2D other = new Double2D(1,1);
		
		if (copyPos){
			copy = this.position.add(other);
		}else if (copyPos){
			copy = this.position.subtract(other);
		}
		/*}else if (copyNegY){
			copyY = y - 1;
		}else{
			copyX = x - 1;
		}*/
			
			
			
		boolean canCopy = CheckCell(copy, state);
		if (canCopy){
			Cell copyCell = new Cell(copy);
			
			state.cells.add(copyCell);
			Double2D d = new Double2D(copyCell.x, copyCell.y);
			state.environtment.setObjectLocation(copyCell, d);
			state.schedule.scheduleRepeating(copyCell);		
			System.out.println("STEP: copy to x: "+x+" y: "+y);
		}else{
			System.out.println("STEP: NOT COPY");
		}
	}

	private void Move(Environment state){
		
		boolean movePos = state.random.nextBoolean();
		//boolean movePosY = state.random.nextBoolean();
		boolean moveNeg = state.random.nextBoolean();
		//int newX = x;
		//int newY = y;
		Double2D movePosition = new Double2D(x,y);
		
		Double2D other = new Double2D(1,1);
		
		if (movePos){
			movePosition = this.position.add(other);
		}else if (moveNeg){
			movePosition = this.position.subtract(other);
		}

		
		boolean move = CheckCell(movePosition, state);

		if (move){
			System.out.println("STEP: x: "+x+" y: "+y);
			Double2D d = new Double2D(x, y);
			state.environtment.setObjectLocation(this, d);		
		}else{
			System.out.println("STEP: NOT MOVE");			
		}
	}
	
	private boolean CheckCell(Double2D d, Environment state){
		for (Cell cell : state.cells){
			state.environtment.getNeighborsExactlyWithinDistance(cell.position, d.distance(position));
			if (cell.position == d){
				return false;
			}
		}
		return true;
	}
}
