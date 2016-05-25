package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class HypoglycemicCellState extends TumorCellState{
	
	//private int dc;
	/*private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private int radium = 10;
	private Double termozolomide;*/
	
	/*private int dq;
	private int toNecroticO;
	private int toNecroticGlu;
	private int radium = 10;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;*/


	
	
	public HypoglycemicCellState(Double oxygen, Double glucose, Double apoptosis, Double motilityRate, Double proliferationRate, Double termozolomide) {
		metabolism = new Metabolism(oxygen, glucose, apoptosis, this.radium);
		motility = new Motility(motilityRate);
		proliferation = new Proliferation(proliferationRate);
		this.termozolomide = termozolomide;
		color = Color.MAGENTA;
	}

	
	@Override
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
				if(!suffOxygen) cell.ChangeStateNecroticState(state); 
				if(suffGlucose)cell.ChangeStateNormtoxicCellState(state, cell.getPosition());

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
		return (int) (this.dq * (1-state.getT()));
	}

	public int toNecroticConversionRate(Environment state){
		return this.toNecroticO *  this.setToNecroticO(state) + this.toNecroticGlu * this.setToNecroticGlu(state);
	}


	private int setToNecroticO(Environment state){//Nqn
		int toNecrotic = 0;
		if(state.getNh() == 1 && state.getGlq() == 1)toNecrotic = 1;
		return toNecrotic;		
	}

	private int setToNecroticGlu(Environment state){ // Glqn
		int toNecrotic = 0;
		Double i = (double) state.getnHypoglicemicCells()/ (state.getnNormtoxicCells()+state.getnHypoglicemicCells()+state.getnHypoxicCells());

		if(i>0.9) toNecrotic = 1;

		return toNecrotic;
	}
*/
}
