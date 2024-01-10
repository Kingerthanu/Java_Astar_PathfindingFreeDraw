package src;

public class Coordinate {
	private int x;
	private int y;
	public boolean wall;
	public Block block;
	public Coordinate parent;
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		this.parent = null;
		this.wall = false;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setWall(boolean stat) {
		this.wall = stat;
	}
	public void setBlock(Block b) {
		this.block = b;
	}
}
