package com.example.demo.controller.dto;

import com.example.demo.modelo.Course;
import com.example.demo.modelo.Topic;
import com.example.demo.repository.CourseRepository;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TopicForm {

    @NotNull @NotEmpty @Length(min = 5)
    private String title;

    @NotNull @NotEmpty @Length(min = 10)
    private String message;

    @NotNull @NotEmpty
    private String courseName;

    public Topic convert(CourseRepository courseRepository) {
        final Course course = courseRepository.findByName(this.courseName);

        return new Topic(title, message, course);
    }
}
