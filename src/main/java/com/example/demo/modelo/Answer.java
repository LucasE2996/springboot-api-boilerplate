package com.example.demo.modelo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Topic topic;

	@ManyToOne
	private User author;

	private Boolean hasSolution = false;
	private LocalDateTime creationDate = LocalDateTime.now();
	private String message;

}
