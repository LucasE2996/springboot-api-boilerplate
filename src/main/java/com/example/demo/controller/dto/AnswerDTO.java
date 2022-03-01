package com.example.demo.controller.dto;

import com.example.demo.model.Answer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerDTO {

    private Long id;
    private String message;
    private LocalDateTime creationDate;
    private String authorName;

    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.message = answer.getMessage();
        this.creationDate = answer.getCreationDate();
        this.authorName = answer.getAuthor().getName();
    }
}
