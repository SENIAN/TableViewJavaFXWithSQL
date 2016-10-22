package Connection;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.sql.*;

/**
 * Created by SENIAN on 10/20/2016.
 *
 * Application is able to insert, delete, modify employeees,
 * Application is able to add new projects and link that to employees. Be sure to first select the Employee table and then choose the employee from the choicebox.
 * Application uses AUTO_INCREMENT on projectid so dont choose a projectid. Only choose the projectid if you'd like to delete the desired project.
 * Application is able to select every Table inside the database which is dynamic and is showed inside the tableview.
 * Application is only able to mutate the tables Employee and Project because that is what was requested by the assignment.
 * Application gives feedback of the SQL errors that are shown.
 * Application does not use SSL so you can ignore the SSL warning.
 */
public class CRUD {

    ObservableList<String> row = FXCollections.observableArrayList();
    private MySql mySQL = new MySql();
    private Statement statement = null;
    private Connection connection = null;
    @FXML private Button deleteEmployee;
    @FXML private Button modifyEmployee;
    @FXML private TextArea errorBox;
    @FXML private TableView tblviewFX;
    @FXML private ChoiceBox<String> choiceboxFX;
    @FXML private ChoiceBox<String> bsnModify;
    @FXML private ChoiceBox<String> bsnModify1;
    @FXML private ChoiceBox projectIDbox;
    @FXML private Pane pane1;
    @FXML private Button buttonfx;
    @FXML private Button addingNewEmployee;
    @FXML private TextField naam;
    @FXML private TextField achternaam;
    @FXML private TextField address;
    @FXML private TextField huisnummer;
    @FXML private TextField postcode;
    @FXML private TextField stad;
    @FXML private TextField land;
    @FXML private TextField bsn;
    @FXML private TextField budgetProject;
    @FXML private TextField hoursProject;
    @FXML private TextField descProject;

    public void readDatabase() {
        try {
            connection = mySQL.getSqlConnection();
            statement = connection.createStatement();
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            ObservableList<String> tables = FXCollections.observableArrayList();
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            choiceboxFX.setItems(tables);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void loadTableIntoView() {
        refresh();
        ObservableList<ObservableList> rowData = FXCollections.observableArrayList();
        ObservableList<String> idData = FXCollections.observableArrayList();
        try {
            connection = mySQL.getSqlConnection();
            statement = connection.createStatement();
            String checkboxval = choiceboxFX.getValue();
            if (checkboxval != null) {
                String SQL = "SELECT * FROM " + checkboxval.toString();
                statement.execute(SQL);
                ResultSet rs = statement.executeQuery(SQL);
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }});           tblviewFX.getColumns().addAll(col); }

                        while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }rowData.add(row);}
                tblviewFX.setItems(rowData);

                for (int i = 0; i < rowData.size(); i++) {
                    idData.add(rowData.get(i).iterator().next().toString());
                }

                bsnModify.setItems(idData);
                projectIDbox.setItems(idData);
                bsnModify1.setItems(idData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void refresh() {
        tblviewFX.getItems().clear();
        tblviewFX.getColumns().clear();
    }

    public void insertIntoEmployee() {
        try {
            connection = mySQL.getSqlConnection();
            statement = connection.createStatement();
            StringBuffer buffer = new StringBuffer(new StringBuilder(7));
            buffer.append("INSERT INTO   " + "employee" + " VALUES ( ");
            buffer.append('"' +
                    bsn.getText() + '"' + ',' + '"' +
                    naam.getText() + '"' + ',' + '"' +
                    achternaam.getText() + '"' + ',' + '"' +
                    address.getText() + '"' + ',' + '"' +
                    huisnummer.getText() + '"' + ',' + '"' +
                    postcode.getText() + '"' + ',' + '"' +
                    stad.getText() + '"' + ',' + '"' +
                    land.getText() + '"' + ')' + ';');
            String SQL = buffer.toString();
            statement.execute(SQL);
        } catch (SQLException e) {
            errorBox.setText(e.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    public void modifyEmployee() {
        try {
            connection = mySQL.getSqlConnection();
            StringBuilder arr1 = new StringBuilder(7);
            String i = null;
            PreparedStatement ps1 = connection.prepareStatement(
                    "UPDATE  employee  SET naam = ? , achternaam = ? , adres = ?, huisnummer = ?,  postcode = ?, stad = ?, land = ? WHERE bsnnr = ?");
            ps1.setString(1, naam.getText());
            ps1.setString(2, achternaam.getText());
            ps1.setString(3, address.getText());
            ps1.setString(4, huisnummer.getText());
            ps1.setString(5, postcode.getText());
            ps1.setString(6, stad.getText());
            ps1.setString(7, land.getText());
            ps1.setString(8, bsnModify.getValue());
            ps1.executeUpdate();
            ps1.close();
        } catch (SQLException e) {
            errorBox.setText(e.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee() {
        try {
            connection = mySQL.getSqlConnection();
            StringBuilder arr1 = new StringBuilder(7);
            String i = null;
            PreparedStatement ps2 = connection.prepareStatement(
                    "DELETE FROM  employee  WHERE bsnnr = ?");
            ps2.setString(1, bsnModify.getValue());
            ps2.executeUpdate();
            ps2.close();
        } catch (SQLException e) {
            errorBox.setText(e.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    public void insertDefaultValue() {
        naam.clear();
        achternaam.clear();
        postcode.clear();
        huisnummer.clear();
        land.clear();
        stad.clear();
        address.clear();
        bsn.clear();
        budgetProject.clear();
        hoursProject.clear();
        descProject.clear();
        bsn.setText(bsnModify.getValue());
    }

    public void insertIntProject() {
        try {
            connection = mySQL.getSqlConnection();
            statement = connection.createStatement();
            StringBuilder arr1 = new StringBuilder(7);
            String i = null;
            arr1.append("INSERT INTO   " + "project" + " VALUES " + "(");
            arr1.append(i + ',');
            arr1.append('"' + budgetProject.getText() + '"' + ',');
            arr1.append('"' + hoursProject.getText() + '"' + ',');
            arr1.append('"' + descProject.getText() + '"' + ',');
            arr1.append('"' + bsnModify1.getValue() + '"' + ')' + ';');
            String SQL = arr1.toString();
            statement.execute(SQL);
        } catch (SQLException e) {
            errorBox.setText(e.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void modifyIntoProjects() {
        try {
            connection = mySQL.getSqlConnection();
            StringBuilder arr1 = new StringBuilder(7);
            String i = null;
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE  project  SET budget = ? , hours = ? , description = ? WHERE projectid = ?");
            ps.setString(1, budgetProject.getText());
            ps.setString(2, hoursProject.getText());
            ps.setString(3, descProject.getText());
            ps.setString(4, projectIDbox.getValue().toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            errorBox.setText(e.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject() {
        try {
            connection = mySQL.getSqlConnection();
            StringBuilder arr1 = new StringBuilder(7);
            String i = null;
            PreparedStatement ps2 = connection.prepareStatement(
                    "DELETE FROM  project  WHERE projectid = ?");
            ps2.setString(1, projectIDbox.getValue().toString());
            ps2.executeUpdate();
            ps2.close();
        } catch (SQLException e) {
            errorBox.setText(e.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }


}
