package com.example.biitalumnidirectory;

public class SJE_Detail {
    public String SJE_Detail_Id;
    public String Title;
    public String Venue;
    public String Starting_Date;
    public String Ending_Date;
    public String Time;
    public String Description;
    public String SJE_Type;
    public String Creator_CNIC;
    public String Creator_Type;


    public SJE_Detail(String SJE_Detail_Id, String title, String venue, String starting_Date, String ending_Date, String time, String description, String SJE_Type, String creator_CNIC, String creator_Type) {
        this.SJE_Detail_Id = SJE_Detail_Id;
        Title = title;
        Venue = venue;
        Starting_Date = starting_Date;
        Ending_Date = ending_Date;
        Time = time;
        Description = description;
        this.SJE_Type = SJE_Type;
        Creator_CNIC = creator_CNIC;
        Creator_Type = creator_Type;
    }
}
