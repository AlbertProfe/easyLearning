import { useEffect, useState } from 'react';
import { Button } from '@vaadin/react-components/Button.js';
import { RadioButton } from '@vaadin/react-components/RadioButton.js';
import { RadioGroup } from '@vaadin/react-components/RadioGroup.js';
import { Notification } from '@vaadin/react-components/Notification.js';
import { QuizEndpoint } from 'Frontend/generated/endpoints';
import  LearningQuestion  from 'Frontend/generated/dev/learn/data/LearningQuestion';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';

export const config: ViewConfig = {
  menu: { order: 4, icon: 'line-awesome/svg/file.svg' },
  title: 'QuizLearning',
  loginRequired: true,
};

export default function QuizLearningView() {
  const [questions, setQuestions] = useState<LearningQuestion[]>([]);
  const [selectedAnswers, setSelectedAnswers] = useState<{ [key: number]: string }>({});
  const [showResults, setShowResults] = useState(false);
  const [correctAnswers, setCorrectAnswers] = useState(0);
  const [unanswered, setUnanswered] = useState(0);
  const [incorrectAnswers, setIncorrectAnswers] = useState(0);

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        const data = await QuizEndpoint.getQuestions();
        setQuestions(data);
      } catch (error) {
        Notification.show('Failed to load questions. Please try again later.', {
          position: 'middle',
          theme: 'error',
        });
      }
    };

    fetchQuestions();
  }, []);

  const handleAnswerChange = (questionIndex: number, answer: string) => {
    setSelectedAnswers((prev) => ({ ...prev, [questionIndex]: answer }));
  };

  const handleSubmit = () => {
    let correct = 0;
    let unansweredCount = 0;
    let incorrect = 0;

    questions.forEach((question, index) => {
      const selectedAnswer = selectedAnswers[index];
      const correctAnswer = getAnswerList(question)[question.solution];

      if (!selectedAnswer) {
        unansweredCount++;
      } else if (selectedAnswer === correctAnswer) {
        correct++;
      } else {
        incorrect++;
      }
    });

    setCorrectAnswers(correct);
    setUnanswered(unansweredCount);
    setIncorrectAnswers(incorrect);
    setShowResults(true);
  };

  const handleRestart = () => {
    setSelectedAnswers({});
    setShowResults(false);
    setCorrectAnswers(0);
    setUnanswered(0);
    setIncorrectAnswers(0);
  };

  const getAnswerList = (question: LearningQuestion) => {
    const answers = [
      question.answer1,
      question.answer2,
      question.answer3,
      question.answer4,
      question.answer5,
      question.answer6,
    ];
    return answers.filter((answer) => answer && answer.trim() !== "");
  };

  if (showResults) {
    return (

      <div className="flex flex-col h-full gap-m p-m">
        <h2 className="text-2xl font-bold">Correction</h2>
        <p>You did not answer {unanswered} question(s).</p>
        <p>You got {correctAnswers} out of {questions.length} correct!</p>

        {questions.map((question, index) => {
          const selectedAnswer = selectedAnswers[index];
          const correctAnswer = getAnswerList(question)[question.solution];
          const isCorrect = selectedAnswer === correctAnswer;

          return (
            <div key={index} className="flex flex-col gap-s">
              <p><strong>Question #{index + 1}:</strong> {question.question}</p>
              <div>
                Your answer: {selectedAnswer || "Not answered"}<br />
                Correct answer: {correctAnswer}<br />
                <span className={isCorrect ? "text-success" : "text-error"}>
                  {isCorrect ? "Correct" : "Incorrect"}
                </span>
              </div>
              {question.explanation && (
                <p>
                  <strong>Explanation:</strong> {question.explanation}
                </p>
              )}
              {index < questions.length - 1 && <hr className="w-full" />}
            </div>
          );
        })}

        <div className="flex justify-center mt-l">
            <Button theme="primary" onClick={handleRestart}>Start Another Quiz</Button>
        </div>
      </div>
    );
  }

  return (
    <div className="quiz-container p-m">
      <h1 className="text-3xl font-bold mb-l text-center">EasyLearning Quiz</h1>

      <div className="questions-container">
        {questions.map((question, index) => (
          <div key={question.id || index} className="question-box mb-l p-m border rounded">
            <h3 className="text-xl font-semibold mb-m">Question #{index + 1}</h3>
            <p className="mb-m">{question.question}</p>
            <p className="text-secondary mb-s">Choose your answer:</p>

            <RadioGroup
              value={selectedAnswers[index] || ""}
              onValueChanged={(e) => handleAnswerChange(index, e.detail.value)}
            >
              {getAnswerList(question).map((answer, i) => (
                <div key={i} style={{ display: 'flex', width: '100%', marginBottom: '10px' }}>
                  <RadioButton
                    value={answer}
                    label={`${String.fromCharCode(97 + i)}. ${answer}`}
                    name={`question-${index}`}
                    style={{ width: '100%' }}
                  />
                </div>
              ))}
            </RadioGroup>
          </div>
        ))}
      </div>

      <div className="flex justify-center mt-l">
        <Button theme="primary" onClick={handleSubmit}>Submit Answers</Button>
      </div>
    </div>
  );

}