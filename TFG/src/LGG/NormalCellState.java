package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class NormalCellState implements CellState{
	private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private int radium = 10;
	private Metabolism metabolism;
	private Color color = Color.GREEN;
	
	public NormalCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		Double randomTumor = state.random.nextDouble() * 200;
		int randomTumorI = randomTumor.intValue();	
		if(randomTumorI == 1){
			this.ChangeStateNormtoxicCellState(cell);	
		}else{
			Double randomApoptosis = state.random.nextDouble() * 200;
			int randomApoptosisI = randomApoptosis.intValue();

			if(randomApoptosisI == 1){
				this.ChangeStateNecrotic(cell);
			}else{
				if(randomApoptosisI < 10 && randomApoptosisI > 1 ){
					cell.Move(state);
				}else{
					if(randomApoptosisI < 20 && randomApoptosisI > 10 ){
						cell.Expand(state);
					}
				}
			}
		}
	}

	private void ChangeStateNormtoxicCellState(Cell cell){
		cell.setCellState(new NormtoxicCellState());
	}
	
	private void ChangeStateNecrotic(Cell cell){
		cell.setCellState(new NecroticCellState());
	}
	
	
	public Color getColor(){
		return this.color;
	}


}
