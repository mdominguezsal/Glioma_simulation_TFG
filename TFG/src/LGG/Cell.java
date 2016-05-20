package LGG;

import java.util.Iterator;
import java.util.Random;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;
import sim.util.Double2D;

public class Cell implements Steppable{
	private Double2D position;
	private CellState cellState;
	
	public Cell(Environment state) { 
		this.position = new Double2D((state.random.nextDouble() * (state.getGridWidth()/3)*2 - (state.getGridWidth()/3)), (state.random.nextDouble() * (state.getGridHeigh()/3)*2)-(state.getGridWidth()/3));
		System.out.println("x: "+position.x+" y: "+position.y);

		Double randomCell = state.random.nextDouble()* 25;
		int i = randomCell.intValue();
		//this.cellState = new NecroticCellState(); 
		if(i < 3) {
			//this.
			cellState = new NormtoxicCellState();
		}else{
			cellState = new NormalCellState();
		}
	}
	
	public Cell(CellState cellState, Double2D position ) { 
		this.cellState = cellState;
		this.position = position;
	}
	
	public Cell(Double2D p) {
		this.position = p;
	}


	@Override
	public void step(SimState state) {
		cellState.executeState((Environment) state, this);
		((Environment) state).environment.setObjectLocation(this, this.getPosition());
		//Environment t = (Environment)state;
		//Expand(t);
		//Move(t);
	}

	/*public void Expand(Environment state){
		boolean copyPos = state.random.nextBoolean();
		//boolean copyPosX = state.random.nextBoolean();
		//boolean copyPosY = state.random.nextBoolean();
		//boolean copyNegY = state.random.nextBoolean();
		Double2D copy = new Double2D(position.x, position.y);
		
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
		}
	
		if (canCopy){
			Cell copyCell = new Cell(copy);
		
			//state.cells.add(copyCell);
			state.environtment.setObjectLocation(copyCell, copyCell.position);
			state.schedule.scheduleRepeating(copyCell);		
			System.out.println("STEP: copy to x: "+copyCell.position.x+" y: "+copyCell.position.y);
		}else{
			System.out.println("STEP: NOT COPY");
		}
	}*/

	

	protected void ChangeStateNormtoxicCellState(){
		this.setCellState(new NormtoxicCellState());
	}
	
	protected void ChangeStateNecroticState(){
		this.setCellState(new NecroticCellState());
	}
	
	protected void ChangeStateHypoglycemicState( ){
		this.setCellState(new HypoglycemicCellState());
	}
	
	protected void ChangeStateHypoxicState( ){
		this.setCellState(new HypoxicCellState());
	}
	
	
	public Double2D getPosition(){
		return this.position;
	}
	
	public void setPosition(Double2D p){
		this.position = p;
	}
	
	public CellState getCellState(){
		return this.cellState;
	}
	
	public void setCellState(CellState c){
		this.cellState = c;
	}

}

