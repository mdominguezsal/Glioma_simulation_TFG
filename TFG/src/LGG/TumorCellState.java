package LGG;

import java.util.Iterator;

import sim.util.Bag;

public class TumorCellState extends CellState{
	protected Metabolism metabolism;
	protected Motility motility;
	protected Proliferation proliferation;
	protected int radium = 10;
	protected Double termozolomide;
		
	
	
	
	public int findTermozolomide(Environment state, Cell cell){
		Environment t = (Environment)state;
		int i = 0;
		Boolean sufficientOxygen = false;
		
		Bag agentsList = t.environment.getNeighborsWithinDistance(cell.getPosition(), cell.getRadium());
		//System.out.println("Cell in position "+position+ " Checking distance "+distance);
		Iterator<Object> it = agentsList.iterator();
		
		while (it.hasNext() && !sufficientOxygen){
			Object obj = it.next();
			if (obj.getClass() == Termozolomide.class){
				Termozolomide term = (Termozolomide)obj;
				//System.out.println("Oxygen in position "+o.position);
				t.environment.remove(term);
				term.removeMolecule();
				i++;
				
			}
		}
		
		return i;
	}
	
}
