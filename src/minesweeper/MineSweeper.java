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
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class MineSweeper extends Application{
    
    public static int countFreeNodes = 0;
    
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
                    Button nodeClicked = (Button)e.getSource();
                    int surroundingMines = countMines(boardButtons, nodeClicked);
                    mineClick(nodeClicked, mineField, surroundingMines);
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
    
    public void mineClick(Button nodeClicked, GridPane board, int surrounding){
        if(nodeClicked instanceof MineNode) {
            nodeClicked.setStyle("-fx-background-color:black;" +
                    "-fx-background-radius: 0;"
                    + "-fx-border-color:gray;");
            System.out.println("Game over - Mine Pressed");
            System.exit(0);
        } 
        else {
            nodeClicked.setStyle("-fx-background-color:lightblue;" +
                    "-fx-background-radius: 0;"
                    + "-fx-border-color:gray;");
            addSurroundingMines(board, nodeClicked, surrounding);
            countFreeNodes++;
            didUserWin(countFreeNodes);
        }
    }
    
    public void addSurroundingMines(GridPane board, Button nodeClicked, int surrounding){
        Label numOfMines = new Label(" " + ((Integer)surrounding).toString());
        numOfMines.setFont(Font.font("SansSerif", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        board.add(numOfMines, GridPane.getColumnIndex(nodeClicked), GridPane.getRowIndex(nodeClicked));
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
    
    public int countMines(Button [][] buttons, Button buPressed){
        int rowNum = GridPane.getRowIndex(buPressed);
        int colNum = GridPane.getColumnIndex(buPressed);
        int countMines = 0;
        
        if(rowNum -1 > -1 && colNum -1 > -1){
            if(buttons[rowNum-1][colNum-1] instanceof MineNode){
                countMines++;
            }
        }
        if(rowNum -1 > -1){
             if(buttons[rowNum-1][colNum] instanceof MineNode){
                countMines++;
            }
        }
        if(colNum -1 > -1){
            if(buttons[rowNum][colNum-1] instanceof MineNode){
                countMines++;
            }
        }
        if(rowNum +1 < 10 && colNum-1 > -1){
            if(buttons[rowNum+1][colNum-1] instanceof MineNode){
                countMines++;
            }
        }
        if(rowNum +1 < 10){
            if(buttons[rowNum+1][colNum] instanceof MineNode){
                countMines++;
            }
        }
        
        if((colNum +1 < 10) && (rowNum -1 > -1)){
            if(buttons[rowNum -1][colNum+1] instanceof MineNode){
                countMines++;
            }
        }
        
        if(colNum +1 < 10){
            if(buttons[rowNum][colNum+1] instanceof MineNode){
                countMines++;
            }
        }
        
        if(colNum +1 < 10 && rowNum +1 < 10){
            if(buttons[rowNum+1][colNum+1] instanceof MineNode){
                countMines++;
            }
        }

        return countMines;
    }
    
    public static void didUserWin(int totalUncoveredNodes){
        if(totalUncoveredNodes ==90){
            System.out.println("User won!");
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
           Application.launch(args);
    }
    
    class MineNode extends Button{
        
    }
    
    class FreeNode extends Button{
        
    }
}
