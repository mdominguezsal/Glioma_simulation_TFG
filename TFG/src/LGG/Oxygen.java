package LGG;

import java.util.Random;

import sim.engine.SimState;

public class Oxygen extends Molecule{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int y;
	public int x;
	
	public Oxygen(Environment environment) {
		Random rand = new Random();
		x = (int)(rand.nextDouble() * environment.gridWidth + 1);
		y = (int)(rand.nextDouble() * environment.gridHeight + 1);
		
		System.out.println("oxygen ___ x: "+x+" y: "+y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void step(SimState state) {
		// TODO Auto-generated method stub
		
	}

}
