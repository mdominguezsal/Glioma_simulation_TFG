package LGG;


import java.awt.Color;

import sim.engine.SimState;

public interface CellState {
	//public void executeState(SimState state, Cell cell);
	public Color getColor();
	public void executeState(Environment state, Cell cell);
}
