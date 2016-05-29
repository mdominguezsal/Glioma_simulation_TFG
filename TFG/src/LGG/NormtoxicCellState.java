package LGG;

import java.awt.Color;

public class NormtoxicCellState extends TumorCellState{
	private static final long serialVersionUID = 1L;

	public NormtoxicCellState(Environment state){
		metabolism = new Metabolism(state.getOxygenConsumtionNormotoxic(), state.getGluConsumtionNormotoxic(), state.getApoptosisValue(), this.radium);
		motility = new Motility(state.getMotilityRatioNormotoxic());
		proliferation = new Proliferation(state.getProliferationRatioNormotoxic());
		termozolomide = state.getEffectiveTermozolomide();
		color = Color.RED;
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
		}else if (this.findTermozolomide(state, cell)> this.termozolomide){
			cell.ChangeStateNecroticState(state, CauseOfDeath.Termozolomide);
		}else if(state.random.nextDouble() < state.getApoptosisValue()){ 
			cell.ChangeStateNecroticState(state, CauseOfDeath.Apoptosis);
		//}else if(needOxygen && !suffOxygen && needGlucose && !suffGlucose){
		//	cell.ChangeStateNecroticState(state);
		}else if(needOxygen && !suffOxygen){
			cell.ChangeStateHypoxicState(state, cell.getPosition()); 
		}else if(needGlucose && !suffGlucose){
			cell.ChangeStateHypoglycemicState(state, cell.getPosition());
		}else if(state.random.nextDouble() < this.proliferation.proliferationRate){
			this.proliferation.proliferate(cell, state);
		}else if(state.random.nextDouble() < motility.ratio){
			this.motility.Move(cell, state);
		}	
	}	
	
	@Override
	public void incrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfNormotoxicCells() + 1;
		state.numberOfNormotoxicChangeValue(newValue);
	}

	@Override
	public void decrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfNormotoxicCells() - 1;
		state.numberOfNormotoxicChangeValue(newValue);
	}
}
