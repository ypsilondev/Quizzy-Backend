package de.ypsilon.quizzy.dataset.question;

/**
 * This (boilerplate) class models different categories for questions (e.g. different topics or subjects)
 */
public class QuestionCategory {

    private String subject;
    private String name;
    private String description;

    public QuestionCategory(String subject, String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
