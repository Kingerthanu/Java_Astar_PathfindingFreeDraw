package src;

import java.util.ArrayList;

public class Map {
	public ArrayList<ArrayList<Coordinate>> matrix = new ArrayList<ArrayList<Coordinate>>(0);
	public Coordinate entry;
	public Coordinate exit;
	public Map(ArrayList<Block> mirrr) {
		translate(mirrr);
}
	public void translate(ArrayList<Block> mirror) {
		ArrayList<Coordinate> ad = new ArrayList<Coordinate>();
		for(Block each : mirror) {
			if(Integer.parseInt(each.getId()) != 0 && Integer.parseInt(each.getId()) % 40 == 0) {
				matrix.add(ad);
				ad = new ArrayList<Coordinate>();
			}
			Coordinate tmp = new Coordinate(Integer.parseInt(each.getId())%40, matrix.size());
			tmp.setBlock(each);
			if(each.status == 1) {
				tmp.setWall(true);
				ad.add(tmp);
			}
			else if(each.status == 2) {
				this.entry = tmp;
				ad.add(tmp);
			}
			else if(each.status == 3) {
				this.exit = tmp;
				ad.add(tmp);
			}
			else{
				ad.add(tmp);
			}
		}
		matrix.add(ad);
	}
	public Coordinate Fetch(int row, int col) {
		for(ArrayList<Coordinate> ro : matrix) {
			for(Coordinate each : ro) {
				if(each.getX() == row && each.getY() == col) {
					return each;
				}
			}
		}
		return null;
	}
	public Coordinate getEntry() {
		return this.entry;
	}
	public Coordinate getExit() {
		return this.exit;
	}
	public void setVisited(int row, int col) {
		this.Fetch(row,col).setWall(true);
	}
	public boolean isValidLocation(int row, int col) {
		if(Fetch(row,col).wall == false) {
			return true;
		}
		return false;
	}
	public boolean isWall(int row, int col) {
		if(this.Fetch(row,col).wall == true) {
			return true;
		}
		return false;
	}
	public boolean isExplored(int row, int col) {
		if(this.Fetch(row,col).wall != true) {
			return false;
		}
		return true;
	}
	public boolean isExit(int row, int col) {
		if(this.Fetch(row,col).getX() == this.exit.getX() && this.Fetch(row,col).getY() == this.exit.getY()) {
			return true;
		}
		return false;
	}
	public boolean isInBounds(int row, int col) {
		if(row == 20 || col == 20) {
			return false;
		}
		return true;
	}
	

}
