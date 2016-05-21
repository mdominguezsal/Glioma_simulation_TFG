package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class NormtoxicCellState extends TumorCellState{
	private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private final static int MOTILITY_RATIO = 1;
	private final static int PROLIFERATION_RATIO = 1;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private int radium = 10;


	public NormtoxicCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
		motility = new Motility(MOTILITY_RATIO);
		proliferation = new Proliferation(PROLIFERATION_RATIO);
		color = Color.RED;
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		Double randomD = state.random.nextDouble() * 200;
		int randomI = randomD.intValue();
		this.metabolism.metabolismExecution(state, cell);
		if(randomI == 1){
			//cell.ChangeStateNecroticState();
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
