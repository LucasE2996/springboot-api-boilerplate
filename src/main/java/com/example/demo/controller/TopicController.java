package com.example.demo.controller;

import com.example.demo.controller.dto.TopicDetailDTO;
import com.example.demo.controller.dto.TopicForm;
import com.example.demo.controller.dto.TopicDTO;
import com.example.demo.controller.dto.UpdateTopicFormDTO;
import com.example.demo.modelo.Topic;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<TopicDTO> listAllTopics(String courseName) {
        List<Topic> topics;
        if (courseName == null)
            topics = topicRepository.findAll();
        else
            topics = topicRepository.findByCourseName(courseName);
        return TopicDTO.convert(topics);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicDTO> createTopic(
            @RequestBody @Valid TopicForm topicForm,
            UriComponentsBuilder uriBuilder
    ) {
        Topic topic = topicForm.convert(courseRepository);
        topicRepository.save(topic);

        URI uri = uriBuilder.path("/topics")
                .buildAndExpand(topic.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new TopicDTO(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailDTO> detail(@PathVariable Long id) {
        // validate if the entity exists in the DB
        final Optional<Topic> topic = topicRepository.findById(id);

        return topic.map(value -> ResponseEntity.ok(new TopicDetailDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicFormDTO form) {
        // validate if the entity exists in the DB
        final Optional<Topic> optional = topicRepository.findById(id);

        return optional.map(value -> {
            final Topic topic = form.update(topicRepository, id);
            return ResponseEntity.ok(new TopicDTO(topic));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        // validate if the entity exists in the DB
        Optional<Topic> optional = topicRepository.findById(id);

        return optional.map(value -> {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
