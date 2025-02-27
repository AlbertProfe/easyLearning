package dev.learn.services;

import dev.learn.data.LearningQuestion;
import dev.learn.services.LearningQuestionService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;


import java.util.List;

@Endpoint
@AnonymousAllowed
public class QuizEndpoint {

    private final LearningQuestionService service;

    public QuizEndpoint(LearningQuestionService service) {

        this.service = service;
    }

    public @Nonnull List<LearningQuestion> getQuestions() {

        return service.getQuestions();
    }
}