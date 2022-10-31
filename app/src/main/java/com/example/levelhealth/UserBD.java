package com.example.levelhealth;

import java.text.DateFormat;

public class UserBD {
    public String id, username, surname, birth, email, type;

    public UserBD() {
    }

    public UserBD(String id, String username, String surname, String birth, String email, String type) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.birth = birth;
        this.type = type;
    }
}
