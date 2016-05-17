package LGG;

import java.util.Iterator;

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
		Boolean sufficientOxygen = false;
		
		Bag agentsList = t.environtment.getNeighborsWithinDistance(position, distance);
		//System.out.println("Cell in position "+position+ " Checking distance "+distance);
		Iterator<Object> it = agentsList.iterator();
		
		while (it.hasNext() && !sufficientOxygen){
			Object obj = it.next();
			if (obj.getClass() == Oxygen.class){
				Oxygen o = (Oxygen)obj;
				//System.out.println("Oxygen in position "+o.position);
				t.environtment.remove(o);
				o.removeMolecule();
				i++;
				if(i == this.min_Oxygen) sufficientOxygen = true;
			}
		}
		return sufficientOxygen;	
	}
	
	public boolean sufficientGlucose(SimState state, Double2D position){
		Environment t = (Environment)state;
		int i = 0;
		Boolean sufficientGlu = false;
		
		Bag agentsList = t.environtment.getNeighborsWithinDistance(position, distance);
		//System.out.println("Cell in position "+position+ " Checking distance "+distance);
		Iterator<Object> it = agentsList.iterator();

		while (it.hasNext() && !sufficientGlu){
			Object obj = it.next();
			if (obj.getClass() == Glucose.class){
				Glucose glu = (Glucose)obj;
				//System.out.println("Glucose in position "+ glu.position);
				t.environtment.remove(glu);
				glu.removeMolecule();
				i++;
				if(i == this.min_Glucose) sufficientGlu = true;
			}	
		}
		return sufficientGlu;
	}


}
