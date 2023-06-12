package br.com.schedulebarber.scheduleBarber.DTO;


import java.util.Objects;


public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {}


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationResponse that = (AuthenticationResponse) o;

        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
