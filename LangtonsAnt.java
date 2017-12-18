package javaFX;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LangtonsAnt extends Application {
	
	Pane root = new Pane();
	
	//the background two-dim array
	private BackGround[][] bg = new BackGround[80][80];
	
	private int XPos = 350;
	private int YPos = 350;
	
	private String white = "0xffffffff";
	private String black = "0x000000ff";
	
	private double time = 0.00009;
	
	private int Steps = 0;
	
	private Text text;
	
	private Timeline goUp;
	private Timeline goRight;
	private Timeline goDown;
	private Timeline goLeft;
	
	private void allStop() {
		try {
			goUp.stop();
			goRight.stop();
			goDown.stop();
			goLeft.stop();
		}
		catch(Exception e) {}
	}
	
	private boolean startStatus = false;
	
	private class Ant extends StackPane {
		public Ant() {
			Rectangle rect = new Rectangle(10, 10);
			rect.setFill(Color.BLUE);
			getChildren().add(rect);
		}
	}
	
	//creating ant object
	private Ant ant = new Ant();
	
	//ant movement
	private void move() {
		ant.setLayoutX(350);
		ant.setLayoutY(350);
		
		root.getChildren().add(ant);
		
		Text text = new Text("Steps: " + Integer.toString(moves));
		text.setLayoutX(30);
		text.setLayoutY(40);
		root.getChildren().add(text);

		goUp = new Timeline(
	        	new KeyFrame(Duration.seconds(time), event -> {
	        		changeColor();
	        		
		        	ant.setLayoutY(YPos - 10);
		        	YPos -= 10;
		        	
		        	moves++;
		        	text.setText("Steps: " + Integer.toString(moves));
		        	
		        	if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(white)) {
		    			allStop();
		    		    goRight.play();
		    		}
		    		else if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(black)) {
		    			allStop();
		    		    	goLeft.play();
		    		}
			})
		);
		goUp.setAutoReverse(true);
		goUp.setCycleCount(1);
		
		goRight = new Timeline(
		        new KeyFrame(Duration.seconds(time), event -> {
		        	changeColor();
		        		
			        ant.setLayoutX(XPos + 10);
			        XPos += 10;
			        	
			        moves++;
			        text.setText("Steps: " + Integer.toString(moves));
			        	
			        if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(white)) {
			    		allStop();
			    		goDown.play();
			    	}
			    	else if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(black)) {
			    		allStop();
			    		goUp.play();
			    	}
			    })
			);
		goRight.setAutoReverse(true);
		goRight.setCycleCount(1);
			
		goDown = new Timeline(
			new KeyFrame(Duration.seconds(time), event -> {
		    		changeColor();
		        		
		        	ant.setLayoutY(YPos + 10);
		        	YPos += 10;
			        	
		        	moves++;
		        	text.setText("Steps: " + Integer.toString(moves));
			        	
		        	if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(white)) {
		    			allStop();
		    		    goLeft.play();
		    		}
		    		else if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(black)) {
		   			allStop();
		    		    	goRight.play();
		    		}
		    	})
		);
		goDown.setAutoReverse(true);
		goDown.setCycleCount(1);
			
		goLeft = new Timeline(
			new KeyFrame(Duration.seconds(time), event -> {
	        		changeColor();
			        		
		       		ant.setLayoutX(XPos - 10);
		        	XPos -= 10;
				        	
		        	moves++;
		        	text.setText("Steps: " + Integer.toString(moves));
				        	
		        	if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(white)) {
		    			allStop();
		    		    goUp.play();
		    		}
		    		else if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(black)) {
			    		allStop();
			    	    	goDown.play();
				}
		    	})
		);
		goDown.setAutoReverse(true);
		goDown.setCycleCount(1);
	}

	private Parent createContent() {
		root.setPrefSize(700, 700);
		
		for (int i = 0; i < 80; i++) {
			for (int j = 0; j < 80; j++) {
				BackGround block = new BackGround();
				
				block.setLayoutX(i * 10);
				block.setLayoutY(j * 10);
				
				root.getChildren().add(block);
				
				bg[i][j] = block;
			}
		}
		
		text = new Text("Press space to start simulation");
		text.setFont(Font.font(18));
		text.setLayoutY(200);
		text.setLayoutX(223);
		root.getChildren().add(text);
		
		move();
		
		return root;
	}
	
	private class BackGround extends StackPane {
		Rectangle rect = new Rectangle(10, 10);
		public BackGround() {
			rect.setFill(Color.WHITE);
			getChildren().add(rect);
		}
	}
	
	public void changeColor() {
		if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(white)) {
     		bg[XPos / 10][YPos / 10].rect.setFill(Color.BLACK);
        	}
        	else if (bg[XPos / 10][YPos / 10].rect.getFill().toString().equals(black)) {
        		bg[XPos / 10][YPos / 10].rect.setFill(Color.WHITE);
        	}
	}

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		
		scene.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.SPACE)) {
				root.getChildren().remove(text);
				if (!startStatus)
					goUp.play();
					startStatus = true;
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("Langton's ant simulation");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
