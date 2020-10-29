package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormError {

    private String field;
    private String error;

}
