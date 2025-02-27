package dev.learn.data;

import jakarta.persistence.*;


@Entity
@Table(name = "LEARNING_QUESTIONS")
public class LearningQuestion  extends AbstractEntity{


    @Column(nullable = false, length = 1000)
    private String question;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int solution;

    @Column(length = 1000)
    private String hint;

    @Column(length = 4000)
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column
    private Framework framework;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Topic topic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubTopic subtopic;


    @Column(length = 1000)
    private String answer1;

    @Column(length = 1000)
    private String answer2;

    @Column(length = 1000)
    private String answer3;

    @Column(length = 1000)
    private String answer4;

    @Column(length = 1000)
    private String answer5;

    @Column(length = 1000)
    private String answer6;

    public enum Category {
        PROGRAMMING, DESIGN, DEVOPS, DATABASE, NETWORKING, SECURITY
    }

    public enum SubCategory {
        WEB, MOBILE
    }

    public enum Language {
        JAVA, PYTHON, JAVASCRIPT, CSHARP, GOLANG, RUST, KOTLIN, JS, TYPESCRIPT, JSX
    }

    public enum Framework {
        SPRING, DJANGO, REACT, ANGULAR, VUE, DOTNET, FLASK
    }

    public enum Topic {
        BASICS, OOP, FUNCTIONAL_PROGRAMMING, WEB_DEVELOPMENT, MOBILE_DEVELOPMENT, MACHINE_LEARNING, HOOKS
    }

    public enum SubTopic {
        FUNDAMENTAL_HOOKS
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Framework getFramework() {
        return framework;
    }

    public void setFramework(Framework framework) {
        this.framework = framework;
    }

    public SubTopic getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(SubTopic subtopic) {
        this.subtopic = subtopic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public String getAnswer6() {
        return answer6;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }
}


