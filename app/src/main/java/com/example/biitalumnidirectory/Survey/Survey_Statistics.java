package com.example.biitalumnidirectory.Survey;

public class Survey_Statistics {
    public String Question_No;
    public String Question;
    public String Option1;
    public String Option2;
    public String Option3;
    public String Option4;
    public String Option5;
    public String Total_Option1;
    public String Total_Option2;
    public String Total_Option3;
    public String Total_Option4;
    public String Total_Option5;

    public Survey_Statistics(String question_No, String question, String option1, String option2, String option3, String option4, String option5, String total_Option1, String total_Option2, String total_Option3, String total_Option4, String total_Option5) {
        Question_No = question_No;
        Question = question;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        Option4 = option4;
        Option5 = option5;
        Total_Option1 = total_Option1;
        Total_Option2 = total_Option2;
        Total_Option3 = total_Option3;
        Total_Option4 = total_Option4;
        Total_Option5 = total_Option5;
    }

    public Survey_Statistics() {
    }
}
