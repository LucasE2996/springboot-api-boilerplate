package com.example.demo.controller;

import com.example.demo.controller.dto.TopicDTO;
import com.example.demo.controller.dto.TopicDetailDTO;
import com.example.demo.controller.form.TopicForm;
import com.example.demo.controller.form.UpdateTopicForm;
import com.example.demo.model.Topic;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    @Cacheable(value = "topicList")
    public Page<TopicDTO> listAllTopics(
            @RequestParam(required = false) String courseName,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ) {
        Page<Topic> topics;
        if (courseName == null)
            topics = topicRepository.findAll(pageable);
        else
            topics = topicRepository.findByCourseName(courseName, pageable);
        return TopicDTO.convert(topics);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "topicList", allEntries = true)
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
    @CacheEvict(value = "topicList", allEntries = true)
    public ResponseEntity<TopicDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicForm form) {
        // validate if the entity exists in the DB
        final Optional<Topic> optional = topicRepository.findById(id);

        return optional.map(value -> {
            final Topic topic = form.update(topicRepository, id);
            return ResponseEntity.ok(new TopicDTO(topic));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "topicList", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        // validate if the entity exists in the DB
        Optional<Topic> optional = topicRepository.findById(id);

        return optional.map(value -> {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
