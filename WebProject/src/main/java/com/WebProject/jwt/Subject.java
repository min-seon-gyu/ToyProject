package com.WebProject.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    private  Long accountId;

    private  String email;

    private  String name;

    private  String type;

    public static Subject atk(Long accountId, String email, String name) {
        return new Subject(accountId, email, name, "ATK");
    }

    public static Subject rtk(Long accountId, String email, String name) {
        return new Subject(accountId, email, name, "RTK");
    }
}
