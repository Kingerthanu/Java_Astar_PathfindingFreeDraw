package src;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Node{
	public Rectangle obj;
	public int status;
	public ArrayList<Block> set;
	public Block(int x, int y, String id) {
		this.obj = new Rectangle(x, y, 20,20);
		this.obj.setFill(Color.rgb(0,0,0));
		this.status = 0;
		this.setId(id);
		this.obj.setOnMouseDragEntered(e -> {
			this.status = 1;
		});
		this.obj.setOnMousePressed(e -> {
			if(e.isPrimaryButtonDown()) {
				// Looks if entrance is present already in grid map (set); if so swap it with new block
				if(this.set.stream().filter(ez -> ez.status == 2).count() == 1) {
					this.set.stream().filter(ez -> ez.status == 2).collect(Collectors.toList()).get(0).status = 0;
				}
				this.status = 2;
			}
			else if(e.isSecondaryButtonDown()) {
				if(this.set.stream().filter(ez -> ez.status == 3).count() == 1) {
					this.set.stream().filter(ez -> ez.status == 3).collect(Collectors.toList()).get(0).status = 0;
				}
				this.status = 3;
			}
		});
	
	}
	public void setSet(ArrayList<Block> set) {
		this.set = set;
	}
	public void setBlockStatus(int stat) {
		this.status = stat;
	}
}

