package com.google.firebase.codelab.friendlychat.entity;

import java.util.Objects;

public class UserToList {

    private String id;
    private String name;
    private String photo;

    public UserToList(String id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public UserToList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserToList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToList that = (UserToList) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, photo);
    }

    @Override
    public String toString() {
        return "UserToList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
