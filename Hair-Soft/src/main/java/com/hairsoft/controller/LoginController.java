package com.hairsoft.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.hairsoft.method.Validation;
import com.hairsoft.entity.Usuario;
import com.hairsoft.hairsoft.LoginApp;
import com.hairsoft.hairsoft.MainScreenApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    public ArrayList<Usuario> usuarios = new ArrayList<>();

    public void initialize(URL url, ResourceBundle rb){
        usuarios.add(new Usuario(1, "Thy", "Thy@gmail.com", "Thy123"));
    }

	//public static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
    @FXML
    public AnchorPane paneLog, paneReg;

    @FXML
    public TextField txfRegUsuario, txfRegEmail, txfEmail;

    @FXML
    public PasswordField txfSenha, txfRegSenha;

    @FXML
    public Button btnCancel, btnConfirm, btnRegister, btnLogin;
    
    @FXML
    public void btnCancel_click(ActionEvent event) {
    	register_off();
    }

    @FXML
    public void btnConfirm_click(ActionEvent event) {
        inserir();
    }

    public void inserir(){
        Alert alert;
        try{
            String nome, email, senha;
            nome = txfRegUsuario.getText();
            email = txfRegEmail.getText();
            senha = txfRegSenha.getText();

            if(nome.isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registro incorreto!");
                alert.setHeaderText("Por favor insira um usuario valido!!");
                alert.showAndWait();
            }else if(Usuario.equalEmail(usuarios , email)){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registro incorreto!");
                alert.setHeaderText("Ja existe um usuario usando este Email!!");
                alert.showAndWait();
            }else if(!Validation.isValidEmail(email)){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registro incorreto!");
                alert.setHeaderText("Por favor insira um email valido!!");
                alert.showAndWait();
            }else if(!Validation.isValidSenha(senha)){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registro incorreto!");
                alert.setHeaderText("Senha precisa ter no maximo 8 caracteres!!");
                alert.showAndWait();
            }else{
                usuarios.add(new Usuario(Usuario.gerarId(usuarios),nome, email, senha ));
                register_off();
            }
        }catch(Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ops");
            alert.setHeaderText("A operação não pode ser realizada.");
            alert.setContentText("Verifique se digitou corretamente!!");
            alert.showAndWait();
        }
    }

    public void logar(){
        Alert alert;
        try {
            String email, senha;
            email = txfEmail.getText();
            senha = txfSenha.getText();

            for(Usuario usuario: usuarios) {
                if (usuario.email.equals(email) && usuario.senha.equals(senha)) {
                    callScreen(usuario.email, usuario.usuario);
                    LoginApp.getStage().close();
                    return;
                }
            }

            throw new IOException();
        } catch (IOException var4) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ops");
            alert.setHeaderText("A operação não pode ser realizada.");
            alert.setContentText("Digite um numero para pesquisar!!");
            alert.showAndWait();
        } catch (Exception var5) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ops");
            alert.setHeaderText("A operação não pode ser realizada.");
            alert.setContentText("Verifique se digitou corretamente!!");
            alert.showAndWait();
        }
    }

    public void callScreen(String Email, String Nome){
        Alert alert;
        MainScreenApp screenApp = new MainScreenApp();
        try{
            MainScreenApp.usuariosCallBack(usuarios, Nome, Email);
            screenApp.start(new Stage());
        }catch (Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro na execução da instancia.");
            alert.setContentText("Contacte o supporte para lhe auxiliar!!");
            alert.showAndWait();
        }
    }


    @FXML
    void btnLogin_click(ActionEvent event) {
        logar();
    }

    @FXML
    void btnRegister_click(ActionEvent event) {
        register_on();
    }
    
    public void register_on() {
        paneLog.setVisible(false);
        paneReg.setVisible(true);

        txfEmail.clear();
        txfSenha.clear();

        btnLogin.setVisible(false);
        btnRegister.setVisible(false);
    }

    public void register_off(){
        paneLog.setVisible(true);
        paneReg.setVisible(false);

        txfRegUsuario.clear();
        txfRegEmail.clear();
        txfRegSenha.clear();

        btnLogin.setVisible(true);
        btnRegister.setVisible(true);
    }

}


