class Carnivore extends Animal{
	public Carnivore(int x, int y){
		setSpeed(1);

		setX(x);
		setY(y);
	}

	public boolean eat(Object food){
		if(food instanceof Herbivore)
			if(getCurrEnergy() <= getMaxEnergy()-3){return true;}
			else{return false;}
		else if(food instanceof Plant || food instanceof Carnivore)
			return false;
		else
			return true;
	}
}