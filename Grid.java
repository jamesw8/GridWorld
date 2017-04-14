// By James Wong

import java.util.Scanner;
import java.util.Random;

public class Grid{
	
	public Object[][] grid;
	public int rowNum = 30, colNum = 30;
	public Grid(){
		grid = new Object[rowNum][colNum];
	}

	// override toString()
	public String toString(){
		String gridPrint = "";
		for(int row = 0; row < rowNum; row++){ // for each row

			for(int col = 0; col < colNum; col++){ // for each column
				if(grid[row][col] instanceof Herbivore)
					gridPrint+="& ";
				else if (grid[row][col] instanceof Carnivore)
					gridPrint+="@ ";
				else if (grid[row][col] instanceof Plant)
					gridPrint+="* ";
				else // Free Space
					gridPrint+=". ";
			}
			gridPrint+="\n";
		}

		return gridPrint;
	}

	public void spawn(int row, int col, Object entity){
		grid[row][col] = entity;
	}

	// public void birth()
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		Random rand = new Random();
		Grid grid = new Grid();

		System.out.println("Insert # of cycles");
		int cycles = input.nextInt(); //
		int spawnRow, spawnCol;
		grid.spawn(0,0, new Herbivore(0,0));

		// INITIAL RANDOM HERBIVORE PLACEMENTS
		//System.out.println( 2.0*(grid.rowNum*grid.colNum)/15 );
		for(int i = 0; i < rand.nextInt( (int)(12.0*(grid.rowNum*grid.colNum)/15) ); i++){
			spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);
			while(grid.grid[spawnRow][spawnCol] != null){
				spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);
			}
			grid.spawn(spawnRow,spawnCol,new Herbivore(spawnRow,spawnCol));
		}

		// INITIAL RANDOM CARNIVORE PLACEMENTS
		for(int i = 0; i < rand.nextInt( (int)(1.0*(grid.rowNum*grid.colNum)/15) ); i++){
			spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);
			while(grid.grid[spawnRow][spawnCol] != null){
				spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);
			}
			grid.spawn(spawnRow,spawnCol,new Carnivore(spawnRow,spawnCol));
		}

		// INITIAL RANDOM PLANT PLACEMENTS
		for(int i = 0; i < rand.nextInt( (int)(2.0*(grid.rowNum*grid.colNum)/15) ); i++){
			spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);
			while(grid.grid[spawnRow][spawnCol] != null){
				spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);
			}
			grid.spawn(spawnRow,spawnCol,new Plant(spawnRow,spawnCol));
		}
		System.out.println("CYCLE 0");
		System.out.println(grid);
		for(int cycle = 1; cycle <= cycles; cycle++){ // iterate through cycles
			System.out.println("CYCLE "+cycle);
			if(cycle % 3 == 0){ // spawn a plant every 3 cycles
				for(int i = 0; i < 3; i++){
					spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);
					while(grid.grid[spawnRow][spawnCol] != null){
						spawnRow = rand.nextInt(grid.rowNum); spawnCol = rand.nextInt(grid.colNum);				
					}
					grid.spawn(spawnRow,spawnCol,new Plant(spawnRow,spawnCol));
				}
			}

			// UPDATE ENTITIES
			for(int row = 0; row < grid.rowNum; row++){ // for each row
				for(int col = 0; col < grid.colNum; col++){ // for each column
					if(grid.grid[row][col] != null){
						if(grid.grid[row][col] instanceof Animal){ // update Animal
							
							if(((Animal)grid.grid[row][col]).getCurrEnergy() < ((Animal)grid.grid[row][col]).getMinEnergy()){
								grid.grid[row][col] = null; // death
								continue;
							}

							if(((Animal)grid.grid[row][col]).giveBirth(grid)); // checks if can birth and gives birth
							else{
								if(cycle % ((Animal)grid.grid[row][col]).getSpeed() == 0){ // otherwise moves
									((Animal)grid.grid[row][col]).move(grid);
								}
							}
						}
					}
				}
			}

			// CYCLIC UPDATES/RESETS
			for(int r = 0; r < grid.rowNum; r++)
					for(int c = 0; c < grid.colNum; c++)
						if(grid.grid[r][c] instanceof Animal){
							((Animal)grid.grid[r][c]).setMoved(0); // reset moved
							((Animal)grid.grid[r][c]).setBirthed(0); // reset birthed
							((Animal)grid.grid[r][c]).setCurrentLife( ((Animal)grid.grid[r][c]).getCurrentLife()+1 );
							((Animal)grid.grid[r][c]).setCurrEnergy( ((Animal)grid.grid[r][c]).getCurrEnergy()-1);
							if( ((Animal)grid.grid[r][c]).getCurrentLife() >= ((Animal)grid.grid[r][c]).getLifeSpan() )
								grid.grid[r][c] = null; // death
						}

			// STARVE HERBIVORES
			if(cycle == cycles)
				for(int r = 0; r < grid.rowNum; r++)
					for(int c = 0; c < grid.colNum; c++)
						if(grid.grid[r][c] instanceof Herbivore)
							grid.spawn(r,c,null);

			System.out.println(grid);
		}	
	}
}