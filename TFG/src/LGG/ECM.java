package LGG;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Double2D;

public class ECM implements Steppable{
	private static final long serialVersionUID = 1L;

	public ECM(Environment status) {

	}

	public void step(SimState simstate) {
		Environment state = (Environment) simstate;

		 Double2D newOPosition= state.positionNewMolecule();
		 Oxygen o = new Oxygen(newOPosition);
		 state.environment.setObjectLocation(o, newOPosition);
		 state.schedule.scheduleRepeating(o);
		 Stoppable stopOx = state.schedule.scheduleRepeating(o);
		 o.setStop(stopOx);

		 Double2D newGluPosition= state.positionNewMolecule();
		 Glucose glu = new Glucose(newGluPosition);
		 state.environment.setObjectLocation(glu, newGluPosition);
		 state.schedule.scheduleRepeating(glu);
		 Stoppable stopGlu = state.schedule.scheduleRepeating(glu);
		 glu.setStop(stopGlu);
	}
}
