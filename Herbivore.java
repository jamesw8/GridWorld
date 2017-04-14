class Herbivore extends Animal{
	public Herbivore(int x, int y){
		setSpeed(2);

		setX(x);
		setY(y);
	}

	public boolean eat(Object food){
		if(food instanceof Plant)
			if(getCurrEnergy() <= getMaxEnergy()-3){return true;}
			else{return false;}
		else if(food instanceof Carnivore || food instanceof Herbivore)
			return false;
		else
			return true;
	}
}