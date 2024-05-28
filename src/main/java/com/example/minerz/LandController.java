package com.example.minerz;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class LandController {
    int finalRow;
    int finalCol;
    private static final double TILE_MARGIN = 2.0;
    private static final double VERTICAL_MARGIN = 20.0;
    private static final double HORIZONTAL_MARGIN = 40.0;
    public TextField txtRows;
    public TextField txtCols;
    public Button btnCreateLand;
    public BorderPane borderPane;
    public GridPane gridPane;
    public VBox vboxBottom;
    public ScrollPane scrollPane;
    int rowBef;
    int colBef;

    int rowBench;
    int colBench;

    int partitionIndex = 0;

    GridPane thirdGridPane;
    GridPane secondTable;

    Map<String, Integer> partitionMap = new HashMap<>();
    int[][] secondTableArray;
    int[][] thirdTableArray;


    Color lighGreenBench = Color.rgb(144, 238, 144, 0.5);
    Color lighterBrownGreen = Color.rgb(204, 204, 0);
    Color lightBrownGreen = Color.rgb(173, 143, 96);
    Color brownGreen = Color.rgb(102, 102, 0);
    Color green = Color.rgb(0, 128, 0);
    Color darkBrownGreen = Color.rgb(51, 51, 0);
    Color darkerBrownGreen = Color.rgb(102, 51, 0);
    Color evenDarkerBrownGreen = Color.rgb(51, 25, 0);
    Color lightOrange = Color.rgb(255, 204, 153);
    Color orange = Color.rgb(255, 128, 0);


    public void initialize() {
        gridPane = new GridPane();
        gridPane.setHgap(TILE_MARGIN);
        gridPane.setVgap(TILE_MARGIN);

        vboxBottom = new VBox();
        vboxBottom.prefWidthProperty().bind(borderPane.widthProperty());
        vboxBottom.prefHeightProperty().bind(borderPane.heightProperty().divide(2));
        vboxBottom.setPadding(new Insets(VERTICAL_MARGIN, HORIZONTAL_MARGIN, VERTICAL_MARGIN, HORIZONTAL_MARGIN));

        scrollPane = new ScrollPane();
        scrollPane.setContent(vboxBottom);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        borderPane.setCenter(scrollPane);

        partitionMap.put("LIGHTESTBROWNGREEN", 0);
        partitionMap.put("LIGHTBROWNGREEN", 1);
        partitionMap.put("BROWNGREEN", 2);
        partitionMap.put("GREEN", 3);
        partitionMap.put("DARKBROWNGREEN", 4);
        partitionMap.put("DARKERBROWNGREEN", 5);
        partitionMap.put("DARKESTBROWNGREEN", 6);
    }

    public void createLand(ActionEvent actionEvent) {
        secondTable = new GridPane();
        thirdGridPane = new GridPane();
        int rows;
        int cols;
        try {
            rows = Integer.parseInt(txtRows.getText());
            cols = Integer.parseInt(txtCols.getText());

            if (rows == rowBef && cols == colBef) {
                showAlert("No Change", "The number of rows and columns is the same as before.");
                return;
            }

            rowBef = rows;
            colBef = cols;
            rowBench = rows;
            colBench = cols;

        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter valid numbers for rows and columns.");
            return;
        }

        gridPane.getChildren().clear();

        double availableWidth = vboxBottom.getWidth() - (cols + 1) * TILE_MARGIN - 2 * HORIZONTAL_MARGIN;
        double availableHeight = vboxBottom.getHeight() - (rows + 1) * TILE_MARGIN - 2 * VERTICAL_MARGIN;

        double tileWidth = availableWidth / cols;
        double tileHeight = availableHeight / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle tile = new Rectangle(tileWidth, tileHeight);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);
                tile.setOnMouseClicked(e -> changeTileColor(tile));
                gridPane.add(tile, j, i);
            }
        }

        vboxBottom.getChildren().clear();
        vboxBottom.getChildren().add(gridPane);
        drillData();
        secondTable = createSecondTable(rows, cols);
        lerchGrossMan(rows, cols);

        Scene scene = borderPane.getScene();
        if (scene != null) {
            Stage stage = (Stage) scene.getWindow();
            if (stage != null) {
                stage.setMaximized(true);
            }
        }
    }

    private void changeTileColor(Rectangle tile) {
        tile.setFill(Color.BLACK);
    }

    private void drillData() {


        if (gridPane == null || gridPane.getChildren().isEmpty()) return;

        int rows;
        int cols;
        int partition;
        int remaining;
        int[] partitionRows = new int[7];

        try {
            rows = Integer.parseInt(txtRows.getText());
            cols = Integer.parseInt(txtCols.getText());
        } catch (NumberFormatException e) {
            return;
        }

        partition = (int) Math.floor(rows / 7.0);
        System.out.println("remaining partition: " + partition);
        remaining = rows - (partition * 7);
        System.out.println("remaining rows with no partition:" + remaining);

        partitionRows[partitionMap.get("LIGHTESTBROWNGREEN")] = partition;
        partitionRows[partitionMap.get("LIGHTBROWNGREEN")] = partition;
        partitionRows[partitionMap.get("BROWNGREEN")] = partition;
        partitionRows[partitionMap.get("GREEN")] = partition;
        partitionRows[partitionMap.get("DARKBROWNGREEN")] = partition;
        partitionRows[partitionMap.get("DARKERBROWNGREEN")] = partition;
        partitionRows[partitionMap.get("DARKESTBROWNGREEN")] = partition;

//        System.out.println("BG partition: " + partitionRows[0]);
//        System.out.println("LBG partition: " + partitionRows[1]);
//        System.out.println("LLBG partition: "+ partitionRows[2]);
//        System.out.println("G partition: " + partitionRows[3]);
//        System.out.println("DG partition: " + partitionRows[4]);
//        System.out.println("DDG partition: " + partitionRows[5]);
//        System.out.println("DDDG partition: " + partitionRows[6]);


        while (remaining != 0) {
            if (partitionIndex == 6) partitionIndex = 0;
            partitionRows[partitionIndex] = partitionRows[partitionIndex] + 1;
            partitionIndex++;
            remaining--;
        }

//        System.out.println("After redistributing");
//        System.out.println("BG partition: " + partitionRows[0]);
//        System.out.println("LBG partition: " + partitionRows[1]);
//        System.out.println("LLBG partition: "+ partitionRows[2]);
//        System.out.println("G partition: " + partitionRows[3]);
//        System.out.println("DG partition: " + partitionRows[4]);
//        System.out.println("DDG partition: " + partitionRows[5]);
//        System.out.println("DDDG partition: " + partitionRows[6]);


        int rowIndex = 0;
        while (partitionRows[partitionMap.get("LIGHTESTBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("2");
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(lighterBrownGreen);
                tile.setAccessibleText("2");
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("LIGHTESTBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("LIGHTBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("1");
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(lightBrownGreen);
                tile.setAccessibleText("1");
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("LIGHTBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("BROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("0");
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(brownGreen);
                tile.setAccessibleText("0");
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("BROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("GREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("-1");
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(green);
                tile.setAccessibleText("-1");
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("GREEN")]--;
        }

        while (partitionRows[partitionMap.get("DARKBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("-2");
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(darkBrownGreen);
                tile.setAccessibleText("-2");
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("DARKBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("DARKERBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("-3");
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(darkerBrownGreen);
                tile.setAccessibleText("-3");
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("DARKERBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("DARKESTBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("-4");
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(evenDarkerBrownGreen);
                tile.setAccessibleText("-4");
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("DARKESTBROWNGREEN")]--;
        }


    }


    public GridPane createSecondTable(int rows, int cols) {
        GridPane secondGridPane = new GridPane();
        secondGridPane.setHgap(TILE_MARGIN);
        secondGridPane.setVgap(TILE_MARGIN);

        double availableWidth = vboxBottom.getWidth() - (cols + 1) * TILE_MARGIN - 2 * HORIZONTAL_MARGIN;
        double availableHeight = vboxBottom.getHeight() - (rows + 1) * TILE_MARGIN - 2 * VERTICAL_MARGIN;

        double tileWidth = availableWidth / cols;
        double tileHeight = availableHeight / rows;

        for (int j = 0; j < cols; j++) {
            int cumulativeSum = 0;
            for (int i = 0; i < rows; i++) {
                Rectangle originalTile = (Rectangle) gridPane.getChildren().get(j + (i * cols));
                int value = Integer.parseInt(originalTile.getAccessibleText());

                cumulativeSum += value;
                Rectangle tile = new Rectangle(tileWidth, tileHeight);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);
                tile.setOnMouseClicked(e -> changeTileColor(tile));
                tile.setAccessibleText(String.valueOf(cumulativeSum));

                Text text = new Text(String.valueOf(cumulativeSum));
                text.setFont(Font.font("Arial", 12));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);

                secondGridPane.add(tile, j, i);
                secondGridPane.add(text, j, i);
            }
        }


        Label secondTableLabel = new Label("Second Table");
        secondTableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        secondTableLabel.setPadding(new Insets(10, 0, 10, 0));
        vboxBottom.getChildren().addAll(secondTableLabel, secondGridPane);
        return secondGridPane;

    }

    public void lerchGrossMan(int rows, int cols) {
        int rowArrays = rows + 1;
        int colArrays = cols + 1;
        secondTableArray = new int[rowArrays][colArrays];
        thirdTableArray = new int[rowArrays][colArrays];

        int rowOffset = 1;
        int colOffset = 1;
        // row
        for (int i = 0; i < colArrays; i++) {
            secondTableArray[0][i] = 0;
        }

        // col
        for (int j = 0; j < rowArrays; j++) {
            for (Node node : secondTable.getChildren()) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);

                // for first col
                if (col == 0 && row == j) {
                    if (node instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) node;
                        int val = Integer.parseInt(rectangle.getAccessibleText());
                        secondTableArray[j + 1][0] = val;
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        int val = Integer.parseInt(text.getText());
                        secondTableArray[j + 1][0] = val;
                    }

                }

            }
        }

        // the orig second table's value
        for (Node node : secondTable.getChildren()) {
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);

            if (node instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) node;
                int val = Integer.parseInt(rectangle.getAccessibleText());
                secondTableArray[row + rowOffset][col + colOffset] = val;
            } else if (node instanceof Text) {
                Text text = (Text) node;
                int val = Integer.parseInt(text.getText());
                secondTableArray[row + rowOffset][col + colOffset] = val;
            }

            finalRow = row;
            finalCol = col;
        }

        // System.out.println("The final row and col: " + finalRow + " " + finalCol + "the final value which should -52 with 55x60 grid: " + secondTableArray[finalRow][finalCol]);
        // sakto najud
        System.out.println("Second table: ");
        for (int i = 0; i < rowArrays; i++) {
            for (int j = 0; j < colArrays; j++) {
                System.out.print(secondTableArray[i][j] + " ");
            }
            System.out.println();
        }

        //now do currValue + the max 3 of it's left for and store it to the array for the thirdtable
        for (int i = 0; i < rowArrays; i++) {
            thirdTableArray[i][0] = secondTableArray[i][0];
        }

        // Populate the rest of the thirdTableArray
        for (int j = 1; j < colArrays; j++) {
            for (int i = 0; i < rowArrays; i++) {
                // For the first row, find max between left and down-left from previous column
                if (i == 0) {
                    thirdTableArray[i][j] = getMax(0, thirdTableArray[i][j - 1], i + 1 < rowArrays ? thirdTableArray[i + 1][j - 1] : 0);
                }
                // For the last row, find max between left-up and left from previous column
                else if (i == rows) {
                    thirdTableArray[i][j] = secondTableArray[i][j] + getMax(0, thirdTableArray[i - 1][j - 1], thirdTableArray[i][j - 1]);
                }
                // For all other rows, find max between left-up, left, and left-down from previous column
                else {
                    int leftUp = (i - 1 >= 0) ? thirdTableArray[i - 1][j - 1] : 0;
                    int left = thirdTableArray[i][j - 1];
                    int leftDown = (i + 1 < rowArrays) ? thirdTableArray[i + 1][j - 1] : 0;
                    thirdTableArray[i][j] = secondTableArray[i][j] + getMax(leftUp, left, leftDown);
                }
            }
        }

        // Print thirdTableArray for verification
        System.out.println("Third table:");
        for (int i = 0; i < rowArrays; i++) {
            for (int j = 0; j < colArrays; j++) {
                System.out.print(thirdTableArray[i][j] + " ");
            }
            System.out.println();
        }

        thirdGridPane.setHgap(TILE_MARGIN);
        thirdGridPane.setVgap(TILE_MARGIN);
        double availableWidth = vboxBottom.getWidth() - (cols + 1) * TILE_MARGIN - 2 * HORIZONTAL_MARGIN;
        double availableHeight = vboxBottom.getHeight() - (rows + 1) * TILE_MARGIN - 2 * VERTICAL_MARGIN;
        double tileWidth = availableWidth / cols;
        double tileHeight = availableHeight / rows;

        for (int j = 1; j < colArrays; j++) {
            for (int i = 1; i < rowArrays; i++) {
                    Rectangle tile = new Rectangle(tileWidth, tileHeight);
                    tile.setFill(Color.TRANSPARENT);
                    tile.setStroke(Color.BLACK);
                    tile.setOnMouseClicked(e -> changeTileColor(tile));

                    int val = thirdTableArray[i][j];
                    Text text = new Text(String.valueOf(val));
                    text.setFont(Font.font("Arial", 12));
                    text.setFill(Color.BLACK);

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().addAll(text, tile);

                    thirdGridPane.add(stackPane, j, i);

            }
        }

        Label thirdTableLabel = new Label("Third Table");
        thirdTableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        thirdTableLabel.setPadding(new Insets(10, 0, 10, 0));

        vboxBottom.getChildren().addAll(thirdTableLabel, thirdGridPane);

    }


    int getMax(int a, int b, int c) {
        int max;
        if (a > b && a > c) {
            max = a;
        } else if (b > a && b > c) {
            max = b;
        } else max = c;

        return max;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearLand(ActionEvent actionEvent) {
        vboxBottom.getChildren().clear();
    }

    public void minePit(ActionEvent actionEvent) {

        for(int j = colBench; j >= 1; j--){
            int max = Integer.MIN_VALUE;
            int rowMinePit = -1;
            int tileIndex;
            for (int i = 1; i <= rowBench; i++) {
                if (thirdTableArray[i][j] > max) {
                    max = thirdTableArray[i][j];
                    rowMinePit = i-1;
                }

                if (thirdTableArray[i][j] == max) {
                    max = thirdTableArray[i][j];
                    rowMinePit = i-1;
                }
            }


            System.out.println("Max value: " + max);
            System.out.println("Index of max value (rowMinePit): " + rowMinePit);
            System.out.println("Total columns (j or currentColBench): " + j);

            if (rowMinePit != -1) {
                int totalIndex = j * rowBench;
                System.out.println("total index " + totalIndex);
                // to get index of gridpane instead of i * currRow + currColumn this is for gridpane left to right,
                // in gridpanes top to bottom totalCol * totalRow - (totalRow - currRow) currRow in which we found the first biggest element
                tileIndex = totalIndex - (rowBench - rowMinePit);
                System.out.println("Tile index: " + tileIndex);

                if (tileIndex < thirdGridPane.getChildren().size()) {
                    Node node = thirdGridPane.getChildren().get(tileIndex);

                    if (node instanceof StackPane) {
                        StackPane stackPane = (StackPane) node;
                        ObservableList<Node> children = stackPane.getChildren();

                        for (Node child : children) {
                            if (child instanceof Rectangle) {
                                Rectangle tile = (Rectangle) child;
                                tile.setFill(lighGreenBench);
                                break;
                            }
                        }
                    } else {
                        System.out.println("Error: The node is not a StackPane.");
                    }
                } else {
                    System.out.println("Error: Calculated tile index is out of bounds.");
                }
            }

        }



    // kc animate ba

    }
}
