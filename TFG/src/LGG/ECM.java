package LGG;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Double2D;

public class ECM implements Steppable{

	//private int creation_GLU_ratio = 2;
	//private int creation_O_ratio = 2;

	public ECM(Environment status) {

	}

	public void step(SimState simstate) {
		Environment state = (Environment) simstate;
		// System.out.print("new molecule2:");
		// System.out.println();
		 
		// Añadir control de cuantas se crean
		 Double2D newOPosition= state.positionNewMolecule();//state.getPositionNewMolecule();
		 Oxygen o = new Oxygen(newOPosition);
		 state.environment.setObjectLocation(o, newOPosition);
		 state.schedule.scheduleRepeating(o);
		 Stoppable stopOx = state.schedule.scheduleRepeating(o);
		 o.setStop(stopOx);

		 // Añadir control de cuantas se crean
		 Double2D newGluPosition= state.positionNewMolecule();
		 Glucose glu = new Glucose(newGluPosition);
		 state.environment.setObjectLocation(glu, newGluPosition);
		 state.schedule.scheduleRepeating(glu);
		 Stoppable stopGlu = state.schedule.scheduleRepeating(glu);
		 glu.setStop(stopGlu);
		 
		 /*state.countObjectsInEnvirontment(termozolomide)
		 
		 Bag b = state.environment.getAllObjects();
		 b.iterator()
		 int i = state.getTermozolimide();*/
	}
}
