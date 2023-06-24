package aliyamanii.javafxcandycrush;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {
    public int screenWidth = 800;
    public int screenHeight = 600;
    private VBox groot; //I   Groot
    public GridPane gameBoard = new GridPane();
    private int hearts = 2;
    private final Heart[] playerHearts = new Heart[3];
    public GridPane heartBox = new GridPane();
    String playerName;
    public Score playerScore = new Score();
    private int gridWidth = 6;
    private int gridHeight = 6;
    private Node selectedNode;
    private final Stage gameStage;
    int depth = 1; // Points multiplier for combos SUPER SUGAR CRUSH
    boolean rainbowBlasted = false;

    public Game(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public void handle() {
        heartBox.setHgap(10);
        for (int i = 0; i < playerHearts.length; i++) {
            Heart heart = new Heart(Heart.HeartType.FULL);
            playerHearts[i] = heart;
            heartBox.add(heart, i, 0);
        }


        heartBox.setAlignment(Pos.TOP_RIGHT);
        HBox lifeScore = new HBox(playerScore, heartBox);
        lifeScore.setAlignment(Pos.TOP_CENTER);
        lifeScore.setSpacing(400);
        playerScore.setTextAlignment(TextAlignment.LEFT);
        groot = new VBox(lifeScore, gameBoard);
        groot.setSpacing(100);

        // Create game board grid
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);

        // Add candy objects to game board
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Candy candy = new Candy();
                gameBoard.add(candy, i, j);
                candy.setOnMousePressed(e -> {
                    selectedNode = candy;
                });
                candy.setOnMouseEntered(e -> {
                    handleClick(candy);
                });

            }
        }


        Scene gameScene = new Scene(groot, screenWidth, screenHeight);
        gameStage.setResizable(false);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(GameMenu.path.toAbsolutePath() + "\\assets\\bgImage.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        groot.setBackground(new Background(backgroundImage));

        lifeScore.setTranslateX(5);
        lifeScore.setTranslateY(5);

        // Set game board scene on gameStage
        gameStage.setScene(gameScene);
        gameStage.setTitle("Candy Crush");
        findMatches(gameBoard);
        gameStage.show();


    }

    //TODO read settings from file
    private void readChoicesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("game_settings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Player Name: ")) {
                } else if (line.startsWith("Selected Size: ")) {
                    int size = Integer.parseInt(line.substring("Selected Size: ".length()));
                    gridWidth = size;
                    gridHeight = size;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClick(Candy label) {
        if (label == selectedNode)
            return;
        // If no node is selected, select the clicked node
        if (selectedNode != null) {
            // If clicked node is adjacent to the selected node, swap them
            int selectedNodeRow = GridPane.getRowIndex(selectedNode);
            int selectedNodeCol = GridPane.getColumnIndex(selectedNode);
            int clickedNodeRow = GridPane.getRowIndex(label);
            int clickedNodeCol = GridPane.getColumnIndex(label);

            //Check if the selected nodes are adjacent
            if ((Math.abs(selectedNodeRow - clickedNodeRow) == 1 && selectedNodeCol == clickedNodeCol)
                    || (Math.abs(selectedNodeCol - clickedNodeCol) == 1 && selectedNodeRow == clickedNodeRow)) {
                swapCandy(selectedNode, label);

                ArrayList<Candy> matches = findMatches(gameBoard);
                if (matches.size() < 3) {
                    if (rainbowBlasted) {
                        giveHeart();
                        rainbowBlasted = false;

                    } else {
                        takeHeart();
                    }
                } else if (matches.size() >= 4) {
                    giveHeart();
                }
                System.out.println(matches.toString());
            }
            selectedNode = null;
        }
    }

    private void swapCandy(Node node1, Node node2) {
        depth = 0;
        GridPane gridPane = (GridPane) node1.getParent();
        int node1Row = GridPane.getRowIndex(node1);
        int node1Col = GridPane.getColumnIndex(node1);
        int node2Row = GridPane.getRowIndex(node2);
        int node2Col = GridPane.getColumnIndex(node2);

        gridPane.getChildren().removeAll(node1, node2);
        gridPane.add(node1, node2Col, node2Row);
        gridPane.add(node2, node1Col, node1Row);
        fadeIn(node1);
        fadeIn(node2);

    }

    public void fadeIn(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700), node);

        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public ArrayList<Candy> findMatches(GridPane grid) {
        ArrayList<Candy> matches = new ArrayList<>();

        // Check for horizontal matches
        for (int row = 0; row < gridWidth; row++) {
            Candy.CandyType latestType = null;
            int count = 0;
            int col;
            for (col = 0; col < gridHeight; col++) {
                Candy.CandyType type = getCandyType(grid, row, col);
                System.out.print(type + " ");
                if (type == latestType) {
                    count++;
                } else {
//                    System.out.print(count + " ");
                    if (count > 2) {
                        if (count == 5) {
                            rainbowBlast(latestType);
                        }
                        for (int i = col; i > col - count; i--) {
                            matches.add(getCandyObject(gameBoard, row, i - 1));
                        }
                    }
                    count = 1;
                    latestType = type;
                }
            }
            if (count == 5) {
                rainbowBlast(latestType);
            } else if (count > 2) {
                for (int i = col; i > col - count; i--) {
                    matches.add(getCandyObject(gameBoard, row, i - 1));
                }
            }
            System.out.println();
        }
        System.out.println();

        // Check for vertical matches
        for (int col = 0; col < gridWidth; col++) {
            Candy.CandyType latestType = null;
            int count = 0;
            int row;
            for (row = 0; row < gridHeight; row++) {
                Candy.CandyType type = getCandyType(grid, row, col);
                if (type == latestType) {
                    count++;
                } else {
                    System.out.print(count + " ");
                    if (count > 2) {
                        if (count == 5) {
                            rainbowBlast(latestType);
                        }
                        for (int i = row; i > row - count; i--) {
                            matches.add(getCandyObject(gameBoard, i - 1, col));
                        }
                    }
                    count = 1;
                    latestType = type;
                }
            }
            if (count == 5) {
                rainbowBlast(latestType);
            } else if (count > 2) {
                for (int i = row; i > row - count; i--) {
                    matches.add(getCandyObject(gameBoard, i - 1, col));
                }
            }
        }


        if (matches.size() >= 3) {
            randomizeMatches((ArrayList<Candy>) matches.stream()
                    .distinct()
                    .collect(Collectors.toList()));
        }
        return matches;
    }

    private Candy getCandyObject(GridPane board, int row, int col) {
        for (Node node : board.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Candy) node;
            }
        }
        return new Candy();
    }

    private Candy.CandyType getCandyType(GridPane grid, int row, int col) {
//        if(row)
        return getCandyObject(grid, row, col).type;
    }

    public void rainbowBlast(Candy.CandyType type) {
        rainbowBlasted = true;
        ArrayList<Candy> candice = new ArrayList<>();
        for (int row = 0; row < gridWidth; row++) {
            for (int col = 0; col < gridHeight; col++) {
                Candy candy = getCandyObject(gameBoard, row, col);
                if (candy.type == type) {
                    candice.add(candy);
                }
            }
        }
        randomizeMatches(candice);
    }

    public void stuckBoard(GridPane grid) {
        ArrayList<Candy> matches = new ArrayList<>();
        //Check for possible horizontal matches
        for (int row = 0; row < gridWidth; row++) {
            Candy.CandyType latestType = null;
            int count = 0;
            int col;
            for (col = 0; col < gridHeight; col++) {
                Candy.CandyType type = getCandyType(grid, row, col);
                System.out.print(type + " ");
                if (type == latestType) {
                    count++;
                } else {
                    if (count == 2) {
                        Candy.CandyType nextType1 = getCandyType(grid, row, col + 2);
                        Candy.CandyType nextType2 = getCandyType(grid, row - 1, col + 1);
                        Candy.CandyType nextType3 = getCandyType(grid, row + 1, col + 1);

                        if (nextType1 == latestType || nextType2 == latestType || nextType3 == latestType)
                            continue;
                        else {
                            for (int i = 0; i < grid.getRowCount(); i++) {
                                for (int j = 0; j < grid.getColumnCount(); j++) {
                                    matches.add(getCandyObject(gameBoard, i, j));
                                    randomizeMatches(matches);
                                }
                            }
                        }
                    }
                    count = 1;
                    latestType = type;
                }
            }
        }
        //Check for possible vertical matches
        for (int col = 0; col < gridWidth; col++) {
            Candy.CandyType latestType = null;
            int count = 0;
            int row;
            for (row = 0; row < gridHeight; row++) {
                Candy.CandyType type = getCandyType(grid, row, col);
                if (type == latestType) {
                    count++;
                } else {
                    if (count == 2) {
                        Candy.CandyType nextType1 = getCandyType(grid, row+2, col);
                        Candy.CandyType nextType2 = getCandyType(grid, row + 1, col - 1);
                        Candy.CandyType nextType3 = getCandyType(grid, row + 1, col + 1);

                        if (nextType1 == latestType || nextType2 == latestType || nextType3 == latestType)
                            continue;
                        else {
                            for (int i = 0; i < grid.getRowCount(); i++) {
                                for (int j = 0; j < grid.getColumnCount(); j++) {
                                    matches.add(getCandyObject(gameBoard, i, j));
                                    randomizeMatches(matches);
                                }
                            }
                        }
                    }
                    count = 1;
                    latestType = type;
                }
            }

        }
    }

    public void randomizeMatches(ArrayList<Candy> matches) {
        depth++;
        System.out.printf("\n###depth = %s####\n", depth);
//        gameBoard.getChildren().removeAll(matches);
        for (Candy node : matches) {
            int col, row;
            try {

                row = GridPane.getRowIndex(node);
                col = GridPane.getColumnIndex(node);
                System.out.println(row + ' ' + col);
            } catch (Exception e) {
                System.out.println("error");
                continue;
            }
            playerScore.increaseScore(depth);

            gameBoard.getChildren().removeAll(node);
            Candy newCandy = new Candy();
            newCandy.setOnMousePressed(e -> {
                selectedNode = newCandy;
            });
            newCandy.setOnMouseEntered(e -> {
                handleClick(newCandy);
            });
            gameBoard.add(newCandy, col, row);
            fadeIn(newCandy);
        }
        delay(500, () -> findMatches(gameBoard));
        Candy.CandyType[] types = Candy.CandyType.values();
        Candy.CandyType newType = types[(int) (Math.random() * types.length)];
    }

    public void takeHeart() {
        if (hearts >= 0) {
            playerHearts[hearts].setType(Heart.HeartType.EMPTY);
            hearts--;
        }
        if (hearts == -1) {
            GameOver overHandler = new GameOver(gameStage);
            overHandler.handle();
        }
    }

    public void giveHeart() {
        if (hearts < 2) {
            hearts++;
            playerHearts[hearts].setType(Heart.HeartType.FULL);
        }
    }

    public void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}
