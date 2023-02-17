package com.WebProject.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    private  String email;

    private  String name;

    private  String type;

    @CreatedDate
    private Long date;

    public static Subject atk(String email, String name, Long date) {
        return new Subject(email, name, "ATK", date);

    }

    public static Subject rtk(String email, String name, Long date) {

        return new Subject(email, name, "RTK", date);
    }
}
