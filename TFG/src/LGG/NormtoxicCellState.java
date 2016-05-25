package LGG;

import java.awt.Color;

import sim.engine.SimState;

public class NormtoxicCellState extends TumorCellState{
	
	/*private final static int MIN_OXYGEN = 1;
	private final static int MIN_GLUCOSE = 1;
	private final static float APOPTOSIS = 1;
	private final static int MOTILITY_RATIO = 1;
	private final static int PROLIFERATION_RATIO = 2;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private int radium = 10;
	private int dq;
*//*
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private int radium = 10;
	private Double termozolomide;*/
	
	public NormtoxicCellState(Double oxygen, Double glucose, Double apoptosis, Double motilityRate, Double proliferationRate, Double termozolomide) {
		metabolism = new Metabolism(oxygen, glucose, apoptosis, this.radium);
		motility = new Motility(motilityRate);
		proliferation = new Proliferation(proliferationRate);
		this.termozolomide = termozolomide;
		color = Color.RED;
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
				if(!suffOxygen) cell.ChangeStateHypoglycemicState(state, cell.getPosition()); 
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
	
	/*public double proliferative(Environment state){
		double i = this.dq * (1-state.getT())+state.getProliferationConstant();
		state.get
		
		
		return i;
	}*/

	
}
