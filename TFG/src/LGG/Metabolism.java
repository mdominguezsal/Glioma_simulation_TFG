package LGG;

import sim.util.Bag;
import sim.util.Double2D;
import sim.engine.SimState;

public class Metabolism implements Behaviour{
	int min_Oxygen;
	int min_Glucose;
	float apoptosis;
	int distance;

	public Metabolism(int oxygen, int glucose, float apoptosis, int distance) {
		this.min_Oxygen = oxygen;
		this.min_Glucose = glucose;
		this.apoptosis = apoptosis;
		this.distance = distance;
	}

	public boolean sufficientOxygen(SimState state, Double2D position){
		Environment t = (Environment)state;
		int i = 0;
		Bag agentsList = t.environtment.getNeighborsWithinDistance(position, distance);
		System.out.println("Cell in position "+position+ " Checking distance "+distance);
		for (Object obj : agentsList){
			if (obj.getClass() == Oxygen.class){
				Oxygen ox = (Oxygen)obj;
				System.out.println("Oxygen in position "+ox.position);
				i++;
			}
		}
		return i>= min_Oxygen;	
	}
	
	public boolean sufficientGlucosa(){
		return false;
	}

	public boolean sufficientGlucose(SimState state, Double2D position) {
		// TODO Auto-generated method stub
		return false;
	}


	

}
