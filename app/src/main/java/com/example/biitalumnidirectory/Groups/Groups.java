package com.example.biitalumnidirectory.Groups;

public class Groups {
    public String Groups_Id;
    public String CNIC;
    public String Group_Name;
    public String Status;

    public Groups(String groups_Id, String CNIC, String group_Name, String status) {
        Groups_Id = groups_Id;
        this.CNIC = CNIC;
        Group_Name = group_Name;
        Status = status;
    }
}
