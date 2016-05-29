package LGG;


import java.awt.Color;
import java.io.Serializable;
import java.util.Iterator;

import sim.util.Bag;

public abstract class CellState implements Serializable{
	private static final long serialVersionUID = 1L;
	protected Metabolism metabolism;
	protected Color color;
	private int movement;
	private int replication;
	
	public Color getColor(){
		return this.color;
	}

	public int getMovement(){
		return this.movement;
	}
	
	public int getReplication(){
		return this.replication;
	}

	abstract void executeState(Environment state, Cell cell);

	public double getOxygenNeeds(){
		return metabolism.getOxygenNeeds();
	}
	
	public double getGlucoseNeeds(){
		return metabolism.getGlucoseNeeds();
	}

	public int findNecroticArround(Environment state, Cell cell){
		Environment t = (Environment)state;
		int i = 0;
		
		Bag agentsList = t.environment.getNeighborsWithinDistance(cell.getPosition(), cell.getRadium());
		Iterator<Object> it = agentsList.iterator();
		
		while (it.hasNext()){
			Object obj = it.next();
			if (obj.getClass() == Cell.class){
				Cell cellArround = (Cell)obj;
				if (cellArround.getCellState().getClass() == NecroticCellState.class){
					i++;					
				}
			}
		}
		
		return i;
	}
	
	public int findTumorArround(Environment state, Cell cell){
		Environment t = (Environment)state;
		int i = 0;
		
		Bag agentsList = t.environment.getNeighborsWithinDistance(cell.getPosition(), cell.getRadium());
		Iterator<Object> it = (Iterator<Object>)agentsList.iterator();
		
		while (it.hasNext()){
			Object obj = it.next();
			if (obj.getClass() == Cell.class){
				Cell cellArround = (Cell)obj;
				if (cellArround.getCellState().getClass() == TumorCellState.class){
					i++;					
				}
			}
		}
		
		return i;
	}
	
	public abstract void incrementTypeOfCell(Environment state);
	public abstract void decrementTypeOfCell(Environment state);
}
