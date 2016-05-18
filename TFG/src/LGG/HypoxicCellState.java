package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class HypoxicCellState extends TumorCellState{
	private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private int radium = 10;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private Color color = Color.ORANGE;
	
	public HypoxicCellState() {
		// TODO Auto-generated constructor stub
	}

	public void executeState(Environment state, Cell cell) {
		Double randomD = state.random.nextDouble() * 200;
		int randomI = randomD.intValue();	
		if(randomI == 1){
			this.ChangeStateNormtoxicCellState(cell);	
		}else{
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
