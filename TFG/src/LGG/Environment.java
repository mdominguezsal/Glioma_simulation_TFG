package LGG;

import java.util.Iterator;
import sim.field.continuous.Continuous2D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.engine.SimState;
import sim.engine.Stoppable;

public class Environment extends SimState {
	private static final long serialVersionUID = 1L;

	public Continuous2D environment;

	 // initial params
	 private Double radium = (double) 4;
	 private int gridWidth = 300;  //the width of the grid
	 private int gridHeight = 300; //the height of the grid
	 private int nInitCells = 50; //The number of particles
	 private int nInitMolecules = 10000;
	 private int nTermozolomide = 150;	 
	 
	 // counting params
	 
	 private int nCells = 0;
	 private int nNormalCells = 0;
	 private int nNormotoxicCells = 0;
	 private int nHypoxicCells = 0;
	 private int nHypoglycemicCells = 0;
	 private int nNecroticCells = 0;
	 private int nDeathBytermozolomide = 0;

	 // behavior params
	 
	 private int effectiveTermozolomide = 10;
	 
	 private double motilityRatioHypoxic = 0.2;
	 private double motilityRatioHypoglucemic = 0.3;
	 private double motilityRatioNormal = 0.1;
	 private double motilityRatioNormotoxic = 0.5;
	 
	 private double proliferationRatioHypoxic = 0.2;
	 private double proliferationRatioHypoglicemic = 0.3;
	 private double proliferationRatioNormal = 0;
	 private double proliferationRatioNormotoxic = 0.5;
	 
	 private double apoptosisValue = 0.00001;
	 private double changeToTumorRandom = 0.001;
 	 private double changeToNecroticbyContact = 0.001;
	 private double changeToTumorbyContact = 0.001;
	 
	 private double oxygenConsumtionNormal = 0.04;
	 private double oxygenConsumtionNormotoxic = 0.08;
	 private double oxygenConsumtionHipoglycemic = 0.005;
	 private double oxygenConsumtionHypoxic = 0.005;
	 
	 private double gluConsumtionNormal = 0.02;
	 private double gluConsumtionNormotoxic = 0.04;
	 private double gluConsumtionHipoglycemic = 0.005;
	 private double gluConsumtionHypoxic = 0.2;
	 
	 
 	 public Environment(long seed) {
		 super(seed);
	 }

	 public void start(){
		 super.start();
		 nCells = 0;
		 nNormalCells = 0;
		 nNormotoxicCells = 0;
		 nHypoxicCells = 0;
		 nHypoglycemicCells = 0;
		 nNecroticCells = 0;
		 nDeathBytermozolomide = 0;
		 
		 environment = new Continuous2D(1.0,gridWidth,gridHeight);

		 for(int i=0;i < nInitCells;i++){
			 Cell cell = new Cell(this);
			 environment.setObjectLocation(cell, cell.getPosition());
			 schedule.scheduleRepeating(cell);
			 nCells = nCells +1;
		 }

		 for(int i=0;i < nInitMolecules;i++){
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
	 }
	 
	 
	 public boolean cellInPosition(Double2D position, int hash, int radium){
		 Bag b = this.environment.getNeighborsExactlyWithinDistance(position, radium);
			if(b == null) return false;
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
	 
	 
	 public Double2D newCellPositionInit(){
		 Double x = this.random.nextDouble();
		 Double y = this.random.nextDouble();
		 return new Double2D((x * this.gridWidth/6 + this.gridWidth/3),(y * this.gridHeight)/6 + (this.gridHeight/3)); 
	 }

	 public Double2D positionMolecule(){
		 Double2D position = new Double2D((this.random.nextDouble() * this.gridWidth), (this.random.nextDouble() * this.gridHeight));
		 return position;
	 }	
	 
	 public Double2D positionTermozolomide(){
		 Double x = this.random.nextDouble();
		 Double y = this.random.nextDouble();
		 return new Double2D((x * this.gridWidth/6 + this.gridWidth/3),(y * this.gridHeight)/6 + (this.gridHeight/3)); 
	 }
	 

	 public Double2D positionNewMolecule(){	
		 Double2D newPosition = null;
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

		 return newPosition;

	 }
	 
	 public void setGridWidth(int width){
		 this.gridWidth = width;
	 }
	 
	 public int getGridWidth(){
		 return this.gridWidth;
	 }

	 public int getGridHeight(){
		 return this.gridHeight;
	 }
	 
	 public void setGridHeight(int height){
		 this.gridHeight = height;
	 }

	 public int getInitialCells(){
		 return this.nInitCells;
	 }

	 public void setInitialCells(int ncells){
		 this.nInitCells = ncells; 
	 }
	 
	 public int getNMolecules(){
		 return this.nInitMolecules;
	 }

	 public void setNMolecules(int nMolecules){
		 this.nInitMolecules = nMolecules;	 
	 }

	 public double getTermozolomide(){
		 return this.nTermozolomide;
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
 
	public int getEffectiveTermozolomide(){
		return effectiveTermozolomide;
	}
	
	public void setEffectiveTermozolomide(int effectiveTermozolomide){
		this.effectiveTermozolomide = effectiveTermozolomide;
	}
	
	public double getMotilityRatioHypoxic() {
		return motilityRatioHypoxic;
	}

	public void setMotilityRatioHypoxic(double motilityRatioHypoxic) {
		this.motilityRatioHypoxic = motilityRatioHypoxic;
	}

	public double getMotilityRatioHypoglycemic() {
		return motilityRatioHypoglucemic;
	}

	public void setMotilityRatioHypoglycemic(double motilityRatioHypoglucemic) {
		this.motilityRatioHypoglucemic = motilityRatioHypoglucemic;
	}

	public double getMotilityRatioNormal() {
		return motilityRatioNormal;
	}

	public void setMotilityRatioNormal(double motilityRatioNormal) {
		this.motilityRatioNormal = motilityRatioNormal;
	}

	public double getMotilityRatioNormotoxic() {
		return motilityRatioNormotoxic;
	}

	public void setMotilityRatioNormotoxic(double motilityRatioNormotoxic) {
		this.motilityRatioNormotoxic = motilityRatioNormotoxic;
	}

	public double getProliferationRatioHypoxic() {
		return proliferationRatioHypoxic;
	}

	public void setProliferationRatioHypoxic(double proliferationRatioHypoxic) {
		this.proliferationRatioHypoxic = proliferationRatioHypoxic;
	}

	public double getProliferationRatioHypoglycemic() {
		return proliferationRatioHypoglicemic;
	}

	public void setProliferationRatioHypoglycemic(double proliferationRatioHypoglicemic) {
		this.proliferationRatioHypoglicemic = proliferationRatioHypoglicemic;
	}

	public double getProliferationRatioNormal() {
		return proliferationRatioNormal;
	}

	public void setProliferationRatioNormal(double proliferationRatioNormal) {
		this.proliferationRatioNormal = proliferationRatioNormal;
	}

	public double getProliferationRatioNormotoxic() {
		return proliferationRatioNormotoxic;
	}

	public void setProliferationRatioNormotoxic(double proliferationRatioNormotoxic) {
		this.proliferationRatioNormotoxic = proliferationRatioNormotoxic;
	}

	public double getApoptosisValue() {
		return apoptosisValue;
	}

	public void setApoptosisValue(double randomchangetoNecrotic) {
		this.apoptosisValue = randomchangetoNecrotic;
	}

	public double getChangetoNecroticbyContact() {
		return changeToNecroticbyContact;
	}

	public void setChangetoNecroticbyContact(double changetoNecroticbyContact) {
		this.changeToNecroticbyContact = changetoNecroticbyContact;
	}

	public double getRandomChangetoTumor() {
		return changeToTumorRandom;
	}

	public void setRandomChangetoTumor(double randomChangetoTumor) {
		this.changeToTumorRandom = randomChangetoTumor;
	}

	public double getOxygenConsumtionNormal() {
		return oxygenConsumtionNormal;
	}

	public void setOxygenConsumtionNormal(double oxygenConsumtionNormal) {
		this.oxygenConsumtionNormal = oxygenConsumtionNormal;
	}

	public double getOxygenConsumtionNormotoxic() {
		return oxygenConsumtionNormotoxic;
	}

	public void setOxygenConsumtionNormotoxic(double oxygenConsumtionNormotoxic) {
		this.oxygenConsumtionNormotoxic = oxygenConsumtionNormotoxic;
	}

	public double getOxygenConsumtionHipoglycemic() {
		return oxygenConsumtionHipoglycemic;
	}
	
	public double getOxygenConsumtionHypoxic(){
		return oxygenConsumtionHypoxic;
	}
	
	public void setOxygenConsumtionHypoxic(double oxygenConsumtionHypoxic){
		this.oxygenConsumtionHypoxic = oxygenConsumtionHypoxic;
	}

	public void setOxygenConsumtionHipoglycemic(double oxygenConsumtionHipoglycemic) {
		this.oxygenConsumtionHipoglycemic = oxygenConsumtionHipoglycemic;
	}

	public double getGluConsumtionNormal() {
		return gluConsumtionNormal;
	}

	public void setGluConsumtionNormal(double gluConsumtionNormal) {
		this.gluConsumtionNormal = gluConsumtionNormal;
	}
	
	public double getGluConsumtionNormotoxic() {
		return gluConsumtionNormotoxic;
	}

	public void setGluConsumtionNormotoxic(double gluConsumtionNormotoxic) {
		this.gluConsumtionNormotoxic = gluConsumtionNormotoxic;
	}

	public double getGluConsumtionHipoglycemic() {
		return gluConsumtionHipoglycemic;
	}

	public void setGluConsumtionHypoxic(double gluConsumtionHypoxic) {
		this.gluConsumtionHypoxic = gluConsumtionHypoxic;
	}
	
	public double getGluConsumtionHypoxic() {
		return gluConsumtionHypoxic;
	}

	public void setGluConsumtionHipoglycemic(double gluConsumtionHipoglycemic) {
		this.gluConsumtionHipoglycemic = gluConsumtionHipoglycemic;
	}

	public double getChangeToTumorbyContact() {
		return changeToTumorbyContact;
	}

	public void setChangeToTumorbyContact(double changeToTumorbyContact) {
		this.changeToTumorbyContact = changeToTumorbyContact;
	}

	public int getNCells(){
		return nCells;
	}
	
	public void numberOfCellsChangeValue(int numCells){
		nCells = numCells;
	}
	
	public int getNumberOfNormalCells(){
		return nNormalCells;
	}
	
	public void numberOfNormalChangeValue(int numCells){
		nNormalCells = numCells;
	}
	 
	public int getNumberOfNormotoxicCells(){
		return nNormotoxicCells;
	}
	
	public void numberOfNormotoxicChangeValue(int numCells){
		nNormotoxicCells = numCells;
	}
	
	public int getNumberOfHypoxicCells(){
		return nHypoxicCells;
	}
	
	public void numberOfHypoxicChangeValue(int numCells){
		nHypoxicCells = numCells;
	}
		
	public int getNumberOfHypoglycemicCells(){
		return nHypoglycemicCells;
	}
	
	public void numberOfHypoglycemicChangeValue(int numCells){
		nHypoglycemicCells = numCells;
	}
		
	public int getNumberOfNecroticCells(){
		return nNecroticCells;
	}
	
	public void numberOfNecroticChangeValue(int numCells){
		nNecroticCells = numCells;
	}
	
	public int getNumberOfNecroticByTermozolomideCells(){
		return nDeathBytermozolomide;
	}
	
	public void numberOfDeathByTermozolomideChangeValue(int numCells){
		nDeathBytermozolomide = numCells;
	}
}


