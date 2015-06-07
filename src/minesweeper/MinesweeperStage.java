package minesweeper;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.event.*;
import java.util.Random;

public class MinesweeperStage extends Application{
    @Override
    public void start(Stage sweeperStage){
        //Setting up outer pane to hold all of the components
        BorderPane outerBox = new BorderPane();

        //mineField holds the 10x10 grid area of the game w/ mines
        GridPane mineField = new GridPane();
        mineField.setAlignment(Pos.CENTER);
        Button[][] boardButtons = new Button[10][10];
  
        //The grid pane that holds the top info bar
        HBox topBox = new HBox(35);
        generateTop(topBox);
            
        //Populating the outerBox, the main window
        outerBox.setTop(topBox);
        outerBox.setCenter(mineField);
        
        //Function to populate the mine field
        populateMineField(mineField, boardButtons);
        
        //Event handler
        for(int i = 0; i<10;i++){
            for(int j = 0; j<10; j++){
                boardButtons[i][j].setOnAction((e)-> {
                    Button temp = (Button)e.getSource();
                    mineClick(temp);
                });
            }
        }
        //Displaying the stage
        executeStage(outerBox, sweeperStage);
    }
    
    //Function to opulate the mine field with buttons
    protected void populateMineField(GridPane board, Button [][] boardButtons){
        int count = 0;
        Random rand = new Random();
        
        //Populating the mineField with either a mine or a free node
        for(int i = 0; i<10;i++){
            for(int j = 0; j<10; j++){
                if(count == 10 || rand.nextInt(10) < 9){
                    boardButtons[i][j] = new FreeNode();
                }
                else{
                    boardButtons[i][j] = new MineNode();
                    count++;
                    }
                
                //Setting the style for the newly placed buttons
                boardButtons[i][j].setStyle("-fx-background-radius: 0;"
                        + "-fx-border-color:gray;" 
                        + "-fx-background-color:gainsboro;");
                board.add(boardButtons[i][j], j, i);
            }
        }
        //If there aren't 10 mines in the field, recreate
        if(count < 10){
            populateMineField(board, boardButtons);
        }
    }
    
    public void mineClick(Button nodeClicked){
        if(nodeClicked instanceof MineNode) {
        nodeClicked.setStyle("-fx-background-color:black;" +
                    "-fx-background-radius: 0;"
                    + "-fx-border-color:gray;");
        } 
        else {
            nodeClicked.setStyle("-fx-background-color:blue;" +
                    "-fx-background-radius: 0;"
                    + "-fx-border-color:gray;");
        }
    }
    
    public void generateTop(HBox topBox){
        //Setting up nodes for the top pane (smiley, timer, minesLeft
        Image smiley = new Image("smiley.png");
        ImageView smileyPane = new ImageView(smiley);
        Rectangle timer = new Rectangle(0,0,35,35);
        timer.setFill(Color.BLACK);
        Rectangle minesLeft = new Rectangle(0,0,35,35);
        minesLeft.setFill(Color.BLACK);
        
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(timer, smileyPane, minesLeft);
    }
    
    public void executeStage(BorderPane outerBox, Stage sweeperStage){
        Scene scene = new Scene(outerBox);
        sweeperStage.setTitle("My Sweeper Clone");
        sweeperStage.setScene(scene);
        sweeperStage.setResizable(false);
        sweeperStage.show();
    }
    
    public static void main(String[] args) {
           Application.launch(args);
    }
    
    class MineNode extends Button{
        
    }
    
    class FreeNode extends Button{
        
    }
}
