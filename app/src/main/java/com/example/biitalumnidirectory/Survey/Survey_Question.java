package com.example.biitalumnidirectory.Survey;

public class Survey_Question {

    public String Question_No;
    public String Question;
    public String Option1;
    public String Option2;
    public String Option3;
    public String Option4;
    public String Option5;
    public String Answer;

    public Survey_Question(String question_No, String question, String option1, String option2, String option3, String option4, String option5, String answer) {
        Question_No = question_No;
        Question = question;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        Option4 = option4;
        Option5 = option5;
        Answer = answer;
    }

    public Survey_Question() {
    }
}
