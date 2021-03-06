package LGG;

import sim.display.*;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;

import java.awt.*;

import javax.swing.*;

import sim.portrayal.simple.OvalPortrayal2D;
import sim.engine.SimState;



public class LGGWithUi extends GUIState {
	public Display2D display;
	public JFrame displayFrame;
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
	
	public LGGWithUi(SimState state) {
		super(state);
	}
	
	public static String getName(){
		return "LGG";
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
		portrayal.setField(se.environment);
		OvalPortrayal2D cellPortrayal = new OvalPortrayal2D(){
			@Override
		    public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
		    	Cell cell = (Cell)object;
		    	paint = cell.getCellState().getColor();
		    	scale = 5;
				super.draw(object, graphics, info);
			}
		};
		portrayal.setPortrayalForClass(Cell.class, cellPortrayal);
		portrayal.setPortrayalForClass(Oxygen.class, new OvalPortrayal2D(Color.CYAN));
		portrayal.setPortrayalForClass(Glucose.class, new OvalPortrayal2D(Color.WHITE));
		portrayal.setPortrayalForClass(Termozolomide.class,  new OvalPortrayal2D(Color.yellow ,2));

		display.reset();
		display.repaint();
	}

	public void init(Controller c){
		super.init(c);
		display = new Display2D(800,800,this);
		displayFrame = display.createFrame();
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.setBackdrop(Color.BLACK);
		display.attach(portrayal,"Cells");
	}


	public Object getSimulationInspectedObject(){
		return state;
	}
}