package LGG;

import java.io.Serializable;

import sim.util.Double2D;

public class Proliferation implements Serializable{
	private static final long serialVersionUID = 1L;
	double proliferationRate;
		
	public Proliferation(double d) {
		this.proliferationRate = d;
	}

	public void proliferate(Cell cell,Environment eState){
		Double2D newPosition = eState.newCopyCellPosition(cell.getPosition());
		if(!eState.cellInPosition(newPosition, this.hashCode(), cell.getRadium())){

			Environment status = (Environment)eState;
			Cell newCell = new Cell(cell.getCellState(),newPosition);
			status.environment.setObjectLocation(newCell, newPosition);
			status.schedule.scheduleRepeating(newCell);
			
			int nCells = eState.getNCells() + 1;
			eState.numberOfCellsChangeValue(nCells);
			cell.getCellState().incrementTypeOfCell(eState);
			
		}
	}
}
