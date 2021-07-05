package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.stage.StageStyle;
import model.Users;

import java.util.ArrayList;
import java.sql.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController  implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button RegisterButton;
    @FXML
    private Button LoginButton;
    @FXML
    private Label LoginmenssagemLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView LockImageView;
    @FXML
    private TextField usuariofield;
    @FXML
    private PasswordField senhafield;


    @Override

    public void initialize (URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("imagensfx/login.jpg");
        Image brandingImage = new Image (brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File LockFile = new File("imagensfx/cadicon.png");
        Image LockImage = new Image (LockFile.toURI().toString());
        LockImageView.setImage(LockImage);
    }


    public void LoginButtonOnAction(ActionEvent event)throws SQLException{
        if(usuariofield.getText().isBlank() == false && senhafield.getText().isBlank() == false){
            LoginValidate();
        }else {
            LoginmenssagemLabel.setText("Preencha os Campos");
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage =(Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void LoginValidate() throws SQLException{
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:unifaesp.db");
        Statement stmt;
        ResultSet rst;
        stmt = connection.createStatement();
        rst = stmt.executeQuery("select *from users");
        ArrayList<Users> listUsers = new ArrayList<Users>();
        //Pegar os itens do resultset e inserir na lista
        while(rst.next()){
            Users u = new Users();
            u.setId(rst.getInt("id_user"));
            u.setName(rst.getString("name"));
            u.setLogin(rst.getString("user"));
            u.setPassword(rst.getString("password"));
            listUsers.add(u);
        }
        rst.close();
        connection.close();
        //login
        String login = usuariofield.getText();
        String password = senhafield.getText();
        //validar login
        for (Users u : listUsers){
            if(u.getLogin().equals(login) && u.getPassword().equals(password)){
                    LoginPagOnAction();
                    break;
            }else{
                System.out.println("Login Incorreto");
                LoginmenssagemLabel.setText("Login Incorreto");
            }
        }
    }
    public void LoginPagOnAction(){
        try{
            Stage stage =(Stage) LoginButton.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520,420));
            registerStage.show();

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void RegisterPagOnAction(ActionEvent event){
        try{
            Stage stage =(Stage) RegisterButton.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520,477));
            registerStage.show();

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
