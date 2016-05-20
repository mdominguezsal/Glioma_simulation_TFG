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
	protected Color color;
	private int movement;
	private int replication;
	
	public CellState() {
		
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
