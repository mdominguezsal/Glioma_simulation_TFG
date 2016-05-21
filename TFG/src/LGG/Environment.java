package LGG;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sim.field.continuous.Continuous2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.engine.SimState;
import sim.engine.Stoppable;

public class Environment extends SimState {
	 public Continuous2D environment;
	 private Double radium = (double) 5;
	
         //This is a 2D space in which bags
	  //are created for each cell once an
	 //agent or a particle first moves into a cell.  
        //This allows multiple
	 //agents or particles to occupy the same cell.

	 private int gridWidth = 300;  //the width of the grid
	 private int gridHeight = 300; //the height of the grid
	 private int nCells = 50; //The number of particles
	 private int nMolecules = 2;
	 
	 public Environment(long seed) {
		 super(seed);
	 }

	 public void start(){
		 super.start();
		environment = new Continuous2D(1.0,gridWidth,gridHeight);

		 for(int i=0;i < nCells;i++){
			 Cell cell = new Cell(this);
			 environment.setObjectLocation(cell, cell.getPosition());
			 schedule.scheduleRepeating(cell);
			 //Stoppable stopCell = schedule.schedulerepeating(cell);
		 }

		 for(int i=0;i < nMolecules;i++){
			 Oxygen oxygen = new Oxygen(getPositionMolecule());
			 environment.setObjectLocation(oxygen, oxygen.position);
			 schedule.scheduleRepeating(oxygen);
			 Stoppable stopOx = schedule.scheduleRepeating(oxygen);
			 oxygen.setStop(stopOx);

			 Glucose glucose = new Glucose(getPositionMolecule());
			 environment.setObjectLocation(glucose, glucose.position);
			 Stoppable stopGlu = schedule.scheduleRepeating(glucose);
			 glucose.setStop(stopGlu);
		 }
		 
		 ECM ecm = new ECM(this);
		 schedule.scheduleRepeating(ecm);
		 //cellSpace.setObjectLocation(cell, cell.x, cell.y);
		 //cells.add(cell);
		 //oxygenSpace.setObjectLocation(oxygen, oxygen.x, oxygen.y);
	 }
	 
	 
	 public boolean cellInPosition(Double2D position){
		 Bag b = this.environment.getNeighborsExactlyWithinDistance(position, radium);
			if(b == null) return false;
			//System.out.print("\n");
			Iterator i = b.iterator();
			while(i.hasNext()){
				Object obj = i.next();
				if(obj.getClass() == Cell.class){
					return true;
				}
			}
			return false;
		}
	 
	 
	 public void setGridWidth(int i){
		 this.gridWidth = i;
	 }
	 
	 public int getGridWidth(){
		 return this.gridWidth;
	 }
	 
	 public void setGridHeigh(int i){
		 this.gridHeight = i;
	 }
	 
	 public int getGridHeigh(){
		 return this.gridHeight;
	 }

	 public Double2D getNewPosition(Double2D position){
		 Double2D movePosition = null;
		 Double move = this.random.nextDouble() * 8;
			int i = move.intValue();
		
			if (i == 0){
				movePosition = new Double2D(position.x+1, position.y);
			}else if( i == 1){
				movePosition = new Double2D(position.x, position.y+1);
			}else if(i==2){
				 movePosition = new Double2D(position.x-1, position.y);
			}else if(i==3){
				movePosition = new Double2D(position.x, position.y-1);
			}else if(i==4){
				movePosition = new Double2D(position.x+1, position.y+1);
			}else if(i==5){
				movePosition = new Double2D(position.x-1, position.y-1);
			}else if(i==6){
				movePosition = new Double2D(position.x-1, position.y+1);
			}else if(i==7){
				movePosition = new Double2D(position.x+1, position.y-1);
			}
			return(movePosition);
	 }
	 
	 public Double2D getNewCellPosition(){
		 Double x = this.random.nextDouble();
		 Double y = this.random.nextDouble();
		 return new Double2D((x * this.getGridWidth()/6 + this.getGridWidth()/6),(y * this.getGridHeigh())/6 + (this.getGridHeigh()/6)); 
	 }

	 public Double2D getPositionMolecule(){
		 Double2D position = new Double2D((this.random.nextDouble() * this.getGridWidth()), (this.random.nextDouble() * this.getGridHeigh()));
		 return position;
	 }	
	 public Double2D getPositionNewMolecule(){	
		 Double2D newPosition = null;
		 Boolean randomxy = this.random.nextBoolean(); 
		 Double xy = this.random.nextDouble()* this.getGridWidth();

		 if(randomxy){
			 newPosition = new Double2D(1, xy);
		 }else{
			 newPosition = new Double2D(xy, 1);
		 }
		 System.out.print("new molecule:");
		 System.out.print(newPosition);
		 System.out.println();
		 return newPosition;
		 
	 }
}


