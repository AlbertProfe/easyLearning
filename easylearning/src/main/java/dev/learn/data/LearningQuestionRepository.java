package dev.learn.data;

import dev.learn.data.LearningQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningQuestionRepository extends JpaRepository<LearningQuestion, Long> {
}
