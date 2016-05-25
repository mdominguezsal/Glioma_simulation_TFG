package LGG;

import java.awt.Color;

import sim.engine.SimState;
import sim.util.Double2D;

public class NormalCellState extends CellState{
	//private final static Double oxygen ;
//	private final static Double glucose ;
//	private final static Double apoptosis;
	
	private int dc;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private double changetoTumor;
	private int radium = 10;
	
	public NormalCellState(double oxygen, double glucose, double apoptosis, double changetoTumor, double motilityRate, double proliferationRate) {
		metabolism = new Metabolism(oxygen, glucose, apoptosis, this.radium);
		motility = new Motility(motilityRate);
		proliferation = new Proliferation(proliferationRate);
		this.changetoTumor = changetoTumor;
		color = Color.GREEN;
	}



	/*protected double proliferationRate(Environment e, Double2D p) {
		// TODO Auto-generated method stub
		return 0;
	}*/






	@Override
	public void executeState(Environment state, Cell cell) {
		Double randomTumor = state.random.nextDouble();
		boolean suffOxygen = this.metabolism.sufficientGlucose(state, cell);
		boolean suffGlucose = this.metabolism.sufficientGlucose(state, cell);

		if(randomTumor < state.getRandomChangetoTumor()){	cell.ChangeStateNormtoxicCellState(state, cell.getPosition());	
		}else{
			Double randomApoptosis = state.random.nextDouble();
			if(randomApoptosis < state.getRandomchangetoNecrotic()){ 
				cell.ChangeStateNecroticState(state);
			}else{
				if( !suffOxygen && suffGlucose){
					cell.ChangeStateNecroticState(state);
				}else{

					if(!suffOxygen) cell.ChangeStateHypoxicState(state, cell.getPosition()); 
					if(!suffGlucose)cell.ChangeStateHypoglycemicState(state, cell.getPosition());

					Double proliferation = state.random.nextDouble();
					if(proliferation < this.proliferation.proliferationRate){

					}else{
						Double randomMotility = (double) motility.ratio;
						if(randomMotility < state.getMotilityRatioNormal()) this.motility.Move(cell, state);

					}
				}
			}
		}	
	}
	
	/*public int proliferative(Environment state){
		return (int) (this.dc * (1-state.getT()));
	}
	
	
	public void necroticCellsAround(Environment state){
		
	}*/
}
