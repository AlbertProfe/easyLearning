package dev.learn.services;


import dev.learn.data.LearningQuestion;
import dev.learn.data.LearningQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LearningQuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuizEndpoint.class);
    private final LearningQuestionRepository repository;

    public LearningQuestionService(LearningQuestionRepository repository) {
        this.repository = repository;
    }

    public List<LearningQuestion> getQuestions() {
        try {
            logger.debug("Fetching all learning questions");
            List<LearningQuestion> questions = repository.findAll();
            logger.debug("Fetched {} questions", questions.size());
            return questions;
        } catch (Exception e) {
            logger.error("Failed to fetch questions", e);
            throw new RuntimeException("Error fetching quiz questions: " + e.getMessage(), e);
        }
    }
}