package LGG;

import java.io.Serializable;
import java.util.Iterator;

import sim.util.Bag;
import sim.engine.SimState;

public class Metabolism implements Serializable{
	private static final long serialVersionUID = 1L;
	double oxygenIntake;
	double glucoseIntake;
	double apoptosis;
	int distance;
	double oxygenConsumptionNeeds = 0.0;
	double glucoseConsumptionNeeds = 0.0;

	public Metabolism(double oxygen, double glucose, double apoptosis, int distance) {
		this.oxygenIntake = oxygen;
		this.glucoseIntake = glucose;
		this.apoptosis = apoptosis;
		this.distance = distance;
	}
	
	public boolean consumeOxygen(SimState state, Cell cell){
		Environment t = (Environment)state;
			
		Bag agentsList = t.environment.getNeighborsWithinDistance(cell.getPosition(), distance);
		Iterator<Object> it = agentsList.iterator();
		
		while (it.hasNext() && oxygenConsumptionNeeds >= 1){
			Object obj = it.next();
			if (obj.getClass() == Oxygen.class){
				Oxygen o = (Oxygen)obj;
				t.environment.remove(o);
				o.removeMolecule();
				oxygenConsumptionNeeds = oxygenConsumptionNeeds - 1;
			}
		}
		
		return oxygenConsumptionNeeds < 1;	
	}
	
	public boolean consumeGlucose(SimState state, Cell cell){
		Environment t = (Environment)state;
				
		Bag agentsList = t.environment.getNeighborsWithinDistance(cell.getPosition(), distance);
		Iterator<Object> it = agentsList.iterator();

		while (it.hasNext() && glucoseConsumptionNeeds >= 1){
			Object obj = it.next();
			if (obj.getClass() == Glucose.class){
				Glucose glu = (Glucose)obj;
				t.environment.remove(glu);
				glu.removeMolecule();
				glucoseConsumptionNeeds = glucoseConsumptionNeeds - 1;
			}	
		}
		
		return glucoseConsumptionNeeds < 1;
	}
	
	public void incrementConsumptionNeeds(){
		oxygenConsumptionNeeds += oxygenIntake;
		glucoseConsumptionNeeds += glucoseIntake;
	}
	
	public boolean needOxygen(){
		return oxygenConsumptionNeeds >= 1.0;
	}
	
	public boolean needGlucose(){
		return glucoseConsumptionNeeds >= 1.0;
	}
	
	public double getOxygenNeeds(){
		return oxygenConsumptionNeeds;
	}
	
	public double getGlucoseNeeds(){
		return glucoseConsumptionNeeds;
	}
}
