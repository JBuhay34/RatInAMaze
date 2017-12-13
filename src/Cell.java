

public class Cell {
	
	
	private Cell neighborRight;
	private Cell neighborLeft;
	private Cell neightborUp;
	private Cell neighborDown;
	
	private int northWall, southWall, eastWall, westWall;
	
	private int alreadyDrawnBefore=0;
	
	public Cell(int northWall, int southWall, int eastWall, int westWall) {
		this.northWall = northWall;
		this.southWall = southWall;
		this.eastWall = eastWall;
		this.westWall = westWall;
	}
	
	public int getNorthWall() {
		return northWall;
	}
	
	public int getSouthWall() {
		return southWall;
	}
	
	public int getEastWall() {
		return eastWall;
	}
	
	public int getWestWall() {
		return westWall;
	}
	
	public void setAlreadyDrawnBefore(int alreadyDrawnBefore) {
		this.alreadyDrawnBefore = alreadyDrawnBefore;
	}
	
	public int getAlreadyDrawnBefore() {
		return alreadyDrawnBefore;
	}
	
	

	 

		 
}
