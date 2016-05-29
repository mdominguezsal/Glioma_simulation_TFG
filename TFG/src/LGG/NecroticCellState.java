package LGG;

import java.awt.Color;

public class NecroticCellState extends CellState{
	private static final long serialVersionUID = 1L;

	public NecroticCellState() {
		color = Color.GRAY;
	}
	
	@Override
	public double getOxygenNeeds(){
		return 0;
	}
	
	@Override
	public double getGlucoseNeeds(){
		return 0;
	}
	
	@Override
	void executeState(Environment state, Cell cell) {
		//do nothing
	}
	
	@Override
	public void incrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfNecroticCells() + 1;
		state.numberOfNecroticChangeValue(newValue);
	}

	@Override
	public void decrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfNecroticCells() - 1;
		state.numberOfNecroticChangeValue(newValue);
	}
}
