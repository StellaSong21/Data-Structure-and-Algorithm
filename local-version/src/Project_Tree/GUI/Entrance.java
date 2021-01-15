package Project_Tree.GUI;

import Project_Tree.BTree.BTree;
import Project_Tree.BTree.Entry;
import Project_Tree.RBTree.RBTree;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import TOOL.IO;

import java.io.*;
import java.util.ArrayList;

public class Entrance extends Application {
    private int time = 0;
    private RBTree rbTree;
    private BTree bTree;
    private boolean isRedBlackTree = true;

    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane);
        primaryStage.setTitle("English-Chinese Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);

        // 右边
        VBox rightPane = new VBox(15);

        // 单选框
        RadioButton rbbt = new RadioButton("red-black tree");
        rbbt.setSelected(true);
        // rbbt.setPadding(new Insets(10,8,8,10));
        RadioButton bpbt = new RadioButton("B+ tree");
        ToggleGroup group = new ToggleGroup();
        rbbt.setToggleGroup(group);
        bpbt.setToggleGroup(group);
        HBox choosePane = new HBox(10);
        choosePane.getChildren().addAll(rbbt, bpbt);

        VBox rightdown = new VBox(10);
        rightdown.setStyle("-fx-border-color:#a19e9d");
        Label LOOKUP = new Label("LOOK-UP");
        Label searchfrom = new Label("search from");
        Label to = new Label("to");
        TextField searchtf = new TextField(); // 551
        Button translate = new Button("Translate");
        Button submit = new Button("Submit");
        TextField from = new TextField();
        from.setMaxWidth(70);
        TextField too = new TextField();
        too.setMaxWidth(70);
        HBox lay1 = new HBox(10);
        lay1.getChildren().addAll(searchtf, translate); // 476
        HBox lay2 = new HBox(10);
        lay2.getChildren().addAll(searchfrom, from, to, too, submit);
        rightdown.setPadding(new Insets(15, 25, 30, 10));

        TextArea result = new TextArea("here shows the result");
        result.setMaxWidth(400);
        result.setEditable(false);

        translate.setOnAction(event -> {
            if (isRedBlackTree) {
                if (!"".equals(searchtf.getText())) {
                    long startTime = System.nanoTime();
                    Project_Tree.RBTree.Node resulttt = rbTree.RBSearch(rbTree.getRoot(), searchtf.getText());
                    long consumingTime = System.nanoTime() - startTime;
                    if (resulttt.getKey() != null) {
                        result.setText("搜索时间(ns) ： " + consumingTime + "\n" + searchtf.getText() + " : "
                                + resulttt.getValue());
                    } else {
                        result.setText("搜索时间(ns) ： " + consumingTime + "\n" + "couldn't find this word");
                    }
                }
            } else {
                if (!"".equals(searchtf.getText())) {
                    long startTime = System.nanoTime();
                    Entry resultt = bTree.BSearch(bTree.getRoot(), searchtf.getText());
                    long consumingTime = System.nanoTime() - startTime;
                    if (resultt != null) {
                        result.setText(
                                "搜索时间(ns) ： " + consumingTime + "\n" + searchtf.getText() + " : " + resultt.getValue());
                    } else {
                        result.setText("搜索时间(ns) ： " + consumingTime + "\n" + "couldn't find this word");
                    }
                }
            }
        });
        submit.setOnAction(event -> {
            if (isRedBlackTree) {
                long startTime = System.nanoTime();
                ArrayList<Project_Tree.RBTree.Node> resultRange = rbTree.RBSearch(rbTree.getRoot(), from.getText(),
                        too.getText());
                long consumingTime = System.nanoTime() - startTime;
                if (resultRange != null) {
                    String s = "";
                    for (int i = 0; i < resultRange.size(); i++) {
                        s += "\n" + resultRange.get(i).getKey() + " : " + resultRange.get(i).getValue();
                    }
                    result.setText("搜索时间(ns) ： " + consumingTime + s);
                } else {
                    result.setText("couldn't find words");
                }
            } else {
                long startTime = System.nanoTime();
                ArrayList<Entry> results = bTree.BSearch(bTree.getRoot(), from.getText(), too.getText());
                long consumingTime = System.nanoTime() - startTime;
                if (results != null) {
                    String s = "";
                    for (int i = 0; i < results.size(); i++) {
                        s += "\n" + results.get(i).getKey() + " : " + results.get(i).getValue();
                    }
                    result.setText("搜索时间(ns) ： " + consumingTime + s);
                } else {
                    result.setText("couldn't find words");
                }
            }
        });

        rightPane.setPadding(new Insets(15, 25, 15, 10));
        rightdown.getChildren().addAll(LOOKUP, lay1, lay2);

        rightPane.getChildren().addAll(choosePane, rightdown, result);

        VBox leftPane = new VBox(30);
        leftPane.setPadding(new Insets(15, 10, 15, 25));
        Label MANAGEMENT = new Label("MANAGEMENT");
        TextField path = new TextField();
        path.setEditable(false);
        Button browser = new Button("Browser");

        browser.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Browser");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null && !"".equals(file))
                path.setText(file.toString());
        });

        Button Submitleft = new Button("Submit");

        Submitleft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(path.getText());
                    InputStreamReader reader = null;
                    try {
                        reader = new InputStreamReader(fis);
                    } catch (NullPointerException e) {
                        System.err.println("File Not Found");
                    }
                    // catch (UnsupportedEncodingException e) {
                    // System.err.println("UnsupportedEncodingException");
                    // }
                    BufferedReader br = new BufferedReader(reader);
                    String s = null;// insert
                    try {
                        s = br.readLine();
                    } catch (IOException e) {
                        System.err.println("IOException");
                    }
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if ("\uFEFFINSERT".equals(s)) {
                        time = 0;
                        long startTime = System.nanoTime();
                        long endTime;
                        if (isRedBlackTree) {
                            Project_Tree.RBTree.Node[] nodes = IO.readRBTxt(path.getText());
                            long[] times = new long[nodes.length / 100];
                            for (int i = 0; i < nodes.length; i++) {
                                rbTree.RBInsert(nodes[i]);
                                time++;
                                if (time % 100 == 0 && rbTree.getSize() <= 500) {
                                    rbTree.RBPreorderTreeWalk(rbTree.getRoot());
                                }
                                if (time % 100 == 0) {
                                    endTime = System.nanoTime();
                                    times[(time - 1) / 100] = endTime - startTime;
                                    startTime = System.nanoTime();
                                }
                            }
                            for (int j = 0; j < times.length; j++) {
                                System.out.println("插入第" + j + "个100个词条所需的时间：" + times[j] + "ns");
                            }
                            time = 0;
                        } else {
                            Entry[] entries = IO.readBTxt(path.getText());
                            long[] times = new long[entries.length / 100];
                            for (int i = 0; i < entries.length; i++) {
                                bTree.BInsert(entries[i]);
                                time++;
                                if (time % 100 == 0 && bTree.getSize() <= 500) {
                                    bTree.BPreorderTreeWalk(bTree.getRoot());
                                }
                                if (time % 100 == 0) {
                                    endTime = System.nanoTime();
                                    times[(time - 1) / 100] = endTime - startTime;
                                    startTime = System.nanoTime();
                                }
                            }
                            for (int j = 0; j < times.length; j++) {
                                System.out.println("插入第" + j + "个100个词条所需的时间：" + times[j] + "ns");
                            }
                            time = 0;
                        }
                    } else if ("\uFEFFDELETE".equals(s)) {
                        time = 0;
                        long startTime = System.nanoTime();
                        long endTime;
                        if (isRedBlackTree) {
                            String[] strings = IO.readDel(path.getText());
                            long[] times = new long[strings.length / 100];
                            for (int i = 0; i < strings.length; i++) {
                                int x = rbTree.RBDelete(strings[i]);
                                if (x == 0)
                                    time++;
                                if (time % 100 == 0 && rbTree.getSize() <= 500) {
                                    rbTree.RBPreorderTreeWalk(rbTree.getRoot());
                                }
                                if (time % 100 == 0) {
                                    endTime = System.nanoTime();
                                    times[(time - 1) / 100] = endTime - startTime;
                                    startTime = System.nanoTime();
                                }
                            }
                            for (int j = 0; j < times.length; j++) {
                                System.out.println("删除第" + j + "个100个词条所需的时间：" + times[j] + "ns");
                            }
                            time = 0;
                        } else {
                            String[] strings = IO.readDel(path.getText());
                            long[] times = new long[strings.length / 100];
                            for (int i = 0; i < strings.length; i++) {
                                int x = bTree.BDelete(strings[i]);
                                if (x == 0)
                                    time++;
                                if (time % 100 == 0 && bTree.getSize() <= 500) {
                                    bTree.BPreorderTreeWalk(bTree.getRoot());
                                }
                                if (time % 100 == 0) {
                                    endTime = System.nanoTime();
                                    times[(time - 1) / 100] = endTime - startTime;
                                    startTime = System.nanoTime();
                                }
                            }
                            for (int j = 0; j < times.length; j++) {
                                System.out.println("删除第" + j + "个100个词条所需的时间：" + times[j] + "ns");
                            }
                            time = 0;
                        }
                    }
                    path.setText("");
                    if (isRedBlackTree)
                        System.out.println("树内目前含有： " + rbTree.getSize() + " 个数");
                    else
                        System.out.println("树内目前含有： " + bTree.getSize() + " 个数");
                } catch (FileNotFoundException e) {
                    System.err.println("File Not Found");
                }
            }
        });

        VBox leftup = new VBox(20);
        leftup.setPadding(new Insets(15, 15, 70, 15));
        leftup.setMinWidth(500);
        leftup.setStyle("-fx-border-color:#a19e9d");
        HBox BaS = new HBox(15);
        BaS.getChildren().addAll(browser, Submitleft);
        leftup.getChildren().addAll(MANAGEMENT, path, BaS);
        BaS.setAlignment(Pos.CENTER);

        VBox leftdown = new VBox(20);
        Label english = new Label("English:");
        Label chinese = new Label("Chinese:");
        Button add = new Button("Add");
        Button delete = new Button("Delete");
        TextField englishtf = new TextField();
        TextField chinesetf = new TextField();
        englishtf.setMaxWidth(120);
        chinesetf.setMaxWidth(120);
        HBox lline1 = new HBox(15);
        lline1.getChildren().addAll(english, englishtf, chinese, chinesetf);
        HBox lline2 = new HBox(15);
        lline2.getChildren().addAll(add, delete);
        lline1.setAlignment(Pos.CENTER);
        lline2.setAlignment(Pos.CENTER);

        leftdown.setStyle("-fx-border-color:#a19e9d");
        leftdown.setPadding(new Insets(30, 15, 65, 15));

        leftdown.getChildren().addAll(lline1, lline2);

        leftPane.getChildren().addAll(leftup, leftdown);

        pane.setRight(rightPane);
        pane.setLeft(leftPane);
        primaryStage.show();

        rbTree = initializeRB();

        add.setOnAction(event -> {
            if (isRedBlackTree) {
                rbTree.RBInsert(new Project_Tree.RBTree.Node(englishtf.getText(), chinesetf.getText()));
            } else {
                bTree.BInsert(new Entry(englishtf.getText(), chinesetf.getText()));
            }
            englishtf.setText("");
            chinesetf.setText("");

            // if (time == 100 && rbTree.getSize() <= 500) {
            // rbTree.RBPreorderTreeWalk(rbTree.getRoot());
            // time = 0;
            // }
        });
        delete.setOnAction(event -> {
            if (!"".equals(englishtf.getText())) {
                if (isRedBlackTree) {
                    rbTree.RBDelete(englishtf.getText());
                } else {
                    bTree.BDelete(englishtf.getText());
                }
                englishtf.setText("");
                chinesetf.setText("");
                // if (time == 100 && bTree.getSize() <= 500) {
                // bTree.BPreorderTreeWalk(bTree.getRoot());
                // time = 0;
                // }
            }

        });

        rbbt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isRedBlackTree = true;
                englishtf.setText("");
                chinesetf.setText("");
                from.setText("");
                too.setText("");
                path.setText("");
                searchtf.setText("");
                result.setText("here shows the result");
                try {
                    rbTree = initializeRB();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        bpbt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isRedBlackTree = false;
                englishtf.setText("");
                chinesetf.setText("");
                from.setText("");
                too.setText("");
                path.setText("");
                searchtf.setText("");
                result.setText("here shows the result");
                try {
                    bTree = initializeBP();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public RBTree initializeRB() throws IOException {
        time = 0;
        RBTree rbtree = new RBTree();
        return rbtree;
    }

    public BTree initializeBP() throws IOException {
        time = 0;
        BTree bptree = new BTree();
        return bptree;
    }
}