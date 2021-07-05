package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class ContasController {
    @FXML
    private Button SairButton;
    @FXML
    private Button BackButton;
    @FXML
    private TextField NameField;
    @FXML
    private TextField UserField;
    @FXML
    private TextField PasswordField;
    @FXML
    private TextField CursField;
    @FXML
    private Label RegisterLabel;
    @FXML
    private TextField pesquisaField;

    public void RegisterButtonOnAction(ActionEvent event)throws SQLException {
        if(NameField.getText().isBlank() == false && UserField.getText().isBlank() == false && PasswordField.getText().isBlank() == false && CursField.getText().isBlank() == false){
            RegisterValidate();
        }else {
            RegisterLabel.setText("Preencha Todos os Campos");
        }
    }

    public void RegisterValidate()throws SQLException{
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:unifaesp.db");
        PreparedStatement stmt;
        if(connection != null){
            String save = "insert into users (name, user, password,curs) values (?, ?, ?, ?)";
            stmt = connection.prepareStatement(save);
            stmt.setString(1, NameField.getText().toString());
            stmt.setString(2, UserField.getText().toString());
            stmt.setString(3, PasswordField.getText().toString());
            stmt.setString(4, CursField.getText().toString());
            if(stmt.execute() == false) {
                RegisterLabel.setText("Registro Salvo com Sucesso");
                NameField.setText("");
                UserField.setText("");
                PasswordField.setText("");
                CursField.setText("");
            }else
                RegisterLabel.setText("Falha ao Registrar");
            stmt.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unifaesp");
            alert.setContentText("Falha na Conexão com Banco de Dados!");
            alert.show();
        }
    }

    public void PesquisaOnAction(ActionEvent event)throws SQLException{
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:unifaesp.db");
        PreparedStatement stmt;
        ResultSet rst;
        String search = "select *from users where Id_user = ?";
        stmt = connection.prepareStatement(search);
        int id = Integer.parseInt(pesquisaField.getText().toString());
        stmt.setInt(1, id);
        rst = stmt.executeQuery();
        while(rst.next()){
            NameField.setText(rst.getString("name"));
            UserField.setText(rst.getString("user"));
            PasswordField.setText(rst.getString("password"));
            CursField.setText(rst.getString("curs"));
        }
        rst.close();
        stmt.close();
        RegisterLabel.setText("");
    }

    public void updateOnAction(ActionEvent event) throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:unifaesp.db");
        PreparedStatement stmt;
        if(connection != null){
            String update = "update users set name = ?, user = ?, password = ?, curs = ? where id_user = ?";
            stmt = connection.prepareStatement(update);
            stmt.setString(1, NameField.getText().toString());
            stmt.setString(2, UserField.getText().toString());
            stmt.setString(3, PasswordField.getText().toString());
            stmt.setString(4, CursField.getText().toString());
            stmt.setInt(5, Integer.parseInt(pesquisaField.getText().toString()));
            if(stmt.execute() == false) {
                RegisterLabel.setText("Registro Atualizado Sucesso");
                pesquisaField.setText("");
                NameField.setText("");
                UserField.setText("");
                PasswordField.setText("");
                CursField.setText("");
            }else {
                RegisterLabel.setText("Falha na Atualização");
                stmt.close();
            }
        }
    }
    public void deleteOnAction(ActionEvent event) throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:unifaesp.db");
        PreparedStatement stmt;
        String delete = "Delete from users where Id_user = ?";
        stmt = connection.prepareStatement(delete);
        int id = Integer.parseInt(pesquisaField.getText());
        stmt.setInt(1, id);
        stmt.execute();
            if(stmt.execute() == false) {
                RegisterLabel.setText("Registro Excluido Sucesso");
                pesquisaField.setText("");
                NameField.setText("");
                UserField.setText("");
                PasswordField.setText("");
                CursField.setText("");
            }else {
                RegisterLabel.setText("Falha ao Excluir");
                stmt.close();
            }
        }


    public void LoginPagOnAction(ActionEvent event){
        try{
            Stage stage =(Stage) SairButton.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520,420));
            registerStage.show();

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void limpaOnAction(ActionEvent event) {

            RegisterLabel.setText("");
            pesquisaField.setText("");
            NameField.setText("");
            UserField.setText("");
            PasswordField.setText("");
            CursField.setText("");
    }
}
