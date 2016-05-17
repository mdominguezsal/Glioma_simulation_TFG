package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class NecroticCellState implements CellState{
	//private final static int MIN_OXYGEN = 1;
	//private final static int MIN_GLUCOSE = 1;
	//private final static float APOPTOSIS = 1;
	//private int radium = 10;
	//private Metabolism metabolism;
	private Color color = Color.GRAY;
	
	public NecroticCellState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public int getMovement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getReplication() {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
