package com.deportivo.DTO;

public class LoginResponse {

    private String token;
    private String tipo;
    private long expiraEn;
    private String rol;

    public LoginResponse(
            String token,
            String tipo,
            long expiraEn,
            String rol) {

        this.token = token;
        this.tipo = tipo;
        this.expiraEn = expiraEn;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public long getExpiraEn() {
        return expiraEn;
    }

    public String getRol() {
        return rol;
    }
}
