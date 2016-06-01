package LGG;

import java.awt.Color;

public class HypoxicCellState extends TumorCellState{
	private static final long serialVersionUID = 1L;

	public HypoxicCellState(Environment state){
		metabolism = new Metabolism(state.getOxygenConsumptionHypoxic(), state.getGluConsumptionHypoxic(), state.getApoptosisValue(), this.radium);
		motility = new Motility(state.getMotilityRatioHypoxic());
		proliferation = new Proliferation(state.getProliferationRatioHypoxic());
		termozolomide = state.valueOfEffectiveTermozolomide();
		color = Color.BLUE;
	}
		
	@Override
	public void executeState(Environment state, Cell cell) {
		this.metabolism.incrementConsumptionNeeds();
		boolean needOxygen = this.metabolism.needOxygen();
		boolean suffOxygen = false;
		boolean needGlucose = this.metabolism.needGlucose();
		boolean suffGlucose = false;
		if (needOxygen){
			suffOxygen = this.metabolism.consumeOxygen(state, cell);
		}
		if (needGlucose){
			suffGlucose = this.metabolism.consumeGlucose(state, cell);			
		}
		
		if (state.random.nextDouble() < (this.findNecroticArround(state, cell) * state.getChangetoNecroticbyContact())){
			cell.ChangeStateNecroticState(state, CauseOfDeath.ByContact);
		}else if(this.findTermozolomide(state, cell) > this.termozolomide){
			cell.ChangeStateNecroticState(state, CauseOfDeath.Termozolomide);
		}else if( state.random.nextDouble() < state.getApoptosisValue()){ 
			cell.ChangeStateNecroticState(state, CauseOfDeath.Apoptosis);
		}else if(needOxygen && suffOxygen){
			cell.ChangeStateNormtoxicCellState(state, cell.getPosition());
		}else if(needGlucose && !suffGlucose && needOxygen && !suffOxygen){
			cell.ChangeStateNecroticState(state, CauseOfDeath.InsufficientNutrients);
		}else if (needGlucose && !suffGlucose){
			cell.ChangeStateHypoglycemicState(state, cell.getPosition());
		}else if(state.random.nextDouble() < this.proliferation.proliferationRate){
			this.proliferation.proliferate(cell, state);
		}else if(state.random.nextDouble() < state.getMotilityRatioNormal()){
			this.motility.Move(cell, state);
		}
	}	
	
	@Override
	public void incrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfHypoxicCells() + 1;
		state.numberOfHypoxicChangeValue(newValue);
	}

	@Override
	public void decrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfHypoxicCells() - 1;
		state.numberOfHypoxicChangeValue(newValue);
	}
}
