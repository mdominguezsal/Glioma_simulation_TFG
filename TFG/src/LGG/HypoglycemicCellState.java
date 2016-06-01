package LGG;

import java.awt.Color;

public class HypoglycemicCellState extends TumorCellState{
	private static final long serialVersionUID = 1L;

	public HypoglycemicCellState(Environment state){
		metabolism = new Metabolism(state.getOxygenConsumptionHypoglycemic(), state.getGluConsumptionhypoglycemic(), state.getApoptosisValue(), this.radium);
		motility = new Motility(state.getMotilityRatioHypoglycemic());
		proliferation = new Proliferation(state.getProliferationRatioHypoglycemic());
		termozolomide = state.valueOfEffectiveTermozolomide();
		color = Color.MAGENTA;
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
		}else if(this.findTermozolomide(state, cell)  > this.termozolomide){
			cell.ChangeStateNecroticState(state, CauseOfDeath.Termozolomide);
		}else if(state.random.nextDouble() < state.getApoptosisValue()){ 
			cell.ChangeStateNecroticState(state, CauseOfDeath.Apoptosis);
		}else if(needGlucose && suffGlucose){
			cell.ChangeStateNormtoxicCellState(state, cell.getPosition());				
		}else if ((needOxygen && !suffOxygen) || (needGlucose && !suffGlucose)){ 
			cell.ChangeStateNecroticState(state, CauseOfDeath.InsufficientNutrients);	
		}else if(state.random.nextDouble() < this.proliferation.proliferationRate){
			this.proliferation.proliferate(cell, state);
		}else if(state.random.nextDouble() < state.getMotilityRatioNormal()){
			this.motility.Move(cell, state);
		}
	}

	@Override
	public void incrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfHypoglycemicCells() + 1;
		state.numberOfHypoglycemicChangeValue(newValue);
	}

	@Override
	public void decrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfHypoglycemicCells() - 1;
		state.numberOfHypoglycemicChangeValue(newValue);
	}
}
