package src;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;

public class Main extends Application {
	private Scene sc;
	private boolean state = false;
	private int r,g,b = 0;
	private Pane root;
	private Timer timer = new Timer(true);
	public Map map;
	public List<Coordinate> route;
	private static ArrayList<Block> set = new ArrayList<Block>() {
		private static final long serialVersionUID = 1L;
	{
		int m = 0;
		for(int i = 0; i < 800; i += 20) {
			for(int d = 0; d < 800; d += 20) {
				add(new Block(d,i,""+m));
				m++;
			}
		}
	}};
    public static void main(String[] args) {
		for(Block each : set) {
			each.setSet(set);
		}
        launch(args);
    }
    public void ColorChange() {
    	//System.out.println(r + " " + " " + g + " " + b);
    	if(!state) {
	    	if(r < 255 && g == 0) {
	    		if(b-1 != -1) {
	    		b--;
	    		if(b == 1) {
	    			b--;
	    			state = true;
	    		}}
	    		r++;
	    	}
	    	else if(g < 255 && b == 0) {
	    		r--;
	    		g++;
	    	}
	    	else if(b < 255) {
	    		g--;
	    		b++;
	    	}
    	}
    	else {
    		if(g < 255 && b == 0) {
	    		g++;
	    	}
	    	else if(r > 0) {
	    		r--;
	    	}
	    	else if(b < 255) {
	    		b++;
	    		g--;
	    	}
	    	else {
	    		state = false;
	    	}
    	}
    }

    @Override
    public void start(Stage arg0) throws Exception {
        arg0.setTitle("Rainbow Paint PathFinder");
        root = new Pane();
        sc = new Scene(root, 800, 800);
        sc.setOnDragDetected(e -> {
        for(Block ev : set) {
        	ev.obj.startFullDrag();
        }});
        sc.setOnKeyPressed(e ->{
        	if(e.getCode().equals(KeyCode.ENTER)) {
        		map = new Map(set);
        		route = this.solve(map);
        		for(Coordinate cord : route) {
        		System.out.print("("+cord.getX()+ ", " + cord.getY() + ") -> ");
        		for(Block each : set) {
        			if(each.obj.getX() == cord.getX()*20 && each.obj.getY() == cord.getY()*20) {
        				each.status = 4;
        			}
        		}
        	}
        	}
        	else if(e.getCode().equals(KeyCode.SHIFT)) {
        		for(Block each : set) {
        			each.status = 0;
        		}
        	}
        });
        for(Block e : set) {
        	root.getChildren().add(e.obj);}
        TimerTask clrCg = new TimerTask() {
        	public void run() {
                	ColorChange();
                	Platform.runLater(() -> {
                	
                	for(Block e : set) {
                		switch(e.status) {
                			case 0:
                				e.obj.setFill(Color.BLACK);
                				break;
                			case 1:
                				e.obj.setFill(Color.rgb(r,g,b));
                				break;
                			case 2:
                				e.obj.setFill(Color.GREEN);
                				break;
                			case 3:
                				e.obj.setFill(Color.RED);
                				break;
                			case 4:
                				e.obj.setFill(Color.PURPLE);
                				break;
                		}
                			
                		
                	}});
                
        	}
        };
        timer.schedule(clrCg, 15l, 15l);
        arg0.setScene(sc);
        arg0.getIcons().add(new Image(Main.class.getResourceAsStream("./Icons/KamalaHarris.jpg")));
        arg0.show();

    }
    private int[][] DIRECTIONS 
	  = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    
    private List<Coordinate> backtrackPath(
    		  Coordinate cur) {
    		    List<Coordinate> path = new ArrayList<>();
    		    Coordinate iter = cur;

    		    while (iter != null) {
    		        path.add(iter);
    		        iter = iter.parent;
    		    }

    		    return path;
    		}
    public List<Coordinate> solve(Map maze) {
        LinkedList<Coordinate> nextToVisit 
          = new LinkedList<>();
        Coordinate start = maze.getEntry();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Coordinate cur = nextToVisit.remove();

            if (!maze.isValidLocation(cur.getX(), cur.getY()) 
              || maze.isExplored(cur.getX(), cur.getY())
            ) {
                continue;
            }

            if (maze.isWall(cur.getX(), cur.getY())) {
                maze.setVisited(cur.getX(), cur.getY());
                continue;
            }

            if (maze.isExit(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
            	if(!((cur.getX() + direction[0] == 40 || cur.getX() + direction[0] == -1) || (cur.getY() + direction[1] == 40 || cur.getY() + direction[1] == -1))) {
                Coordinate coordinate 
                  = new Coordinate(
                    cur.getX() + direction[0], 
                    cur.getY() + direction[1]
                  );
                coordinate.parent = cur;
                nextToVisit.add(coordinate);
                maze.setVisited(cur.getX(), cur.getY());
            }}
        }
        return Collections.emptyList();
    }
	}