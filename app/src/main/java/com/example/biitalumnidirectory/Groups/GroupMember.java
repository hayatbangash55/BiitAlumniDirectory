package com.example.biitalumnidirectory.Groups;

public class GroupMember {
    public String GroupMember_CNIC;
    public String Arid_No;
    public String Name;
    public String Dicipline;
    public String Section;
    public String Semester;
    public String NewImage;
    public String OldImage;
    public String Status;

    public GroupMember(String groupMember_CNIC, String arid_No, String name, String dicipline, String section, String semester, String newImage, String oldImage, String status) {
        GroupMember_CNIC = groupMember_CNIC;
        Arid_No = arid_No;
        Name = name;
        Dicipline = dicipline;
        Section = section;
        Semester = semester;
        NewImage = newImage;
        OldImage = oldImage;
        Status = status;
    }
}
