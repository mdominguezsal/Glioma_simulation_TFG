package LGG;

import java.util.ArrayList;
import java.util.List;

import sim.field.grid.SparseGrid2D;
import sim.engine.SimState;

public class Environment extends SimState {

	 public SparseGrid2D particleSpace;  
         //This is a 2D space in which bags
	  //are created for each cell once an
	 //agent or a particle first moves into a cell.  
        //This allows multiple
	 //agents or particles to occupy the same cell.

	 public int gridWidth = 50;  //the width of the grid
        public int gridHeight = 50; //the height of the grid
        public int n = 100; //The number of particles
        List<Cell> cells;
	 public Environment(long seed) {
		super(seed);
		// TODO Auto-generated constructor stub
	 }

	 public void start(){
		super.start();
		cells = new ArrayList<Cell>();
		particleSpace = new SparseGrid2D(gridWidth, gridHeight); 
               //create a 2D
		//space for our agents.

		/*
		 * Now, let's make n particles and put them into random
                *locations in particleSpace
                * To do this, we will use the random method built 
                * into SimState.
                * It has lot's of
               * methods, which make generating random locations easier.
               */

              for(int i=0;i < n;i++){
            	   Cell cell = new Cell(this);
                   particleSpace.setObjectLocation(cell, cell.x, cell.y);
                   schedule.scheduleRepeating(cell);
                   cells.add(cell);
              }
        }
}


