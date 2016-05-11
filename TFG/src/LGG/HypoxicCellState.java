package LGG;

import sim.engine.SimState;

public class HypoxicCellState implements TumorCellState{

	public HypoxicCellState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeState(SimState state, Cell cell) {
		// TODO Auto-generated method stub
		System.out.println("Hypoxic State");
	}

}
