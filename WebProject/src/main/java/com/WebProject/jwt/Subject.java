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

    private  Long id;

    private  String email;

    private  String name;

    private  String type;

    @CreatedDate
    private Long date;

    public static Subject atk(Long id, String email, String name, Long date) {
        return new Subject(id, email, name, "ATK", date);

    }

    public static Subject rtk(Long id, String email, String name, Long date) {
        return new Subject(id, email, name, "RTK", date);
    }
}
