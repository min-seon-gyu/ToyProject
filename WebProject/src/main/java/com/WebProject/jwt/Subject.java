package com.WebProject.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@AllArgsConstructor
@Getter
public class Subject {

    private static final String ATK = "ATK";
    private static final String RTK = "RTK";

    private  String email;

    private  String name;

    private  String type;

    @CreatedDate
    private Long date;

    public static Subject atk(String email, String name, Long date) {
        return new Subject(email, name, ATK, date);

    }

    public static Subject rtk(String email, String name, Long date) {

        return new Subject(email, name, RTK, date);
    }
}
