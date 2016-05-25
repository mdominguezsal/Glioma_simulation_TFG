package LGG;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sim.field.continuous.Continuous2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Bag;
import sim.util.Double2D;
import sim.engine.SimState;
import sim.engine.Stoppable;

public class Environment extends SimState {
	 public Continuous2D environment;
	 private Double radium = (double) 4;
	 private int gridWidth = 300;  //the width of the grid
	 private int gridHeight = 300; //the height of the grid
	 private int nInitCells = 50; //The number of particles
	 private int nNecroticCells = 0;
	 private int nNormtoxicCells = 0;
	 private int nHypoxicCells = 0;
	 private int nHypoglicemicCells = 0;
	 private int nInitMolecules = 1000;
	 private int nO;
	 private int nGlu;
	 private double nTermozolomide = 50;
	 private int maxConcentrationCell = 300;
	 private int maxConcentrationMolecules = 10000;
	 private int proliferationConstant = 1;
	 private int conversionToNecrotic = 1;
	 
	 //////
	 
	 private double motilityRatioHypoxic;
	 private double motilityRatioHypoglucemic;
	 private double motilityRatioNormal;
	 private double motilityRatioNormotoxic;
	 
	 private double proliferationRatioHypoxic;
	 private double proliferationRatioHypoglicemic;
	 private double proliferationRatioNormal;
	 private double proliferationRatioNormotoxic;
	 
	 private double randomchangetoNecrotic;
	 private double changetoNecroticbyContact;
	 private double randomChangetoTumor;
	 
	 private double oxygenConsumtionNormal;
	 private double oxygenConsumtionNormotoxic;
	 private double oxygenConsumtionHipoglycemic;
	 
	 private double gluConsumtionNormal;
	 private double gluConsumtionNormotoxic;
	 private double gluConsumtionHipoglycemic;
	 
	 
 	 public Environment(long seed) {
		 super(seed);
	 }

	 public void start(){
		 super.start();
		environment = new Continuous2D(1.0,gridWidth,gridHeight);

		 for(int i=0;i < nInitCells;i++){
			 Cell cell = new Cell(this);
			 environment.setObjectLocation(cell, cell.getPosition());
			 schedule.scheduleRepeating(cell);
			 //Stoppable stopCell = schedule.schedulerepeating(cell);
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
		 //cellSpace.setObjectLocation(cell, cell.x, cell.y);
		 //cells.add(cell);
		 //oxygenSpace.setObjectLocation(oxygen, oxygen.x, oxygen.y);
	 }
	 
	 
	 public boolean cellInPosition(Double2D position, int hash, int radium){
		 Bag b = this.environment.getNeighborsExactlyWithinDistance(position, radium);
			if(b == null) return false;
			//System.out.print("\n");
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
		 return new Double2D((x * this.gridWidth/6 + this.gridWidth/6),(y * this.gridHeight)/6 + (this.gridHeight/6)); 
	 }

	 public Double2D positionMolecule(){
		 Double2D position = new Double2D((this.random.nextDouble() * this.gridWidth), (this.random.nextDouble() * this.gridHeight));
		 return position;
	 }	
	 
	 public Double2D positionTermozolomide(){
		 Double x = this.random.nextDouble();
		 Double y = this.random.nextDouble();
		 return new Double2D((x * this.gridWidth/8 + this.gridWidth/8),(y * this.gridHeight)/8 + (this.gridHeight/8)); 
	 }
	 

	 public Double2D positionNewMolecule(){	
		 Double2D newPosition = null;
		 // Boolean randomxy = this.random.nextBoolean(); 
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

		 // System.out.print("new molecule:");
		 //System.out.print(newPosition);
		 //System.out.println();
		 return newPosition;

	 }
	 public int getGridWidth(){
		 return this.gridWidth;
	 }

	 public int getGridHeigh(){
		 return this.gridHeight;
	 }

	 public int getNCells(){
		 return this.nInitCells;
	 }

	 public void setNCells(int ncells){
		 this.nInitCells = nInitCells; 
	 }

	 public int getNMolecules(){
		 return this.nInitMolecules;
	 }

	 public void setNMolecules(int nMolecules){
		 this.nInitMolecules = nMolecules;	 
	 }

	 public double getTermozolimide(){
		 return this.nTermozolomide;
	 }
	 public void setTermozolomide(double t){
		 this.nTermozolomide = t;	 
	 }

	 public int getconversionToNecrotic(){
		 return this.conversionToNecrotic;
	 }
	 public void setconversionToNecrotic(int t){
		 this.conversionToNecrotic = t;
	 }

	 public void setProliferationConstant(int constant){
		 this.proliferationConstant = constant;
	 }

	 public int getProliferationConstant(){
		 return this.proliferationConstant;
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

 	 private double nutrientCoefficient(int concentration){
 		 int nh = 0;
 		 if(concentration <= 0) nh = 1;	 
 		 double d = 1 - (this.bhAndbq((double)concentration)+this.conversionToNecrotic) * nh; 	
 		 return d;
 	 }
 	 


 	 private double bhAndbq(double concentration){ 
 		 double d = (1 - (double)concentration/this.nInitMolecules)/20;
 		 return d;
 	 }


 	 public int concentration(Double2D position, Class c){
 		 Bag b = this.environment.getNeighborsWithinDistance(position, this.radium);
 		 Iterator it = b.iterator();
 		 int concentration = 0;

 		 while (it.hasNext()){
 			 Object tmp = it.next();
 			 if(tmp.getClass() == c){
 				 concentration++;
 			 }
 		 }
 		 return concentration;
 	 }

 	 private void addCellToCounting(int i){
 		 this.nInitCells = this.nInitCells++;
 	 }
 	 
 	 public Double getT(){
 		 return  (double) (this.nInitCells/this.maxConcentrationCell);
 	 }

	public int getnNormtoxicCells() {
		return nNormtoxicCells;
	}

	public void setnNormtoxicCells(int nNormtoxicCells) {
		this.nNormtoxicCells = nNormtoxicCells;
	}

	public int getnNecroticCells() {
		return nNecroticCells;
	}

	public void setnNecroticCells(int nNecroticCells) {
		this.nNecroticCells = nNecroticCells;
	}

	public int getnHypoxicCells() {
		return nHypoxicCells;
	}

	public void setnHypoxicCells(int nHypoxicCells) {
		this.nHypoxicCells = nHypoxicCells;
	}

	public int getnHypoglicemicCells() {
		return nHypoglicemicCells;
	}

	public void setnHypoglicemicCells(int nHypoglicemicCells) {
		this.nHypoglicemicCells = nHypoglicemicCells;
	}

	public int getGlq() {	
	return (int) this.nutrientCoefficient(this.nGlu);
	}


	public int getNh() {
		return (int)this.nutrientCoefficient(this.nO);
	}

	public double getMotilityRatioHypoxic() {
		return motilityRatioHypoxic;
	}

	public void setMotilityRatioHypoxic(double motilityRatioHypoxic) {
		this.motilityRatioHypoxic = motilityRatioHypoxic;
	}

	public double getMotilityRatioHypoglucemic() {
		return motilityRatioHypoglucemic;
	}

	public void setMotilityRatioHypoglucemic(double motilityRatioHypoglucemic) {
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

	public double getProliferationRatioHypoglicemic() {
		return proliferationRatioHypoglicemic;
	}

	public void setProliferationRatioHypoglicemic(
			double proliferationRatioHypoglicemic) {
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

	public double getRandomchangetoNecrotic() {
		return randomchangetoNecrotic;
	}

	public void setRandomchangetoNecrotic(double randomchangetoNecrotic) {
		this.randomchangetoNecrotic = randomchangetoNecrotic;
	}

	public double getChangetoNecroticbyContact() {
		return changetoNecroticbyContact;
	}

	public void setChangetoNecroticbyContact(double changetoNecroticbyContact) {
		this.changetoNecroticbyContact = changetoNecroticbyContact;
	}

	public double getRandomChangetoTumor() {
		return randomChangetoTumor;
	}

	public void setRandomChangetoTumor(double randomChangetoTumor) {
		this.randomChangetoTumor = randomChangetoTumor;
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

	public void setGluConsumtionHipoglycemic(double gluConsumtionHipoglycemic) {
		this.gluConsumtionHipoglycemic = gluConsumtionHipoglycemic;
	}

}


