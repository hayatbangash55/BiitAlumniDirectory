package com.example.biitalumnidirectory.Groups;

public class Select_Groups {
    public String Groups_Id;
    public String CNIC;
    public String Group_Name;
    public boolean isSelected;

    public Select_Groups(String groups_Id, String CNIC, String group_Name, boolean isSelected) {
        Groups_Id = groups_Id;
        this.CNIC = CNIC;
        Group_Name = group_Name;
        this.isSelected = isSelected;
    }
}
