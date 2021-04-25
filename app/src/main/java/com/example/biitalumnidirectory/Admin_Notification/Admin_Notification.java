package com.example.biitalumnidirectory.Admin_Notification;

public class Admin_Notification {
    public String SJE_Detail_Id;
    public String Posted_SJE_Id;
    public String Creator_CNIC;
    public String SJE_Type;
    public String Title;
    public String Posted_Time;
    public String Posted_Date;
    public String Student_Name;


    public Admin_Notification(String SJE_Detail_Id, String posted_SJE_Id, String creator_CNIC, String SJE_Type, String title, String posted_Time, String posted_Date, String student_Name) {
        this.SJE_Detail_Id = SJE_Detail_Id;
        Posted_SJE_Id = posted_SJE_Id;
        Creator_CNIC = creator_CNIC;
        this.SJE_Type = SJE_Type;
        Title = title;
        Posted_Time = posted_Time;
        Posted_Date = posted_Date;
        Student_Name = student_Name;
    }
}
