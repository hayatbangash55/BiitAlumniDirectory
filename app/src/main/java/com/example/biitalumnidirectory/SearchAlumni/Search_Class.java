package com.example.biitalumnidirectory.SearchAlumni;

public class Search_Class {
    public String Arid_No;
    public String Name;
    public String Dicipline;
    public String Section;
    public String Semester;
    public String CNIC;
    public String NewImage;
    public String OldImage;
    public String Status;

    public Search_Class(String arid_No, String name, String dicipline, String section, String semester, String CNIC, String newImage, String oldImage, String status) {
        Arid_No = arid_No;
        Name = name;
        Dicipline = dicipline;
        Section = section;
        Semester = semester;
        this.CNIC = CNIC;
        NewImage = newImage;
        OldImage = oldImage;
        Status = status;
    }
}
