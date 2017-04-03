package com.cjx.nexwork.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xavi on 08/02/2017.
 */

public class User implements Serializable {
    Long id;
    String login;
    String password;
    String firstName;
    String lastName;
    String email;
    String imagen;
    String web_personal;
    String facebook;
    String twitter;
    String skype;
    String github;
    String carta_presentacion;
    String ciudad;

    public User(Long id, String password, String login, String firstName, String lastName, String email, String imagen, String facebook, String twitter, String skype, String github, String carta_presentacion, String web_personal) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imagen = imagen;
        this.facebook = facebook;
        this.twitter = twitter;
        this.skype = skype;
        this.github = github;
        this.carta_presentacion = carta_presentacion;
        this.web_personal = web_personal;
    }

    public User(String login, String password, String email, String firstName, String lastName){
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public String getWeb_personal() {
        return web_personal;
    }
    public void setWeb_personal(String web_personal) {
        this.web_personal = web_personal;
    }
    public String getFacebook() {
        return facebook;
    }
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
    public String getTwitter() {
        return twitter;
    }
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
    public String getSkype() {
        return skype;
    }
    public void setSkype(String skype) {
        this.skype = skype;
    }
    public String getGithub() {
        return github;
    }
    public void setGithub(String github) {
        this.github = github;
    }
    public String getCarta_presentacion() {
        return carta_presentacion;
    }
    public void setCarta_presentacion(String carta_presentacion) {
        this.carta_presentacion = carta_presentacion;
    }
    public String getCiudad() {
        return ciudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (imagen != null ? !imagen.equals(user.imagen) : user.imagen != null) return false;
        if (web_personal != null ? !web_personal.equals(user.web_personal) : user.web_personal != null)
            return false;
        if (facebook != null ? !facebook.equals(user.facebook) : user.facebook != null)
            return false;
        if (twitter != null ? !twitter.equals(user.twitter) : user.twitter != null) return false;
        if (skype != null ? !skype.equals(user.skype) : user.skype != null) return false;
        if (github != null ? !github.equals(user.github) : user.github != null) return false;
        if (carta_presentacion != null ? !carta_presentacion.equals(user.carta_presentacion) : user.carta_presentacion != null)
            return false;
        return ciudad != null ? ciudad.equals(user.ciudad) : user.ciudad == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (imagen != null ? imagen.hashCode() : 0);
        result = 31 * result + (web_personal != null ? web_personal.hashCode() : 0);
        result = 31 * result + (facebook != null ? facebook.hashCode() : 0);
        result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
        result = 31 * result + (skype != null ? skype.hashCode() : 0);
        result = 31 * result + (github != null ? github.hashCode() : 0);
        result = 31 * result + (carta_presentacion != null ? carta_presentacion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "carta_presentacion='" + carta_presentacion + '\'' +
                ", id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imagen='" + imagen + '\'' +
                ", web_personal='" + web_personal + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                ", skype='" + skype + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}
