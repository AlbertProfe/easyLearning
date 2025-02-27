package dev.learn.controller;

import dev.learn.data.LearningQuestion;
import dev.learn.data.LearningQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LearningQuestionController {
    @Autowired
    LearningQuestionRepository learningQuestionRepository;

    // Get all questions
    @GetMapping("/api/v1/questions")
    public ResponseEntity<List<LearningQuestion>> getAll() {
        List<LearningQuestion> questions = learningQuestionRepository.findAll();
        return ResponseEntity.ok(questions); // Return 200 OK with the list of questions
    }

    @GetMapping("/api/v1/questions/{id}")
    public ResponseEntity<LearningQuestion> getById(@PathVariable Long id) {
        return learningQuestionRepository.findById(id)
                .map(question -> ResponseEntity.ok(question)) // Return 200 OK with the question
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found if the question doesn't exist
    }
}