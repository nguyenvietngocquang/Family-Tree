package com.example.familytree;

import java.io.Serializable;
import java.util.Calendar;

public class Member implements Serializable {
    private String name;
    private int day, month, year;
    private int age;
    private boolean male;
    private Member father;
    private Member spouse;

    public static String convertName(String name) {
        if (name.equals(""))
            return "";
        name = name.toLowerCase();
        String[] list = name.split(" ");
        String result = new String("");
        for (String temp : list) {
            temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
            result += temp + " ";
        }
        return result.trim();
    }

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, boolean male) {
        this.name = name;
        this.male = male;
    }

    public Member(Member member) {
        this.name = member.name;
        this.day = member.day;
        this.month = member.month;
        this.year = member.year;
        this.male = member.male;
        this.age = member.age;
        this.father = member.father;
        this.spouse = member.spouse;
    }

    public Member(String name, int day, int month, int year, boolean male) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.male = male;
        this.age = Calendar.getInstance().get(Calendar.YEAR) - this.year;
    }

    public Member(Member father, String name, int day, int month, int year, boolean male) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.male = male;
        this.father = father;
        this.age = Calendar.getInstance().get(Calendar.YEAR) - this.year;
    }

    public Member(String name, int day, int month, int year, boolean male, Member spouse) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.male = male;
        this.spouse = spouse;
        this.age = Calendar.getInstance().get(Calendar.YEAR) - this.year;
    }

    public Member(String name, int day, int month, int year, boolean male, Member father, Member spouse) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.male = male;
        this.father = father;
        this.spouse = spouse;
        this.age = Calendar.getInstance().get(Calendar.YEAR) - this.year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Member getFather() {
        return father;
    }

    public void setFather(Member father) {
        this.father = father;
    }

    public Member getSpouse() {
        return spouse;
    }

    public void setSpouse(Member spouse) {
        this.spouse = spouse;
    }
}
