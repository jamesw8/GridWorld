import java.util.Random;

public abstract class Animal{
	private int speed;			// cycles per move

	private int lifeSpan = 20;	// max age
	private int currentLife = 0;// current age

	private int minEnergy = 1;		// min energy to live
	private int currEnergy = 10;	// current energy
	private int maxEnergy = 20;		// max energy
	private int birthEnergy = 4;	// energy needed to give birth

	private int moved = 0;
	private int birthed = 0;
	private int x,y;			// location
	// private int rows, cols;		// grid specifications

	public int getSpeed(){return this.speed;}
	public int getLifeSpan(){return this.lifeSpan;}
	public int getCurrentLife(){return this.currentLife;}
	public int getMinEnergy(){return this.minEnergy;}
	public int getCurrEnergy(){return this.currEnergy;}
	public int getMaxEnergy(){return this.maxEnergy;}
	public int getBirthEnergy(){return this.birthEnergy;}
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public int getMoved(){return this.moved;}
	public int getBirthed(){return this.birthed;}

	public void setSpeed(int val){this.speed = val;}
	public void setLifeSpan(int val){this.lifeSpan = val;}
	public void setCurrentLife(int val){this.currentLife = val;}
	public void setMinEnergy(int val){this.minEnergy = val;}
	public void setCurrEnergy(int val){this.currEnergy = val;}
	public void setMaxEnergy(int val){this.maxEnergy = val;}
	public void setBirthEnergy(int val){this.birthEnergy = val;}
	public void setX(int val){this.x = val;}
	public void setY(int val){this.y = val;}
	public void setMoved(int val){this.moved = val;}
	public void setBirthed(int val){this.birthed= val;}

	public boolean giveBirth(Grid grid){ 
		boolean birthed = false;
		if(getBirthed()==0 && getCurrentLife()>=4){
			if(getCurrEnergy() >= getBirthEnergy()+getMinEnergy()){ // can give birth
				
				int x = getX();
				int y = getY();
				birthed = false;
				if(this instanceof Herbivore){
					if(x!=0 && grid.grid[x-1][y]==null){
						grid.spawn(x-1,y,new Herbivore(x-1,y));
						//System.out.println("SPAWNING HERBIVORE AT "+(x-1)+" "+y);
						birthed = true;
					}
					else if(x!=(grid.rowNum-1) && grid.grid[x+1][y]==null){
						grid.spawn(x+1,y,new Herbivore(x+1,y));
						//System.out.println("SPAWNING HERBIVORE AT "+(x+1)+" "+y);
						birthed = true;
					}
					else if(y!=0 && grid.grid[x][y-1]==null){
						grid.spawn(x,y-1,new Herbivore(x,y-1));
						//System.out.println("SPAWNING HERBIVORE AT "+x+" "+(y-1));
						birthed = true;
					}
					else if(y!=(grid.colNum-1) && grid.grid[x][y+1]==null){
						grid.spawn(x,y+1,new Herbivore(x,y+1));
						//System.out.println("SPAWNING HERBIVORE AT "+x+" "+(y+1));
						birthed = true;
					}
				}
				else if(this instanceof Carnivore){
					if(x!=0 && grid.grid[x-1][y]==null){
						grid.spawn(x-1,y,new Carnivore(x-1,y));
						//System.out.println("SPAWNING CARNIVORE AT "+(x-1)+" "+y);
						birthed = true;
					}
					else if(x!=(grid.rowNum-1) && grid.grid[x+1][y]==null){
						grid.spawn(x+1,y,new Carnivore(x+1,y));
						//System.out.println("SPAWNING CARNIVORE AT "+(x+1)+" "+y);
						birthed = true;
					}
					else if(y!=0 && grid.grid[x][y-1]==null){
						grid.spawn(x,y-1,new Carnivore(x,y-1));
						//System.out.println("SPAWNING CARNIVORE AT "+x+" "+(y-1));
						birthed = true;
					}
					else if(y!=(grid.colNum-1) && grid.grid[x][y+1]==null){
						grid.spawn(x,y+1,new Carnivore(x,y+1));
						//System.out.println("SPAWNING CARNIVORE AT "+x+" "+(y+1));
						birthed = true;
					}
				}
				if(birthed){
					setCurrEnergy( getCurrEnergy() - 3 );
					//System.out.println(grid.grid[x][y].getClass() + " was birthed");
					setBirthed(1);
				}
			}
		}
		return birthed;
	}
	public void move(Grid grid){
		if(getMoved()==1)
			return;
		// check if can move and then random place to move
		Random random = new Random();
		int[] possibleMoves = new int[8]; // up down left right upleft upright downleft downright

		for(int i = 0; i < 8; i++)
			possibleMoves[i] = 0;
		int x = getX();
		int y = getY();
		int pos = -1;
		int energyEaten = 0;
		if(x!=0 && eat(grid.grid[x-1][y])){
			possibleMoves[0] = 1;
			if(grid.grid[x-1][y] != null) // if food then eat
				pos = 0;
				if(grid.grid[x-1][y] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x-1][y]).getCurrEnergy();
				else if(grid.grid[x-1][y] instanceof Plant)
					energyEaten = 3;
		}
		if(x!=(grid.rowNum-1) && eat(grid.grid[x+1][y])){
			possibleMoves[1] = 1;
			if(grid.grid[x+1][y] != null)
				pos = 1;
				if(grid.grid[x+1][y] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x+1][y]).getCurrEnergy();
				else if(grid.grid[x+1][y] instanceof Plant)
					energyEaten = 3;
		}
		if(y!=0 && eat(grid.grid[x][y-1])){
			possibleMoves[2] = 1;
			if(grid.grid[x][y-1] != null)
				pos = 2;
				if(grid.grid[x][y-1] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x][y-1]).getCurrEnergy();
				else if(grid.grid[x][y-1] instanceof Plant)
					energyEaten = 3;
		}
		if(y!=(grid.colNum-1) && eat(grid.grid[x][y+1])){
			possibleMoves[3] = 1;
			if(grid.grid[x][y+1] != null)
				pos = 3;
				if(grid.grid[x][y+1] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x][y+1]).getCurrEnergy();
				else if(grid.grid[x][y+1] instanceof Plant)
					energyEaten = 3;
		}

		if(possibleMoves[0]==1 && possibleMoves[2]==1 && eat(grid.grid[x-1][y-1])){
			possibleMoves[4] = 1;
			if(grid.grid[x-1][y-1] != null)
				pos = 4;
				if(grid.grid[x-1][y-1] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x-1][y-1]).getCurrEnergy();
				else if(grid.grid[x-1][y-1] instanceof Plant)
					energyEaten = 3;
		}
		if(possibleMoves[0]==1 && possibleMoves[3]==1 && eat(grid.grid[x-1][y+1])){
			possibleMoves[5] = 1;
			if(grid.grid[x-1][y+1] != null)
				pos = 5;
				if(grid.grid[x-1][y+1] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x-1][y+1]).getCurrEnergy();
				else if(grid.grid[x-1][y+1] instanceof Plant)
					energyEaten = 3;
		}
		if(possibleMoves[1]==1 && possibleMoves[2]==1 && eat(grid.grid[x+1][y-1])){
			possibleMoves[6] = 1;
			if(grid.grid[x+1][y-1] != null)
				pos = 6;
				if(grid.grid[x+1][y-1] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x+1][y-1]).getCurrEnergy();
				else if(grid.grid[x+1][y-1] instanceof Plant)
					energyEaten = 3;
		}
		if(possibleMoves[1]==1 && possibleMoves[3]==1 && eat(grid.grid[x+1][y+1])){
			possibleMoves[7] = 1;
			if(grid.grid[x+1][y+1] != null)
				pos = 7;
				if(grid.grid[x+1][y+1] instanceof Animal)
					energyEaten = ((Animal)grid.grid[x+1][y+1]).getCurrEnergy();
				else if(grid.grid[x+1][y+1] instanceof Plant)
					energyEaten = 3;
		}

		int good = 0; int tryPos;
		boolean ate = false;
		for(int i = 0; i < possibleMoves.length; i++)
			if(possibleMoves[i]==1){
				good=1;
				ate = true;
			}
		if(good==1){ 
			while(pos<0){ // IF NO FOOD THEN RANDOM
				ate = false;
				tryPos = random.nextInt(8);
				if(possibleMoves[tryPos]==1)
					pos = tryPos;
			}
		}

		if(ate){
			//System.out.print("Energy before eating: "+getCurrEnergy());
			setCurrEnergy( getCurrEnergy() + energyEaten );
			//System.out.println(" Energy after eating: "+getCurrEnergy());

		}

		switch(pos){
			case 0:
				setX(x-1); // UP
				grid.grid[x-1][y] = this;
				grid.grid[x][y] = null;
				break;
			case 1:
				setX(x+1); // DOWN
				grid.grid[x+1][y] = this;
				grid.grid[x][y] = null;
				break; 
			case 2:
				setY(y-1); // LEFT
				grid.grid[x][y-1] = this;
				grid.grid[x][y] = null;
				break; 
			case 3:
				setY(y+1); // RIGHT
				grid.grid[x][y+1] = this;
				grid.grid[x][y] = null;
				break; 
			case 4:
				setX(x-1); // UPLEFT
				setY(y-1);
				grid.grid[x-1][y-1] = this;
				grid.grid[x][y] = null;
				break;
			case 5:
				setX(x-1); // UPRIGHT
				setY(y+1);
				grid.grid[x-1][y+1] = this;
				grid.grid[x][y] = null;
				break;
			case 6:
				setX(x+1); // DOWNLEFT
				setY(y-1);
				grid.grid[x+1][y-1] = this;
				grid.grid[x][y] = null;
				break;
			case 7:
				setX(x+1); // DOWNRIGHT
				setY(y+1);
				grid.grid[x+1][y+1] = this;
				grid.grid[x][y] = null;
				break;
		}
		setMoved(1);
	}
	
	abstract boolean eat(Object food);
}