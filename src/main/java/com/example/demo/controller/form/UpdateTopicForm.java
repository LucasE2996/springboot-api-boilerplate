package com.example.demo.controller.form;

import com.example.demo.modelo.Topic;
import com.example.demo.repository.TopicRepository;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTopicForm {

    @NotNull @NotEmpty @Length(min = 5)
    private String title;

    @NotNull @NotEmpty @Length(min = 5)
    private String message;

    public Topic update(TopicRepository repository, Long topicId) {
        Topic topic = repository.getOne(topicId);

        topic.setMessage(message);
        topic.setTitle(title);

        // we do not need to call any repository method for saving it to the DB because of @Transactional annotation
        return topic;
    }

}
