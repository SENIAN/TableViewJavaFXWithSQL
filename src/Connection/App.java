package Connection;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.File;
import java.net.URL;
import java.sql.*;

import javafx.scene.control.TableColumn.CellDataFeatures;
/**
 * Created by Mohammed on 10/18/2016.
 *
 * • Add, delete, or modify an employee, including the data about his/her
 address, education, and job position.
 • Add, modify, delete a project, including its location.
 • Assign an existing employee to a project.
 • Check what projects cannot pay the rent, that is, the rent is higher than
 10% of their budget.
 H
 */
public class App extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader guiLoader = new FXMLLoader(getClass().getResource("/Connection/javafx.fxml"));
        Pane pane1 = guiLoader.load();
        Scene scene = new Scene(pane1);
        primaryStage.setTitle("SQL Manager CRUD");
        primaryStage.setScene(scene);
        primaryStage.show();

        CRUD controller = (CRUD)guiLoader.getController();

        controller.readDatabase();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
