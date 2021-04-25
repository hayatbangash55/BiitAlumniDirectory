package com.example.biitalumnidirectory.Groups;

public class Add_Group_Member {
    public String Arid_No;
    public String Name;
    public String Dicipline;
    public String Section;
    public String Semester;
    public String CNIC;
    public String NewImage;
    public String OldImage;
    public boolean isChecked;

    public Add_Group_Member(String arid_No, String name, String dicipline, String section, String semester, String CNIC, String newImage, String oldImage, boolean isChecked) {
        Arid_No = arid_No;
        Name = name;
        Dicipline = dicipline;
        Section = section;
        Semester = semester;
        this.CNIC = CNIC;
        NewImage = newImage;
        OldImage = oldImage;
        this.isChecked = isChecked;
    }
}
