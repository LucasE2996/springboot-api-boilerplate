package com.example.demo.controller.form;

import com.example.demo.model.Course;
import com.example.demo.model.Topic;
import com.example.demo.repository.CourseRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TopicForm {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String title;

    @NotNull
    @NotEmpty
    @Length(min = 10)
    private String message;

    @NotNull
    @NotEmpty
    private String courseName;

    public Topic convert(CourseRepository courseRepository) {
        final Course course = courseRepository.findByName(this.courseName);

        return new Topic(title, message, course);
    }
}
