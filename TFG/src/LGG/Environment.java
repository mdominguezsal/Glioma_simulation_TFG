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
	 private Double radium = (double) 4;
	 private int gridWidth = 300;  //the width of the grid
	 private int gridHeight = 300; //the height of the grid
	 private int nCells = 50; //The number of particles
	 private int nMolecules = 1000;
	 private int nTermozolomide = 50;
	 
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
			 Oxygen oxygen = new Oxygen(positionMolecule());
			 environment.setObjectLocation(oxygen, oxygen.position);
			 schedule.scheduleRepeating(oxygen);
			 Stoppable stopOx = schedule.scheduleRepeating(oxygen);
			 oxygen.setStop(stopOx);

			 Glucose glucose = new Glucose(positionMolecule());
			 environment.setObjectLocation(glucose, glucose.position);
			 Stoppable stopGlu = schedule.scheduleRepeating(glucose);
			 glucose.setStop(stopGlu);
		 }
		 
		 for(int i=0;i < this.nTermozolomide ;i++){
			 Termozolomide termozolomide = new Termozolomide(positionTermozolomide());
			 environment.setObjectLocation(termozolomide, termozolomide.position);
			 schedule.scheduleRepeating(termozolomide);
			 Stoppable stopOx = schedule.scheduleRepeating(termozolomide);
			 termozolomide.setStop(stopOx); 
		 }
		 
		 ECM ecm = new ECM(this);
		 schedule.scheduleRepeating(ecm);
		 //cellSpace.setObjectLocation(cell, cell.x, cell.y);
		 //cells.add(cell);
		 //oxygenSpace.setObjectLocation(oxygen, oxygen.x, oxygen.y);
	 }
	 
	 
	 public boolean cellInPosition(Double2D position, int hash, int radium){
		 Bag b = this.environment.getNeighborsExactlyWithinDistance(position, radium);
			if(b == null) return false;
			//System.out.print("\n");
			Iterator i = b.iterator();
			while(i.hasNext()){
				Object obj = i.next();
				if(obj.getClass() == Cell.class && obj.hashCode() != hash){
					return true;
				}
			}
			return false;
		}
	 
	 public Double2D newCopyCellPosition(Double2D position){
		 Double2D movePosition = null;
		 Double move = this.random.nextDouble() * 8;
			int i = move.intValue();
		
			if (i == 0){
				movePosition = new Double2D(position.x + this.radium, position.y);
			}else if( i == 1){
				movePosition = new Double2D(position.x, position.y+this.radium);
			}else if(i == 2){
				 movePosition = new Double2D(position.x-this.radium, position.y);
			}else if(i == 3){
				movePosition = new Double2D(position.x, position.y-this.radium);
			}else if(i == 4){
				movePosition = new Double2D(position.x+this.radium, position.y+this.radium);
			}else if(i == 5){
				movePosition = new Double2D(position.x-this.radium, position.y-this.radium);
			}else if(i == 6){
				movePosition = new Double2D(position.x-this.radium, position.y+this.radium);
			}else if(i == 7){
				movePosition = new Double2D(position.x+this.radium, position.y-this.radium);
			}
			return(movePosition);
	 }


	 		 
	 public Double2D newPosition(Double2D position){
		 Double2D movePosition = null;
		 Double move = this.random.nextDouble() * 8;
			int i = move.intValue();
		
			if (i == 0){
				movePosition = new Double2D(position.x+1, position.y);
			}else if( i == 1){
				movePosition = new Double2D(position.x, position.y+1);
			}else if(i == 2){
				 movePosition = new Double2D(position.x-1, position.y);
			}else if(i == 3){
				movePosition = new Double2D(position.x, position.y-1);
			}else if(i == 4){
				movePosition = new Double2D(position.x+1, position.y+1);
			}else if(i == 5){
				movePosition = new Double2D(position.x-1, position.y-1);
			}else if(i == 6){
				movePosition = new Double2D(position.x-1, position.y+1);
			}else if(i == 7){
				movePosition = new Double2D(position.x+1, position.y-1);
			}
			return(movePosition);
	 }
	 
	 
	 public Double2D newCellPosition(){
		 Double x = this.random.nextDouble();
		 Double y = this.random.nextDouble();
		 return new Double2D((x * this.gridWidth/6 + this.gridWidth/6),(y * this.gridHeight)/6 + (this.gridHeight/6)); 
	 }

	 public Double2D positionMolecule(){
		 Double2D position = new Double2D((this.random.nextDouble() * this.gridWidth), (this.random.nextDouble() * this.gridHeight));
		 return position;
	 }	
	 
	 public Double2D positionTermozolomide(){
		 Double x = this.random.nextDouble();
		 Double y = this.random.nextDouble();
		 return new Double2D((x * this.gridWidth/8 + this.gridWidth/8),(y * this.gridHeight)/8 + (this.gridHeight/8)); 
	 }
	 
	 
	 public Double2D positionNewMolecule(){	
		 Double2D newPosition = null;
		// Boolean randomxy = this.random.nextBoolean(); 
		 Double xy = this.random.nextDouble()* this.getGridWidth();
		
		 Double randomxy = this.random.nextDouble() * 4;
		int i = randomxy.intValue();
			
			
		 if(i == 0){
			 newPosition = new Double2D(1, xy);
		 }else if(i == 1){
			 newPosition = new Double2D(xy, 1);
		 }else if(i == 2){
			 newPosition = new Double2D(xy, this.gridHeight-2);
		 }else if(i == 3){
			 newPosition = new Double2D(this.gridWidth-2, xy);
		 }
		 
		// System.out.print("new molecule:");
		 //System.out.print(newPosition);
		 //System.out.println();
		 return newPosition;
		 
	 }
	 public int getGridWidth(){
		 return this.gridWidth;
	 }
	
	 public int getGridHeigh(){
		 return this.gridHeight;
	 }

	 public int getNCells(){
		 return this.nCells;
	 }
	 public void setNCells(int ncells){
		 this.nCells = nCells; 
	 }
	 
	 public int getNMolecules(){
		 return this.nMolecules;
	 }
	 
public void setNMolecules(int nMolecules){
		 this.nMolecules = nMolecules;	 
	 }

	 public int getTermozolimide(){
		 return this.nMolecules;
	 }
	 public void setTermozolomide(int t){
		 this.nTermozolomide = t;	 
	 }

	 public int countObjectsInEnvirontment(Object object){
		 Bag b = this.environment.allObjects;
		 Iterator i = b.iterator();
		 int n = 0;
		 while (i.hasNext()){
			 Object tmp = i.next();
			 if(tmp.getClass() == object.getClass()) n++;
		 }
		 
		 return n;
	 }

}


