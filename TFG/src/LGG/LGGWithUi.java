package LGG;

import sim.display.*;
import sim.portrayal.grid.*;

import java.awt.*;

import javax.swing.*;

import sim.portrayal.simple.OvalPortrayal2D;
import sim.engine.SimState;



public class LGGWithUi extends GUIState {
	public Display2D display;
	public JFrame displayFrame;
	SparseGridPortrayal2D cellPortrayal = new SparseGridPortrayal2D();

	
	public static void main(String[] args){
		LGGWithUi ex = new LGGWithUi();
		Console c = new Console(ex);
		c.setVisible(true);
		System.out.println("Start Simulation");
	}
	
	public LGGWithUi() {
		super(new Environment(System.currentTimeMillis()));
	}


	public void quit(){
		super.quit();
		if(displayFrame != null) displayFrame.dispose();displayFrame = null;
		display = null;
	}
	
	public void start(){
		super.start();
		setupPortrayals();
	}
	
	public void load(SimState state){
		super.load(state); 
		setupPortrayals();
	}

	private void setupPortrayals() {
		Environment se = (Environment)state;
		cellPortrayal.setField(se.particleSpace);
		OvalPortrayal2D o = new OvalPortrayal2D(Color.red);
		cellPortrayal.setPortrayalForAll(o);
		display.reset();
		display.repaint();
	}

	public void init(Controller c){
		super.init(c);
		display = new Display2D(400,400,this);
		displayFrame = display.createFrame();
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.setBackdrop(Color.black);
		display.attach(cellPortrayal,"Cells");
	}


	public Object getSimulationInspectedObject(){
		return state;
	}
}