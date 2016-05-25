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
	private int radium = 5;
	private int distance;
	
	public Cell(Environment state) { 
		Boolean validPosition = false;
		while(!validPosition){
			this.position =  state.newCellPositionInit();
			if(!state.cellInPosition(this.position, this.hashCode(), this.radium)) validPosition = true;
		}
		if(!state.cellInPosition(this.position, this.hashCode(), this.radium)){
			Double proliferationRate = this.proliferationRate(state, position);
			//System.out.println("x: "+position.x+" y: "+position.y);
			Double randomCell = state.random.nextDouble()* 100;
			int i = randomCell.intValue();
			//this.cellState = new NecroticCellState(); 
			if(i == 0) {
				//this.
				cellState = new NormtoxicCellState(state.getOxygenConsumtionNormotoxic(), state.getGluConsumtionNormotoxic(), state.getRandomchangetoNecrotic(), state.getMotilityRatioNormotoxic(), state.getProliferationRatioNormotoxic(), state.getTermozolimide());
				int nTumorCells = state.getnNormtoxicCells();
				state.setnNormtoxicCells(nTumorCells++);
			}else{ 
				cellState = new NormalCellState(state.getOxygenConsumtionNormal(), state.getGluConsumtionNormal(), state.getRandomchangetoNecrotic(), state.getRandomChangetoTumor(), state.getMotilityRatioNormal(), state.getMotilityRatioNormal());
			}
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

	

	protected void ChangeStateNormtoxicCellState(Environment state, Double2D position){
		 double proliferationRate = this.proliferationRate(state, position);
		 
		 int nNormtoxic = state.getnNormtoxicCells();
		 state.setnNormtoxicCells(nNormtoxic++);
		 
		this.setCellState(new NormtoxicCellState(state.getOxygenConsumtionNormotoxic(), state.getGluConsumtionNormotoxic(), state.getRandomchangetoNecrotic(), state.getMotilityRatioNormotoxic(), state.getProliferationRatioNormotoxic(), state.getTermozolimide()));
	}
	
	protected void ChangeStateNecroticState(Environment state){
		 int nNecrotic = state.getnNecroticCells();
		 state.setnNormtoxicCells(nNecrotic++);
		 
		this.setCellState(new NecroticCellState());
	}
	
	protected void ChangeStateHypoglycemicState(Environment state, Double2D position){
		 double proliferationRate = this.proliferationRate(state, position);
		 
		 int nHypoglicemic = state.getnHypoglicemicCells();
		 state.setnNormtoxicCells(nHypoglicemic++);
		 
		this.setCellState(new HypoglycemicCellState(state.getOxygenConsumtionNormotoxic(), state.getGluConsumtionNormotoxic(), state.getRandomchangetoNecrotic(), state.getMotilityRatioNormotoxic(), state.getProliferationRatioNormotoxic(), state.getTermozolimide()));
	}
	
	protected void ChangeStateHypoxicState(Environment state, Double2D position){
		 double proliferationRate = this.proliferationRate(state, position);
		 
		 int nHypoxic = state.getnHypoxicCells();
		 state.setnNormtoxicCells(nHypoxic++);
		 
		this.setCellState(new HypoxicCellState(state.getOxygenConsumtionNormotoxic(), state.getGluConsumtionNormotoxic(), state.getRandomchangetoNecrotic(), state.getMotilityRatioNormotoxic(), state.getProliferationRatioNormotoxic(), state.getTermozolimide()));
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


	public int getRadium(){
		return this.radium;
	}
	
	
	
	protected double proliferationRate(Environment status, Double2D position){
		double proliferationRate = status.concentration(position, Oxygen.class) * status.concentration(position, Glucose.class) * status.getProliferationConstant() * (1-status.getT());
		return proliferationRate;
	}
}

