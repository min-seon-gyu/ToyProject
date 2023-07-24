package com.WebProject.Store;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Entity
public class Store{

    @Id
    @Column(nullable = false)
    private long id;

    private String name;

    private String address;

    private String tell;

    private String operating_time;

    private String type;

    private String representative_menu;

    private Double lat;

    private Double lon;

    private double score;


}
