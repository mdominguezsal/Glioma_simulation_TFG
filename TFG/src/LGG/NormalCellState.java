package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class NormalCellState extends CellState{
	private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private final static int MOTILITY_RATIO = 1;
	private final static int PROLIFERATION_RATIO = 1;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private int radium = 10;
	
	public NormalCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
		motility = new Motility(MOTILITY_RATIO);
		proliferation = new Proliferation(PROLIFERATION_RATIO);
		color = Color.GREEN;
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		Double randomD = state.random.nextDouble() * 200;
		int randomI = randomD.intValue();
		
		Boolean suffOxygen = this.metabolism.sufficientOxygen(state, cell.getPosition());
		Boolean suffGlucose = this.metabolism.sufficientGlucose(state, cell.getPosition());


		if(randomI == 1){
			cell.ChangeStateNormtoxicCellState();	
		}else{
			this.motility.Move(cell, state);
			if(randomI >= 195){
				
				if( !suffOxygen || suffGlucose)	cell.ChangeStateNecroticState();
				//Double randomApoptosis = state.random.nextDouble() * 200;
				//int randomApoptosisI = randomApoptosis.intValue();
			}
			if(randomI == 2) cell.ChangeStateNecroticState();
			if(randomI == 3) this.motility.Move(cell, state);
		if(randomI == 10) this.proliferation.proliferate(cell, state);
				//if(randomI < 10 && randomI > 1 ){
				
				//}else{
				//	if(randomI < 20 && randomI > 10 ){
				//	this.proliferation.proliferate(cell, state);
				//}
			}
			//}
			//}
		//}
	}
	
	
	public void necroticCellsAround(Environment state){
		
	}
}
