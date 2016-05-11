package LGG;

import sim.engine.SimState;

public class NormtoxicCellState implements TumorCellState{
	private final static int MIN_OXYGEN = 3;
	private final static int MIN_GLUCOSE = 6;
	private final static float APOPTOSIS = 1;
	private int radium = 10;
	private Metabolism metabolism;
	
	public NormtoxicCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
	}
	
	@Override
	public void executeState(SimState state, Cell cell) {
		if (metabolism.sufficientOxygen(state, cell.position)){
			System.out.println("Sufficient oxygen for cell in position "+ cell.position.toString());
		}else{
			System.out.println("Insufficient oxygen for cell in position "+ cell.position.toString());
			cell.cellState = new HypoxicCellState();
		}
		
		/*if (metabolism.sufficientGlucose(state, cell.position)){
			System.out.println("Sufficient oxygen for cell in position "+ cell.position.toString());
		}else{
			System.out.println("Insufficient oxygen for cell in position "+ cell.position.toString());
			cell.cellState = new HypoglycemicCellState();
		}*/
		
	}
	

}
