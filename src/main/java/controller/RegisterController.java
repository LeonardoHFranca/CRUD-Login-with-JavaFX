package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RegisterController implements Initializable  {
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
    private ImageView LockImageView;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        File LockFile = new File("imagensfx/cadicon.png");
        Image LockImage = new Image (LockFile.toURI().toString());
        LockImageView.setImage(LockImage);
    }

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

    public void LoginPagOnAction(ActionEvent event){
        try{
            Stage stage =(Stage) BackButton.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600,400));
            registerStage.show();

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}
