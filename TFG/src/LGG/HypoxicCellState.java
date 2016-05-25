package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class HypoxicCellState extends TumorCellState{
	/*private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private final static int MOTILITY_RATIO = 5;
	private final static int PROLIFERATION_RATIO = 1;
	
	private int toNecroticO; //Ah
	private int toNecroticGlu; // Aglh
	private int radium = 10;
	private int dh = 1;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;*/

	
	public HypoxicCellState(Double oxygen, Double glucose, Double apoptosis, Double motilityRate, Double proliferationRate, Double termozolomide) {
		metabolism = new Metabolism(oxygen, glucose, apoptosis, this.radium);
		motility = new Motility(motilityRate);
		proliferation = new Proliferation(proliferationRate);
		this.termozolomide = termozolomide;
		this.color = Color.BLUE;
	}
	
	

	public void executeState(Environment state, Cell cell) {
		boolean suffOxygen = this.metabolism.sufficientGlucose(state, cell);
		boolean suffGlucose = this.metabolism.sufficientGlucose(state, cell);

		Double randomApoptosis = state.random.nextDouble();

		if(randomApoptosis < state.getRandomchangetoNecrotic()){ 
			cell.ChangeStateNecroticState(state);
		}else{
			if( !suffOxygen && suffGlucose){
				//cell.ChangeStateNecroticState(state);
			}else{
				if(suffOxygen) cell.ChangeStateNormtoxicCellState(state, cell.getPosition()); 
				if(!suffGlucose)cell.ChangeStateNecroticState(state);

				if(this.findTermozolomide(state, cell)> this.termozolomide){
					cell.ChangeStateNecroticState(state);
				}else{
					Double proliferation = state.random.nextDouble();
					if(proliferation < this.proliferation.proliferationRate){
						this.proliferation.proliferate(cell, state);

					}else{
						Double randomMotility = (double) motility.ratio;
						if(randomMotility < state.getMotilityRatioNormal()){
							this.motility.Move(cell, state);
						}

					}
				}
			}
		}	
	}
		
	
	/*
	public int proliferative(Environment state){
		return this.dh * (1-state.getT());
	}
	
	public int toNecroticConversionRate(Environment state){
		return this.toNecroticO *  this.setToNecroticO(state) + this.toNecroticGlu * this.setToNecroticGlu(state);
	}
	
	
		private int setToNecroticO(Environment state){//Nhn
			int toNecrotic = 0;
			Double i = (double) state.getnHypoxicCells() / (state.getnNormtoxicCells()+state.getnHypoglicemicCells()+state.getnHypoxicCells());
			
			if(i>0.9) toNecrotic = 1;
			
			return toNecrotic;
		}
		
		private int setToNecroticGlu(Environment state){ // Glhn
			int toNecrotic = 0;
			if(state.getNh() == 1 && state.getGlq() == 1)toNecrotic = 1;
			return toNecrotic;
		}
		
*/
	
}
