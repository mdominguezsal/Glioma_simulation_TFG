package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class NormalCellState implements CellState{
	private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private final static int MOTILITY_RATIO = 1;
	private int radium = 10;
	private Metabolism metabolism;
	private Motility motility;
	private Color color = Color.GREEN;
	private int movement = 1;
	private int replication;
	
	public NormalCellState() {
		metabolism = new Metabolism(MIN_OXYGEN, MIN_GLUCOSE,APOPTOSIS, radium);
		motility = new Motility(MOTILITY_RATIO);
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		Double randomD = state.random.nextDouble() * 200;
		int randomI = randomD.intValue();	
		if(randomI == 1){
			this.ChangeStateNormtoxicCellState(cell);	
		}else{
			//Double randomApoptosis = state.random.nextDouble() * 200;
			//int randomApoptosisI = randomApoptosis.intValue();

			if(randomI == 2){
				this.ChangeStateNecrotic(cell);
			}else{
				if(randomI < 10 && randomI > 1 ){
					this.motility.Move(cell, state);
				}else{
					if(randomI < 20 && randomI > 10 ){
						//cell.Expand(state);
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

	public int getMovement(){
		return this.movement;
	}
	
	public int getReplication(){
		return this.replication;
	}


}
