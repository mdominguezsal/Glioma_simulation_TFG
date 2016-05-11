package LGG;

import sim.engine.SimState;

public class NormalCellState implements CellState{
	private final static int MIN_OXYGEN = 3;
	private final static int MIN_GLUCOSE = 6;
	private final static float APOPTOSIS = 1;
	private int radium = 10;
	private Metabolism metabolism;
	
	public NormalCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
	}

	@Override
	public void executeState(SimState state, Cell cell) {
		// TODO Auto-generated method stub
		
	}

	private void ChangeStateNormtoxicCellState(Cell cell){
		cell.cellState = new NormtoxicCellState();
	}
	



}
