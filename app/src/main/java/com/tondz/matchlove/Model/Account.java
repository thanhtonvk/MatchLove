package com.tondz.matchlove.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Account {
    private String id, fullName, address, dateOfBirth, avatar,sex,job,school,company;
    private List<String> images;
    private String email, passWord;
    private Location location;
    private String status;
    private boolean firstSetup,block,admin;
    private List<String>hobbies;

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getAgefrom() {
        return agefrom;
    }

    public void setAgefrom(int agefrom) {
        this.agefrom = agefrom;
    }

    public int getAgeto() {
        return ageto;
    }

    public void setAgeto(int ageto) {
        this.ageto = ageto;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    private int space;
    private int agefrom,ageto;
    private boolean male,female;
    public Account(){

    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFirstSetup() {
        return firstSetup;
    }

    public void setFirstSetup(boolean firstSetup) {
        this.firstSetup = firstSetup;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Account(String id, String fullName, String address, String dateOfBirth, String avatar, List<String> images, String email, String passWord, Location location, String status, boolean firstSetup, boolean block, boolean admin) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.images = images;
        this.email = email;
        this.passWord = passWord;
        this.location = location;
        this.status = status;
        this.firstSetup = firstSetup;
        this.block = block;
        this.admin = admin;
    }

    public Account(String email, String passWord, String fullName, String dateOfBirth){
        this.email = email;
        this.passWord = passWord;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.block = false;
        this.firstSetup = true;
        this.admin = false;
    }

}
