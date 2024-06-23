package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.auth;

public class ResetPasswordRequest {

    private String email;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
