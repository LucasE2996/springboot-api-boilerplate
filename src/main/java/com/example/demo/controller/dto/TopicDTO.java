package com.example.demo.controller.dto;

import com.example.demo.model.Topic;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
public class TopicDTO {

    private Long id;
    private String title;
    private String message;
    private LocalDateTime creationDate;

    public TopicDTO(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.creationDate = topic.getCreationDate();
    }

    public static Page<TopicDTO> convert(Page<Topic> topics) {
        return topics.map(TopicDTO::new);
    }
}
