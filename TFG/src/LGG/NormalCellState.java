package LGG;

import java.awt.Color;

public class NormalCellState extends CellState{
	private static final long serialVersionUID = 1L;
	private Motility motility;
	private Proliferation proliferation;
	private int radium = 10;
	
	public NormalCellState(Environment state){
		metabolism = new Metabolism(state.getOxygenConsumptionNormal(), state.getGluConsumptionNormal(), state.getApoptosisValue(), this.radium);
		motility = new Motility(state.getMotilityRatioNormal());
		proliferation = new Proliferation(state.getProliferationRatioNormal());
		color = Color.GREEN;
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
		
		if (state.random.nextDouble() < (this.findTumorArround(state, cell) * state.getChangeToTumorbyContact())){
			cell.ChangeStateNormtoxicCellState(state, cell.getPosition());
		}else if (state.random.nextDouble() < (this.findNecroticArround(state, cell) * state.getChangetoNecroticbyContact())){
			cell.ChangeStateNecroticState(state, CauseOfDeath.ByContact);
		}else if(state.random.nextDouble() < state.getApoptosisValue()){ 
			cell.ChangeStateNecroticState(state, CauseOfDeath.Apoptosis);
		}else if(state.random.nextDouble() < state.getRandomChangetoTumor()){
			cell.ChangeStateNormtoxicCellState(state, cell.getPosition());	
		}else if((needOxygen && !suffOxygen) || (needGlucose && !suffGlucose)){
			cell.ChangeStateNecroticState(state, CauseOfDeath.InsufficientNutrients);
		}else if(state.random.nextDouble() < this.proliferation.proliferationRate){
			this.proliferation.proliferate(cell, state);
		}else if(state.random.nextDouble() < motility.ratio){
			this.motility.Move(cell, state);
		}
	}
	
	@Override
	public void incrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfNormalCells() + 1;
		state.numberOfNormalChangeValue(newValue);
	}

	@Override
	public void decrementTypeOfCell(Environment state) {
		int newValue = state.getNumberOfNormalCells() - 1;
		state.numberOfNormalChangeValue(newValue);
	}
}
