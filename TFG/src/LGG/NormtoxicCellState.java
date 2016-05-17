package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class NormtoxicCellState implements TumorCellState{
	private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private int radium = 10;
	private Metabolism metabolism;
	private Color color = Color.RED;
	
	public NormtoxicCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
	}
	
	@Override
	public void executeState(Environment state, Cell cell) {
		Boolean isSufficientGlucose = metabolism.sufficientGlucose(state, cell.getPosition());
		Boolean isSufficientOxygen = metabolism.sufficientOxygen(state, cell.getPosition());

		
		if(!isSufficientOxygen && !isSufficientGlucose){
			//System.out.println("Insufficient oxygen and glucose for cell in position "+  cell.getPosition().toString());
			cell.setCellState(new NecroticCellState());
		}else if(!isSufficientOxygen){
			//System.out.println("Insufficient oxygen for cell in position "+ cell.getPosition().toString());
			cell.setCellState(new HypoxicCellState());
		}else if(!isSufficientGlucose){
			//System.out.println("Insufficient oxygen for cell in position "+  cell.getPosition().toString());
			cell.setCellState(new HypoglycemicCellState());
		}
	}
	
	public Color getColor(){
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
