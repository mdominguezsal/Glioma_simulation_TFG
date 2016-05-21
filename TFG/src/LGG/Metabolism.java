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
	int count = 0;

	public Metabolism(int oxygen, int glucose, float apoptosis, int distance) {
		this.min_Oxygen = oxygen;
		this.min_Glucose = glucose;
		this.apoptosis = apoptosis;
		this.distance = distance;
	}

	public void metabolismExecution(SimState state, Cell cell){
		//System.out.print(this.sufficientGlucose(state, cell.getPosition()));
		
		//if(!this.sufficientGlucose(state, cell.getPosition())){
			//if(cell.getCellState().getClass() == NormtoxicCellState.class) 	cell.ChangeStateHypoglycemicState();

			//if(cell.getCellState().getClass() == NormalCellState.class)	cell.ChangeStateNecroticState();
			/*if(cell.getCellState().getClass() == HypoglycemicCellState.class){
				Double randomD = state.random.nextDouble() * 200;
				int randomI = randomD.intValue();
				if(count > randomI)  cell.getCellState().ChangeStateNecroticState(cell);
				count = count++;
		}
		/*if(!this.sufficientOxygen(state, cell.getPosition())){
			if(cell.getCellState().getClass() == NormtoxicCellState.class) 	cell.getCellState().ChangeStateHypoglycemicState(cell);

			if(cell.getCellState().getClass() == NormtoxicCellState.class) 	cell.getCellState().ChangeStateHypoxicState(cell);

			if(cell.getCellState().getClass() == HypoglycemicCellState.class) cell.getCellState().ChangeStateNecroticState(cell);

			//cell.getCellState().ChangeStateHypoxicState(cell);

			/*if(cell.getCellState().getClass() == HypoxicCellState.class){
				Double randomD = state.random.nextDouble() * 200;
				int randomI = randomD.intValue();
				if(count > randomI)  cell.getCellState().ChangeStateNecroticState(cell);
				count = count++;
			}
		}	*/
	}
	
	private boolean sufficientOxygen(SimState state, Double2D position){
		Environment t = (Environment)state;
		int i = 0;
		Boolean sufficientOxygen = false;
		
		Bag agentsList = t.environment.getNeighborsWithinDistance(position, distance);
		//System.out.println("Cell in position "+position+ " Checking distance "+distance);
		Iterator<Object> it = agentsList.iterator();
		
		while (it.hasNext() && !sufficientOxygen){
			Object obj = it.next();
			if (obj.getClass() == Oxygen.class){
				Oxygen o = (Oxygen)obj;
				//System.out.println("Oxygen in position "+o.position);
				t.environment.remove(o);
				o.removeMolecule();
				i++;
				if(i == this.min_Oxygen) sufficientOxygen = true;
			}
		}
		return sufficientOxygen;	
	}
	
	private boolean sufficientGlucose(SimState state, Double2D position){
		Environment t = (Environment)state;
		int i = 0;
		Boolean sufficientGlu = false;
		
		Bag agentsList = t.environment.getNeighborsWithinDistance(position, distance);
		//System.out.println("Cell in position "+position+ " Checking distance "+distance);
		Iterator<Object> it = agentsList.iterator();

		while (it.hasNext() && !sufficientGlu){
			Object obj = it.next();
			if (obj.getClass() == Glucose.class){
				Glucose glu = (Glucose)obj;
				//System.out.println("Glucose in position "+ glu.position);
				t.environment.remove(glu);
				glu.removeMolecule();
				i++;
				if(i == this.min_Glucose) sufficientGlu = true;
			}	
		}
		return sufficientGlu;
	}


}
