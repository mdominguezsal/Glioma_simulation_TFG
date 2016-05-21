package LGG;

import java.awt.Color;

import sim.engine.SimState;
import sim.util.Double2D;

public class NecroticCellState extends CellState{
	private static final int MIN_GLUCOSE = 0;
	private static final int MIN_OXYGEN = 0;
	private static final float APOPTOSIS = 0;
	private static final int MOTILITY_RATIO = 0;
	private static final int PROLIFERATION_RATIO = 0;
	private Metabolism metabolism;
	private Motility motility;
	private Proliferation proliferation;
	private int radium = 10;
	//private Metabolism metabolism;


	public NecroticCellState() {
		color = Color.GRAY;
	}

	@Override
	public void executeState(Environment state, Cell cell) {
		
		// TODO Auto-generated method stub
		/*System.out.print("necrotyc");
		System.out.print(cell.getPosition());
		System.out.print("\n");
		/*Double2D d = new Double2D(0,0);
		state.environment.setObjectLocation(this,d);	*/
	}

	
}
