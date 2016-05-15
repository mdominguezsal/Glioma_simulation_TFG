package LGG;

import java.util.ArrayList;
import java.util.List;

import sim.field.continuous.Continuous2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Double2D;
import sim.engine.SimState;
import sim.engine.Stoppable;

public class Environment extends SimState {
	 public Continuous2D environtment;
	
         //This is a 2D space in which bags
	  //are created for each cell once an
	 //agent or a particle first moves into a cell.  
        //This allows multiple
	 //agents or particles to occupy the same cell.

	 private int gridWidth = 100;  //the width of the grid
	 private int gridHeight = 100; //the height of the grid
	 private int nCells = 30; //The number of particles
	 private int nMolecules = 300;
	 
	 public Environment(long seed) {
		 super(seed);
	 }

	 public void start(){
		 super.start();
		environtment = new Continuous2D(1.0,gridWidth,gridHeight);

		 for(int i=0;i < nCells;i++){
			 Cell cell = new Cell(this);
			 environtment.setObjectLocation(cell, cell.getPosition());
			 schedule.scheduleRepeating(cell);
			 //Stoppable stopCell = schedule.schedulerepeating(cell);
		 }

		 for(int i=0;i < nMolecules;i++){
			 Oxygen oxygen = new Oxygen(this);
			 environtment.setObjectLocation(oxygen, oxygen.position);
			 schedule.scheduleRepeating(oxygen);
			 Stoppable stopOx = schedule.scheduleRepeating(oxygen);
			 oxygen.setStop(stopOx);

			 Glucose glucose = new Glucose(this);
			 environtment.setObjectLocation(glucose, glucose.position);
			 Stoppable stopGlu = schedule.scheduleRepeating(glucose);
			 glucose.setStop(stopGlu);
		 }
		 //cellSpace.setObjectLocation(cell, cell.x, cell.y);
		 //cells.add(cell);
		 //oxygenSpace.setObjectLocation(oxygen, oxygen.x, oxygen.y);
	 }
	 
	 public void setgridWidth(int i){
		 this.gridWidth = i;
	 }
	 
	 public int getGridWidth(){
		 return this.gridWidth;
	 }
	 
	 public void setgridHeigh(int i){
		 this.gridHeight = i;
	 }
	 
	 public int getGridHeigh(){
		 return this.gridHeight;
	 }
}


