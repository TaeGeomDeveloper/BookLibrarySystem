package VO;

public class StudentVO {
    // 학생 정보 학번, 이름, 전공, 나이
    private int stdNo;
    private String name;
    private String major;
    private int age;
    // 대출 시작일
    private int RStartDay = 1;
    private int ReturnDay;
    // 대출 가능 여부
    public boolean isPossible = false;
    // 대출 권수
    private int Count = 0;
    // 대출 횟수
    private int RentalCount = 0;
    // 연체 횟수
    private int OverdueCount = 0;

    public int getOverdueCount() {
        return OverdueCount;
    }

    public void setOverdueCount(int overdueCount) {
        OverdueCount = overdueCount;
    }

    public int getRentalCount() {
        return RentalCount;
    }

    public void setRentalCount(int rentalCount) {
        RentalCount = rentalCount;
    }

    // 대출 기간
    private int Days = 0;

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getReturnDay() {
        return ReturnDay;
    }

    public void setReturnDay(int returnDay) {
        ReturnDay = returnDay;
    }


    public StudentVO(int stdNo, String name, String major, int age) {
        this.stdNo = stdNo;
        this.name = name;
        this.major = major;
        this.age = age;
    }

    public boolean isPossible() {
        return isPossible;
    }

    public void setisPossible(boolean isPossible) {
        this.isPossible = isPossible;
    }

    public int getRStartDay() {
        return RStartDay;
    }

    public void setRStartDay(int RStartDay) {
        this.RStartDay = RStartDay;
    }

    public int getStdNo() {
        return stdNo;
    }

    public void setStdNo(int stdNo) {
        this.stdNo = stdNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
