package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class HypoxicCellState implements TumorCellState{
	private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private int radium = 10;
	private Metabolism metabolism;
	private Color color = Color.ORANGE;
	
	public HypoxicCellState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		// TODO Auto-generated method stub
		System.out.println("Hypoxic State");
	}

	public Color getColor(){
		return this.color;
	}
}
