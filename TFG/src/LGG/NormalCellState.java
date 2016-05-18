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
	private Color color = Color.GREEN;
	
	public NormalCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
		motility = new Motility(MOTILITY_RATIO);
		proliferation = new Proliferation(PROLIFERATION_RATIO);
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		Double randomD = state.random.nextDouble() * 200;
		int randomI = randomD.intValue();
		//this.metabolism.
		if(randomI == 1){
			this.ChangeStateNormtoxicCellState(cell);	
		}else{
			//Double randomApoptosis = state.random.nextDouble() * 200;
			//int randomApoptosisI = randomApoptosis.intValue();

			if(randomI == 2){
				this.ChangeStateNecroticState(cell);
			}else{
				if(randomI < 10 && randomI > 1 ){
					this.motility.Move(cell, state);
				}else{
					if(randomI < 20 && randomI > 10 ){
						this.proliferation.proliferate(cell, state);
					}
				}
			}
		}
	}
}
