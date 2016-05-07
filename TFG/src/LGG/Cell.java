package LGG;

import java.util.Random;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Cell implements Steppable{
	public int y;
	public int x;

	public Cell(Environment environment) {
		Random rand = new Random();
		x = (int)(rand.nextDouble() * 50 + 1);
		y = (int)(rand.nextDouble() * 50 + 1);
		
		System.out.println("x: "+x+" y: "+y);
		// TODO Auto-generated constructor stub
	}
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		System.out.println("new cell at x: "+x+" y: "+y);
		// TODO Auto-generated constructor stub
	}

	public void step(SimState state) {
		// TODO Auto-generated method stub
		Environment t = (Environment)state;
		Expand(t);
		Move(t);
	}

	private void Expand(Environment state){
		boolean copyPosX = state.random.nextBoolean();
		boolean copyPosY = state.random.nextBoolean();
		boolean copyNegY = state.random.nextBoolean();
		int copyX = x;
		int copyY = y;
		if (copyPosX){
			copyX = x + 1;
		}else if (copyPosY){
			copyY = y + 1;
		}else if (copyNegY){
			copyY = y - 1;
		}else{
			copyX = x - 1;
		}
		boolean canCopy = CheckCell(copyX, copyY, state);
		if (canCopy){
			Cell copyCell = new Cell(copyX, copyY);
			state.cells.add(copyCell);
			state.particleSpace.setObjectLocation(copyCell, copyCell.x, copyCell.y);
			state.schedule.scheduleRepeating(copyCell);		
			System.out.println("STEP: copy to x: "+x+" y: "+y);
		}else{
			System.out.println("STEP: NOT COPY");
		}
	}

	private void Move(Environment state){
		boolean movePosX = state.random.nextBoolean();
		boolean movePosY = state.random.nextBoolean();
		boolean moveNegY = state.random.nextBoolean();
		int newX = x;
		int newY = y;
		if (movePosX){
			newX = x + 1;
		}else if (movePosY){
			newY = y + 1;
		}else if (moveNegY){
			newY = y - 1;
		}else{
			newX = x - 1;
		}
		
		boolean move = CheckCell(newX, newY, state);

		if (move){
			x = newX;
			y = newY;
			System.out.println("STEP: x: "+x+" y: "+y);
			state.particleSpace.setObjectLocation(this, x, y);			
		}else{
			System.out.println("STEP: NOT MOVE");			
		}
	}
	
	private boolean CheckCell(int x, int y, Environment state){
		for (Cell cell : state.cells){
			if (cell.x == x && cell.y == y){
				return false;
			}
		}
		return true;
	}
}
