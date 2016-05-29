 package LGG;

import java.io.Serializable;

import sim.util.Double2D;

public class Motility implements Serializable{
	private static final long serialVersionUID = 1L;
	Double ratio;

	public Motility(Double motility) {
		this.ratio = motility;
	}

	public void Move(Cell cell,Environment eState){
		Double2D cellPosition = cell.getPosition();
		Double2D newPosition = eState.newPosition(cellPosition);
		if(!eState.cellInPosition(newPosition, cell.hashCode(), cell.getRadium())){
			cell.setPosition(newPosition);
			eState.environment.setObjectLocation(cell, newPosition);
		}
	}
}
