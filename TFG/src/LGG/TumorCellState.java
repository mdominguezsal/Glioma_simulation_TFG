package LGG;

import java.util.Iterator;

import sim.util.Bag;

public abstract class TumorCellState extends CellState{
	private static final long serialVersionUID = 1L;
	
	protected Motility motility;
	protected Proliferation proliferation;
	protected int radium = 10;
	protected int termozolomide;
		
	public int findTermozolomide(Environment state, Cell cell){
		Environment t = (Environment)state;
		int i = 0;
		
		Bag agentsList = t.environment.getNeighborsWithinDistance(cell.getPosition(), cell.getRadium());
		Iterator<Object> it = agentsList.iterator();
		
		while (it.hasNext()){
			Object obj = it.next();
			if (obj.getClass() == Termozolomide.class){
				i++;
			}
		}
		
		return i;
	}

}
