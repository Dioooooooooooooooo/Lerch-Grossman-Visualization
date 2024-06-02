package com.example.minerz;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


import static com.example.minerz.LoginController.userId;


public class LandController {

    public Label profitText;
    private Color currentColor;
    public RadioButton radioBtn5;
    public RadioButton radioBtn6;
    public RadioButton radioBtn7;
    private static final double TILE_MARGIN = 1.0;
    private static final double VERTICAL_MARGIN = 5.0;
    private static final double HORIZONTAL_MARGIN = 5.0;
    public TextField txtRows;
    public TextField txtCols;
    public Button btnCreateLand;
    public BorderPane borderPane;
    public GridPane gridPane;
    public VBox vboxBottom;
    public ScrollPane scrollPane;

    int rowBench;
    int colBench;

    boolean isPit = false;
    int partitionIndex = 0;

    GridPane thirdTable;
    GridPane secondTable;

    Label secondTableLabel;
    Label thirdTableLabel;
    int profit;
    Map<String, Integer> partitionMap = new HashMap<>();
    Map<Integer, Integer> indexMaxMap = new HashMap<>();

    int[][] secondTableArray;
    int[][] thirdTableArray;

    private ToggleGroup toggleGroup = new ToggleGroup();



    Color lightGreenBench = Color.rgb(144, 238, 144, 0.5);
    Color lighterBrownGreen = Color.rgb(204, 204, 0);
    Color lightBrownGreen = Color.rgb(173, 143, 96);
    Color brownGreen = Color.rgb(102, 102, 0);
    Color green = Color.rgb(0, 128, 0);
    Color darkBrownGreen = Color.rgb(51, 51, 0);
    Color darkerBrownGreen = Color.rgb(102, 51, 0);
    Color evenDarkerBrownGreen = Color.rgb(51, 25, 0);
    Color lighterOrange = Color.rgb(255, 204, 153);
    Color light0range = Color.rgb(255, 170, 27);
    Color orange = Color.rgb(255, 127, 0);

    Color black = Color.rgb(0, 0, 0);

    Color gray = Color.rgb(128, 128, 128);

    Color lightgray = Color.rgb(211, 211, 211);



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

        radioBtn5.setToggleGroup(toggleGroup);
        radioBtn6.setToggleGroup(toggleGroup);
        radioBtn7.setToggleGroup(toggleGroup);

        radioBtn5.setCursor(Cursor.HAND);
        radioBtn6.setCursor(Cursor.HAND);
        radioBtn7.setCursor(Cursor.HAND);

        radioBtn5.setOnMouseEntered(e -> radioBtn5.setCursor(Cursor.HAND));
        radioBtn5.setOnMouseExited(e -> radioBtn5.setCursor(Cursor.DEFAULT));

        radioBtn6.setOnMouseEntered(e -> radioBtn5.setCursor(Cursor.HAND));
        radioBtn6.setOnMouseExited(e -> radioBtn5.setCursor(Cursor.DEFAULT));

        radioBtn7.setOnMouseEntered(e -> radioBtn5.setCursor(Cursor.HAND));
        radioBtn7.setOnMouseExited(e -> radioBtn5.setCursor(Cursor.DEFAULT));


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
        thirdTable = new GridPane();
        int rows;
        int cols;

        if (!gridPane.getChildren().isEmpty()) {
            MediaUtil.getInstance().playSoundEffect();
            showAlert("You can't", "There's an existing land sample!");
            return;
        }

        try {
            rows = Integer.parseInt(txtRows.getText());
            cols = Integer.parseInt(txtCols.getText());
            System.out.println(cols + " " + rows);
            rowBench = rows;
            colBench = cols;

        } catch (NumberFormatException e) {
            MediaUtil.getInstance().playSoundEffect();
            showAlert("Invalid input", "Please enter valid numbers for rows and columns.");
            return;
        } catch (Exception e) {
            MediaUtil.getInstance().playSoundEffect();
            showAlert("Invalid input", "Please enter numbers for rows and columns.");
            return;
        }

        gridPane.getChildren().clear();

        double availableWidth = vboxBottom.getWidth() - (cols + 1) * TILE_MARGIN - 2 * HORIZONTAL_MARGIN;
        double availableHeight = vboxBottom.getHeight() - (rows + 1) * TILE_MARGIN - 2 * VERTICAL_MARGIN;

        double tileWidth = availableWidth / cols;
        double tileHeight = availableHeight / rows;

        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        Rectangle tile = new Rectangle(tileWidth, tileHeight);
                        tile.setFill(Color.LIGHTGRAY);
                        tile.setStroke(Color.BLACK);
                        gridPane.add(tile, j, i);
                    }
                }

                System.out.println("first gridpane row and col " + gridPane.getRowCount() + " " + gridPane.getColumnCount());

                vboxBottom.getChildren().clear();
                vboxBottom.getChildren().add(gridPane);
                drillData();
                MediaUtil.getInstance().playSoundEffect();
                Scene scene = borderPane.getScene();
                if (scene != null) {
                    Stage stage = (Stage) scene.getWindow();
                    if (stage != null) {
                        stage.setMaximized(true);
                    }
                }
            });
        });

        MediaUtil.getInstance().playSoundEffect();
        thread.start();
    }


    private Color getCurrentColor(){
        System.out.println("Current color is: " + currentColor);
        return currentColor;
    }
    private Color setCurrentColor(int val){
        switch (val){
            case 5:
                currentColor = lightgray;
                break;
            case 6:
                currentColor = gray;
                break;
            case 7:
                currentColor = black;
            default:
                break;
        }


        return currentColor;
    }

    private void updateText(Rectangle tile, Text text, Color color){
        text.setAccessibleText("");
        if(color == null){
            showAlert("No values selected","Please select a value in the radiobutton above.");
            return;
        }

        if(color.equals(lightgray)){
            tile.setFill(color);
            text.setText("5");
            tile.setAccessibleText("5");
        }else if(color.equals(gray)){
            tile.setFill(color);
            text.setText("6");
            tile.setAccessibleText("6");
        }else if(color.equals(black)){
            tile.setFill(color);
            text.setText("7");
            tile.setAccessibleText("7");
        }
        MediaUtil.getInstance().playDirt();
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
        System.out.println("row and col in drilldata " + rows + " " + cols);
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

        while (remaining != 0) {
            if (partitionIndex == 6) partitionIndex = 0;
            partitionRows[partitionIndex] = partitionRows[partitionIndex] + 1;
            partitionIndex++;
            remaining--;
        }

        int rowIndex = 0;
        while (partitionRows[partitionMap.get("LIGHTESTBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("2");
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(lighterBrownGreen);
                tile.setAccessibleText("2");

                tile.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                tile.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));
                text.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                text.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));

                text.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                tile.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));

                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("LIGHTESTBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("LIGHTBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("1");
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(lightBrownGreen);
                tile.setAccessibleText("1");

                tile.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                tile.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));
                text.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                text.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));

                text.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                tile.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("LIGHTBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("BROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("0");
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(brownGreen);
                tile.setAccessibleText("0");
                tile.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                tile.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));
                text.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                text.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));

                text.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                tile.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("BROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("GREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("-1");
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(green);
                tile.setAccessibleText("-1");
                tile.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                tile.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));
                text.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                text.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));

                text.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                tile.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("GREEN")]--;
        }

        while (partitionRows[partitionMap.get("DARKBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("-2");
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(darkBrownGreen);
                tile.setAccessibleText("-2");
                tile.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                tile.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));
                text.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                text.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));

                text.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                tile.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("DARKBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("DARKERBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++) {
                Text text = new Text("-3");
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(darkerBrownGreen);
                tile.setAccessibleText("-3");
                tile.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                tile.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));
                text.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                text.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));

                text.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                tile.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("DARKERBROWNGREEN")]--;
        }

        while (partitionRows[partitionMap.get("DARKESTBROWNGREEN")] > 0) {
            for (int j = 0; j < cols; j++){
                Text text = new Text("-4");
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.WHITE);
                GridPane.setHalignment(text, HPos.CENTER);
                Rectangle tile = (Rectangle) gridPane.getChildren().get(rowIndex);
                tile.setFill(evenDarkerBrownGreen);
                tile.setAccessibleText("-4");
                tile.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                tile.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));
                text.setOnMouseEntered(e -> tile.setCursor(Cursor.HAND));
                text.setOnMouseExited(e -> tile.setCursor(Cursor.DEFAULT));

                text.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                tile.setOnMouseClicked(e -> updateText(tile, text, getCurrentColor()));
                gridPane.add(text, j, rowIndex / cols);
                rowIndex++;
            }
            partitionRows[partitionMap.get("DARKESTBROWNGREEN")]--;
        }


    }


    public GridPane createSecondTable(int rows, int cols) {

        GridPane secondGridPane = new GridPane();
        System.out.println("2nd gridpane rows and cols " + rows + " " + cols);
        secondTableArray = new int[rows][cols];
        secondGridPane.getChildren().clear();
        secondGridPane.setHgap(TILE_MARGIN);
        secondGridPane.setVgap(TILE_MARGIN);
        double availableWidth = vboxBottom.getWidth() - (rows + 1) * TILE_MARGIN - 2 * HORIZONTAL_MARGIN;
        double availableHeight = vboxBottom.getHeight() - (cols + 1) * TILE_MARGIN - 2 * VERTICAL_MARGIN;
        double tileWidth = availableWidth / cols;
        double tileHeight = availableHeight / rows;

        // first row
        for (int i = 0; i < rows; i++) {
            for (Node node : gridPane.getChildren()) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);

                // for first col
                if (col == 0 && row == i) {
                    if (node instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) node;
                        int val = Integer.parseInt(rectangle.getAccessibleText());
                        secondTableArray[i][0] = val;
                    } else if (node instanceof Text) {
                        Text text = (Text) node;
                        int val = Integer.parseInt(text.getText());
                        secondTableArray[i][0] = val;
                    }
                }
            }
        }


        // the orig second table's value
        for (Node node : gridPane.getChildren()) {
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);
            System.out.println("row and col" + row + " "+ col);
            if (node instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) node;
                int val = Integer.parseInt(rectangle.getAccessibleText());
                secondTableArray[row][col] = val;
            } else if (node instanceof Text) {
                Text text = (Text) node;
                int val = Integer.parseInt(text.getText());
                secondTableArray[row][col] = val;
            }

        }

        // System.out.println("The final row and col: " + finalRow + " " + finalCol + "the final value which should -52 with 55x60 grid: " + secondTableArray[finalRow][finalCol]);
        // sakto najud
        System.out.println("Second table: ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(secondTableArray[i][j] + " ");
            }
            System.out.println();
        }



        for (int j = 0; j < cols; j++) {
            int cumulativeSum = 0;
            for (int i = 0; i < rows; i++) {
                int val = secondTableArray[i][j];
                cumulativeSum += val;
                Rectangle tile = new Rectangle(tileWidth, tileHeight);
                tile.setFill(Color.TRANSPARENT);
                tile.setStroke(Color.BLACK);
                tile.setAccessibleText(String.valueOf(cumulativeSum));

                Text text = new Text(String.valueOf(cumulativeSum));
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.BLACK);
                GridPane.setHalignment(text, HPos.CENTER);

                secondGridPane.add(tile, j, i);
                secondGridPane.add(text, j, i);
            }
            System.out.println();
        }

//        secondTableLabel = new Label("Second Table");
//        secondTableLabel.setFont(Font.font("Minecraft Regular" ,15));
//        secondTableLabel.setPadding(new Insets(10, 0, 10, 0));
//        vboxBottom.getChildren().addAll(secondTableLabel, secondGridPane);
        return secondGridPane;

    }

    public void lerchGrossMan(int rows, int cols) {
        int rowArrays = rows + 1;
        int colArrays = cols + 1;
        System.out.println("third table rows and cols " + rowArrays + " " + colArrays);
        GridPane thirdGridPane = new GridPane();
        thirdTableArray = new int[rowArrays][colArrays];
        thirdTableArray[0][0] = 0;

        Thread[] threads = new Thread[colArrays - 1];

        for (int j = 1; j < colArrays; j++) {
            final int colIndex = j;
            threads[j - 1] = new Thread(() -> {
                for (int i = 0; i < rowArrays; i++) {
                    if (i == 0) {
                        int left = thirdTableArray[i][colIndex - 1];
                        int leftDown = i + 1 < rowArrays ? thirdTableArray[i + 1][colIndex - 1] : 0;
                        thirdTableArray[i][colIndex] = secondTableArray[i][colIndex - 1] + getMax(0, left, leftDown);
                    } else if (i == rowArrays - 1) {
                        int left = thirdTableArray[i][colIndex - 1];
                        int leftUp = i - 1 >= 0 ? thirdTableArray[i - 1][colIndex - 1] : 0;
                        thirdTableArray[i][colIndex] = secondTableArray[i - 1][colIndex - 1] + getMax(leftUp, left, 0);
                    } else {
                        int leftUp = i - 1 >= 0 ? thirdTableArray[i - 1][colIndex - 1] : 0;
                        int left = thirdTableArray[i][colIndex - 1];
                        int leftDown = i + 1 < rowArrays ? thirdTableArray[i + 1][colIndex - 1] : 0;
                        thirdTableArray[i][colIndex] = secondTableArray[i - 1][colIndex - 1] + getMax(leftUp, left, leftDown);
                    }
                }
            });
            threads[j - 1].start();
            try {
                threads[j - 1].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        double availableWidth = vboxBottom.getWidth() - (rows + 1) * TILE_MARGIN - 2 * HORIZONTAL_MARGIN;
        double availableHeight = vboxBottom.getHeight() - (cols + 1) * TILE_MARGIN - 2 * VERTICAL_MARGIN;
        double tileWidth = availableWidth / cols;
        double tileHeight = availableHeight / rows;

        for (int j = 1; j < colArrays; j++) {
            for (int i = 1; i < rowArrays; i++) {
                Rectangle tile = new Rectangle(tileWidth, tileHeight);
                tile.setFill(Color.TRANSPARENT);
                tile.setStroke(Color.BLACK);

                int val = thirdTableArray[i][j];
                Text text = new Text(String.valueOf(val));
                text.setFont(Font.font("Minecraft Regular", 15));
                text.setFill(Color.BLACK);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(text, tile);

                thirdGridPane.add(stackPane, j, i);

            }
        }

        thirdTableLabel = new Label("");
        thirdTableLabel.setFont(Font.font("Minecraft Regular", 15));
        thirdTableLabel.setPadding(new Insets(10, 0, 10, 0));
        vboxBottom.getChildren().addAll(thirdTableLabel, thirdGridPane);
        thirdTable = thirdGridPane;
    }



    int calculateProfit() {
        int grossProfit = 0;
        int totalBlocks = 0;
        int[] values = new int[8];

        TreeMap<Integer, Integer> sortedIndexMaxMap = new TreeMap<>(indexMaxMap);

        for (Map.Entry<Integer, Integer> entry : sortedIndexMaxMap.entrySet()) {
            int column = entry.getKey();
            int row = entry.getValue();
            int i = 0;

            while (i <= row){
                int index = (i * colBench) + column;
                Rectangle tile = (Rectangle) gridPane.getChildren().get(index);
                int value = Integer.parseInt(tile.getAccessibleText());
                switch (value) {
                    case 0:
                        values[0] += 10000;
                        break;
                    case 1:
                        values[1] += 15000;
                        break;
                    case 2:
                        values[2] += 20000;
                        break;
                    case 3:
                        values[3] += 25000;
                        break;
                    case 4:
                        values[4] += 30000;
                        break;
                    case 5:
                        values[5] += 35000;
                        break;
                    case 6:
                        values[6] += 40000;
                        break;
                    case 7:
                        values[7] += 45000;
                        break;
                    default:
                        break;
                }
                i++;
                totalBlocks++;
            }

        }

        for (int value : values) {
            grossProfit += value;
        }
        grossProfit -= totalBlocks * 5000;
        System.out.println("total 0s " + values[0]);
        System.out.println("total 1s " + values[1]);
        System.out.println("total 2s " + values[2]);
        System.out.println("total 3s " + values[3]);
        System.out.println("total 4s " + values[4]);
        System.out.println("total 5s " + values[5]);
        System.out.println("total 6s " + values[6]);
        System.out.println("total 7s " + values[7]);
        System.out.println("total blocks " + totalBlocks);
        System.out.println("TOTAL PROFIT " + grossProfit);
        profitText.setText("₱" + String.valueOf(grossProfit));

        ProfitQuery profit = new ProfitQuery();

        String username = profit.getUsernameByUserId(userId);
        if (username != null) {
            System.out.println("The username for userId " + userId + " is " + username);
        } else {
            System.out.println("No username found for userId " + userId);
        }

        profit.saveProfit(userId,grossProfit,username);
        return grossProfit;

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
        MediaUtil.getInstance().playSoundEffect();
        gridPane.getChildren().clear();
        vboxBottom.getChildren().clear();
        isPit = false;
        profitText.setText("");
    }
    public void clearPit(ActionEvent actionEvent) {
        MediaUtil.getInstance().playSoundEffect();

        if (!isPit) {
            showAlert("Mine pit", "The pit mine is empty!");
            MediaUtil.getInstance().playSoundEffect();
            return;
        }

        thirdTable.getChildren().clear();
        gridPane.getChildren().remove(thirdTable);
        thirdTableLabel.setText("");
        isPit = false;
        profitText.setText("");

    }




    public void minePit(ActionEvent actionEvent) {
        System.out.println("Currently benching...");
        if (isPit) {
            showAlert("Mine pit", "There's still a mine pit!");
            MediaUtil.getInstance().playSoundEffect();
            return;
        }
        if (gridPane.getChildren().isEmpty()) {
            showAlert("No Land", "Please create land first to have an upper pit limit.");
            MediaUtil.getInstance().playSoundEffect();
            return;
        }
        MediaUtil.getInstance().playSoundEffect();
        secondTable = createSecondTable(rowBench, colBench);
        lerchGrossMan(rowBench, colBench);

        for (int j = colBench; j >= 1; j--) {
            int max = Integer.MIN_VALUE;
            int rowMinePit = -1;
            int tileIndex;

            for (int i = 1; i <= rowBench; i++) {
                if (thirdTableArray[i][j] > max) {
                    max = thirdTableArray[i][j];
                    rowMinePit = i - 1;
                } else if (thirdTableArray[i][j] == max && i - 1 > rowMinePit) {
                    rowMinePit = i - 1;
                }
            }

            if (j > 1) {
                int nextMax = Integer.MIN_VALUE;
                int nextRowMinePit = -1;
                for (int k = j - 1; k >= Math.max(1, j - 2); k--) {
                    for (int i = 1; i <= rowBench; i++) {
                        if (thirdTableArray[i][k] > nextMax) {
                            nextMax = thirdTableArray[i][k];
                            nextRowMinePit = i - 1;
                        } else if (thirdTableArray[i][k] == nextMax && i - 1 > nextRowMinePit) {
                            nextRowMinePit = i - 1;
                        }
                    }
                }

                if (nextMax > max) {
                    max = nextMax;
                    rowMinePit = nextRowMinePit;
                    j = Math.max(1, j - 2) + 1;
                }
            }

            System.out.println("Max value: " + max);
            System.out.println("Index of max value (rowMinePit): " + rowMinePit);
            System.out.println("Total columns (j or currentColBench): " + j);

            if (rowMinePit != -1) {
                int totalIndex = (j - 1) * rowBench;
                System.out.println("Total index: " + totalIndex);
                tileIndex = totalIndex + rowMinePit;
                System.out.println("Tile index: " + tileIndex);

                if (tileIndex < thirdTable.getChildren().size()) {
                    Node node = thirdTable.getChildren().get(tileIndex);

                    if (node instanceof StackPane) {
                        StackPane stackPane = (StackPane) node;
                        ObservableList<Node> children = stackPane.getChildren();

                        for (Node child : children) {
                            if (child instanceof Rectangle) {
                                Rectangle tile = (Rectangle) child;
                                tile.setFill(lightGreenBench);
                                break;
                            }
                        }
                    } else {
                        MediaUtil.getInstance().playSoundEffect();
                        System.out.println("Error: The node is not a StackPane.");
                    }
                } else {
                    MediaUtil.getInstance().playSoundEffect();
                    System.out.println("Error: Calculated tile index is out of bounds.");
                }
            }
            System.out.println("max row at that coloumn " + rowMinePit + " " + (j-1));
            // legit
            indexMaxMap.put(j-1, rowMinePit);

        }



        isPit = true;
        calculateProfit();
        MediaUtil.getInstance().playSoundEffect();
    }






    @FXML
    private void handleRadioButtonAction() {
        if (radioBtn5.isSelected()) {
            System.out.println("RadioButton 1 is selected.");
            setCurrentColor(5);
        }

        if (radioBtn6.isSelected()) {
            System.out.println("RadioButton 2 is selected.");
            setCurrentColor(6);
        }

        if (radioBtn7.isSelected()) {
            System.out.println("RadioButton 3 is selected.");
            setCurrentColor(7);
        }
    }


}
