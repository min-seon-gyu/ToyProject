package com.WebProject.controller;

import com.WebProject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginData {

    private Member Member;
    private String Key;
}
