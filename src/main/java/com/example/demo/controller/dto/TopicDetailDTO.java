package com.example.demo.controller.dto;

import com.example.demo.model.Topic;
import com.example.demo.model.TopicStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TopicDetailDTO {

    private Long id;
    private LocalDateTime creationDate;
    private String title;
    private String message;
    private String authorName;
    private TopicStatus topicStatus;
    private List<AnswerDTO> answers;

    public TopicDetailDTO(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.authorName = topic.getAuthor().getName();
        this.topicStatus = topic.getStatus();
        this.creationDate = topic.getCreationDate();
        this.answers = new ArrayList<>();
        this.answers.addAll(topic.getAnswers().stream().map(AnswerDTO::new).collect(Collectors.toList()));
    }

}
