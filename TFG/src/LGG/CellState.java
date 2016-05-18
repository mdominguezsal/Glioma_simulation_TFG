package LGG;


import java.awt.Color;

import sim.engine.SimState;

public abstract class CellState {
	//private final int minOxygen;
	//private final int minGlucose;
	//private final float apoptosis;
	//private int radium = 10;
	
	//private Metabolism metabolism;
	//private Motility motility;
	//private Proliferation proliferation;
	private Color color;
	private int movement;
	private int replication;
	
	
	public CellState() {
		
	}

	protected void ChangeStateNormtoxicCellState(Cell cell){
		cell.setCellState(new NormtoxicCellState());
	}
	
	protected void ChangeStateNecroticState(Cell cell){
		cell.setCellState(new NecroticCellState());
	}
	
	protected void ChangeStateHypoglycemicState(Cell cell){
		cell.setCellState(new HypoglycemicCellState());
	}
	
	protected void ChangeStateHypoxicState(Cell cell){
		cell.setCellState(new HypoglycemicCellState());
	}
	
	public Color getColor(){
		return this.color;
	}

	public int getMovement(){
		return this.movement;
	}
	
	public int getReplication(){
		return this.replication;
	}

	public void executeState(Environment state, Cell cell) {	
	}

}
