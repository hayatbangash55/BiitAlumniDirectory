package com.example.biitalumnidirectory;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.biitalumnidirectory.Login_Classes.User_RegistrationData;

public class SharedReference {

    SharedPreferences ShredRef;

    public SharedReference(Context context) {
        ShredRef = context.getSharedPreferences("myRef", Context.MODE_PRIVATE);
    }

//Login

    public void save_RememberMe(Boolean b) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putBoolean("RememberMe", b);
        editor.commit();
    }

    public Boolean get_RememberMe() {
        Boolean b = ShredRef.getBoolean("RememberMe", false);
        return b;
    }

    //user currently Login
    public void save_UserType(String type) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("login_Type", type);
        editor.commit();
    }

    public String get_UserType() {
        String type = ShredRef.getString("login_Type", "No Type");
        return type;
    }

    public void save_UserLoginCNIC(String cnic) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("login_CNIC", cnic);
        editor.commit();
    }

    public String get_LoginCNIC() {
        String cnic = ShredRef.getString("login_CNIC", "No Cnic");
        return cnic;
    }


    public void save_LoadUserDataByCNIC(String cnic) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("Current_CNIC", cnic);
        editor.commit();
    }

    public String get_LoadUserDataByCNIC() {
        String cnic = ShredRef.getString("Current_CNIC", "No Cnic");
        return cnic;
    }


    //Registration

    //Email Verification
    public void save_UserRegistrationData(User_RegistrationData User2) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("Reg_Email", User2.Email);
        editor.putString("Reg_CNIC", User2.CNIC);
        editor.putString("Reg_Password", User2.Password);
        editor.commit();
    }


    public User_RegistrationData get_UserRegistrationData() {
        String Email = ShredRef.getString("Reg_Email", "No userName");
        String cnic = ShredRef.getString("Reg_CNIC", "No userName");
        String Password = ShredRef.getString("Reg_Password", "No password");
        return new User_RegistrationData(cnic, Email, Password);
    }

    //status that user reached here
    public void save_registrationStatus(Boolean b) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putBoolean("RegistrationStatus", b);
        editor.commit();
    }

    public Boolean get_registrationStatus() {
        Boolean b = ShredRef.getBoolean("RegistrationStatus", false);
        return b;
    }


    //Edit Profile
    public void save_EditProfile(Boolean value) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putBoolean("Edit_Check", value);
        editor.commit();
    }

    public Boolean get_EditProfile() {
        Boolean value = ShredRef.getBoolean("Edit_Check", false);
        return value;
    }

    public void save_Image(String img) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("Image", img);
        editor.commit();
    }

    public String get_Image() {
        String image = ShredRef.getString("Image", "No Image");
        return image;
    }
}

