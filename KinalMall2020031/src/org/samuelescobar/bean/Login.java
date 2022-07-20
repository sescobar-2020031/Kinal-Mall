package org.samuelescobar.bean;

public class Login {
    public String usuarioMaster;
    public String passwordLogin;

    public Login() {
    }

    public Login(String usuarioMaster, String passwordLogin) {
        this.usuarioMaster = usuarioMaster;
        this.passwordLogin = passwordLogin;
    }

    public String getUsuarioMaster() {
        return usuarioMaster;
    }

    public void setUsuarioMaster(String usuarioMaster) {
        this.usuarioMaster = usuarioMaster;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }
    
    
    
    
}
