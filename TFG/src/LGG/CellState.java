package LGG;

import sim.engine.SimState;

public interface CellState {
	public void executeState(SimState state, Cell cell);
}
