package com.example.biitalumnidirectory.MyProfile;

public class PublicationInfo {
    public String Stu_Pub_Id;
    public String Title;
    public String Publisher;
    public String Publication_Date;
    public String Publication_URL;
    public String Description;

    public PublicationInfo(String stu_Pub_Id, String title, String publisher, String publication_Date, String publication_URL, String description) {
        Stu_Pub_Id = stu_Pub_Id;
        Title = title;
        Publisher = publisher;
        Publication_Date = publication_Date;
        Publication_URL = publication_URL;
        Description = description;
    }
}
