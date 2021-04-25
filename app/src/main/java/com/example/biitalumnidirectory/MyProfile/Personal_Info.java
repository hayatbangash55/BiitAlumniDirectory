package com.example.biitalumnidirectory.MyProfile;

public class Personal_Info {

    public String Primary_Email;
    public String City;
    public String Secondary_Email;
    public String Phone_No;
    public String Office_No;
    public String Address;
    public String Gender;
    public String Maritial_Status;
    public String DOB;
    public String Section;
    public String Discipline;
    public String Degree_Completion;
    public String Session;

    public Personal_Info(String primary_Email, String city, String secondary_Email, String phone_No, String office_No, String address, String gender, String maritial_Status, String DOB, String section, String discipline, String degree_Completion, String session) {
        Primary_Email = primary_Email;
        City = city;
        Secondary_Email = secondary_Email;
        Phone_No = phone_No;
        Office_No = office_No;
        Address = address;
        Gender = gender;
        Maritial_Status = maritial_Status;
        this.DOB = DOB;
        Section = section;
        Discipline = discipline;
        Degree_Completion = degree_Completion;
        Session = session;
    }
}

