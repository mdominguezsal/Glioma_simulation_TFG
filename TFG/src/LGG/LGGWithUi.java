package LGG;

import sim.display.*;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.*;

import java.awt.*;

import javax.swing.*;

import sim.portrayal.simple.OvalPortrayal2D;
import sim.engine.SimState;



public class LGGWithUi extends GUIState {
	public Display2D display;
	public JFrame displayFrame;
	//SparseGridPortrayal2D cellPortrayal = new SparseGridPortrayal2D();
	//SparseGridPortrayal2D nutrientsPortrayal = new SparseGridPortrayal2D();
	ContinuousPortrayal2D portrayal = new ContinuousPortrayal2D();

	
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
		portrayal.setField(se.environtment);
		portrayal.setPortrayalForClass(Cell.class, new OvalPortrayal2D(Color.green,3));
		portrayal.setPortrayalForClass(Oxygen.class, new OvalPortrayal2D(Color.blue));
		portrayal.setPortrayalForClass(Glucose.class, new OvalPortrayal2D(Color.cyan));

		//cellPortrayal.setField(se.environtment);
		
		//OvalPortrayal2D o = new OvalPortrayal2D(Color.red);
		//cellPortrayal.setPortrayalForAll(o);
		
		
		//nutrientsPortrayal.setField(se.environtment);
		//OvalPortrayal2D p = new OvalPortrayal2D(Color.blue);
		//nutrientsPortrayal.setPortrayalForAll(p);
		
		display.reset();
		display.repaint();
	}

	public void init(Controller c){
		super.init(c);
		display = new Display2D(1000,1000,this);
		displayFrame = display.createFrame();
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.setBackdrop(Color.black);
		display.attach(portrayal,"Cells");
		//display.attach(nutrientsPortrayal, "o");
		
	}


	public Object getSimulationInspectedObject(){
		return state;
	}
}