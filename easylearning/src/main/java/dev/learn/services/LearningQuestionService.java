package dev.learn.services;


import dev.learn.data.LearningQuestion;
import dev.learn.data.LearningQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningQuestionService {

    private final LearningQuestionRepository repository;

    public LearningQuestionService(LearningQuestionRepository repository) {
        this.repository = repository;
    }

    public List<LearningQuestion> getQuestions() {
        return repository.findAll();
    }
}