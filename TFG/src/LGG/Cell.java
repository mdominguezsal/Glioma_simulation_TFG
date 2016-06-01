package LGG;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

public class Cell implements Steppable{
	private static final long serialVersionUID = 1L;
	
	private Double2D position;
	private CellState cellState;
	protected CauseOfDeath reasonDeath;
	private int radium = 5;
	
	public Cell(Environment state) {
		reasonDeath = CauseOfDeath.Alive;
		Boolean validPosition = false;
		while(!validPosition){
			this.position =  state.newCellPositionInit();
			if(!state.cellInPosition(this.position, this.hashCode(), this.radium)) validPosition = true;
		}
		if(!state.cellInPosition(this.position, this.hashCode(), this.radium)){
			Double randomCell = state.random.nextDouble()* 50;
			int i = randomCell.intValue();

			if(i == 0) {
				cellState = new NormtoxicCellState(state);
				cellState.incrementTypeOfCell(state);
			}else{ 
				cellState = new NormalCellState(state);
				cellState.incrementTypeOfCell(state);
			}
		}
	}

	public Cell(CellState cellState, Double2D position ) { 
		reasonDeath = CauseOfDeath.Alive;
		this.cellState = cellState;
		this.position = position;
	}
	

	@Override
	public void step(SimState state) {
		cellState.executeState((Environment) state, this);
		((Environment) state).environment.setObjectLocation(this, this.getPosition());
	}

	protected void ChangeStateNormtoxicCellState(Environment state, Double2D position){
		cellState.decrementTypeOfCell(state);
		this.setCellState(new NormtoxicCellState(state));
		cellState.incrementTypeOfCell(state);
	}
	
	protected void ChangeStateNecroticState(Environment state, CauseOfDeath reasonDeath){
		cellState.decrementTypeOfCell(state);
		this.setCellState(new NecroticCellState());
		this.reasonDeath = reasonDeath;
		if (reasonDeath == CauseOfDeath.Termozolomide){
			int value = state.getNumberOfNecroticByTermozolomideCells();
			value++;
			state.numberOfDeathByTermozolomideChangeValue(value);
		}
		cellState.incrementTypeOfCell(state);
	}
	
	protected void ChangeStateHypoglycemicState(Environment state, Double2D position){
		cellState.decrementTypeOfCell(state);
		this.setCellState(new HypoglycemicCellState(state));
		cellState.incrementTypeOfCell(state);
	}
	
	protected void ChangeStateHypoxicState(Environment state, Double2D position){
		cellState.decrementTypeOfCell(state);
		this.setCellState(new HypoxicCellState(state));
		cellState.incrementTypeOfCell(state);
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
	
	public double getGlucoseConsumptionNeeds(){
		return this.cellState.getGlucoseNeeds();
	}
	
	public double getOxygenConsumptionNeeds(){
		return this.cellState.getOxygenNeeds();
	}
	
	public String getReasonDeath() {
		return reasonDeath.toString();
	}
}

