package com.example.biitalumnidirectory.Alumni_Notification;

public class Alumni_Notification {
    public String SJE_Detail_Id;
    public String Title;
    public String Posted_SJE_Id;
    public String Posted_Date;
    public String Posted_Time;
    public String Seen_Status;
    public String SJE_Type;


    public Alumni_Notification(String SJE_Detail_Id, String title, String posted_SJE_Id, String posted_Date, String posted_Time, String seen_Status, String SJE_Type) {
        this.SJE_Detail_Id = SJE_Detail_Id;
        Title = title;
        Posted_SJE_Id = posted_SJE_Id;
        Posted_Date = posted_Date;
        Posted_Time = posted_Time;
        Seen_Status = seen_Status;
        this.SJE_Type = SJE_Type;
    }
}
