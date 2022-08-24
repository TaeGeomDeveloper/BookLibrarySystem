package BL;

import java.sql.SQLException;
import java.util.*;
import VO.*;
import DAO.dao;

public class Logic {
    dao dao = new dao();
    ArrayList<BookVO> Booklist = dao.selectBookData();
    ArrayList<StudentVO> StudentList = dao.selectStudentData();
    ArrayList<RentalBookVO> RentalBookList = new ArrayList<RentalBookVO>();

    public Logic() throws SQLException {
    }

    // 수동 대출장부 작성
    public void manualInsert(){
        // 대충 장부의 정보 학생의 학번, 분류 번호, 대출일, 반납예정일
        Scanner sc = new Scanner(System.in);

        System.out.println("학번을 입력해주세요 : ");
        int StdNo = sc.nextInt();
        System.out.println("책 분류 번호를 입력해주세요 : ");
        int BookNo = sc.nextInt();

        System.out.println("대출일을 입력 해주시기 바랍니다. ");
        System.out.println("");

        System.out.println("반납 예정일을 입력 해주시기 바랍니다. ");

        // 대출 장부 작성.

    }

    // 자동 500건의 생성 처리
    public void Insert() {
        int MaxDay = 0;
        Random r = new Random();
        for (int i = 0; i < 500; i++) {

            int temp = 0;
            int SN = 0;         // 학생의 번호

            while (true) {
                // 10명중 한명의 랜덤 학생의 학번
                int StudentNum = r.nextInt(10);
                // 책을 빌린적이 있는 학생 일때
                if (StudentList.get(StudentNum).isPossible()) {
                    SN = StudentNum;

                    // 모든 도서를 검색
                    for (BookVO vo : Booklist) {
                        // 책의 학생 번호와 현재 학생의 번호가 같은가?
                        if (vo.getStdNo() == StudentList.get(StudentNum).getStdNo()) {
                            // 책 반납 처리
                            vo.setisPossible(false);
                            vo.setStdNo(0); // 학생 번호 초기화
                        }
                    }
                    for (StudentVO vo : StudentList) {
                        if(MaxDay < vo.getReturnDay()) {
                            MaxDay = vo.getReturnDay();
                        }
                    }
                    for (StudentVO vo : StudentList) {
                        vo.setRStartDay(MaxDay);
                    }
                    StudentList.get(StudentNum).setisPossible(false);
                }
                // 대출한 학생이 아닌경우
                if (!StudentList.get(StudentNum).isPossible()) {
                    SN = StudentNum;
                    break;
                }
            }
            // 1 ~ 5권 중 랜덤으로 대출.
            int RN = r.nextInt(4)+1;
            // 반납일 은 랜덤 설정
            int ReturnDay = r.nextInt(10);

            // 중복 방지
            for (int j = 0; j < RN; j++) {
                int BookNum = r.nextInt(30);      // 1 ~ 30 권
                // 대출 되었는지 중복 확인
                if (!Booklist.get(BookNum).isPossible()) {
                    // 대출 장부 작성
                    RentalBookList.add(new RentalBookVO(StudentList.get(SN).getStdNo(), Booklist.get(BookNum).getBookNo(),
                            StudentList.get(SN).getRStartDay(), StudentList.get(SN).getRStartDay() + ReturnDay));
                    Booklist.get(BookNum).setCount(Booklist.get(BookNum).getCount() + 1);
                    StudentList.get(SN).setCount(StudentList.get(SN).getCount() + 1);
                    // 책을 빌린 학생 번호를 넣어줌.
                    Booklist.get(BookNum).setStdNo(StudentList.get(SN).getStdNo());
                    // 중복 처리
                    Booklist.get(BookNum).setisPossible(true);
                }
            }
            // 연체일
            int Overdue = 0;
            if (ReturnDay > 7) {
                Overdue = ReturnDay - 7;
                // 연체 횟수
                StudentList.get(SN).setOverdueCount(StudentList.get(SN).getOverdueCount() + 1);
            }

            // 대출 시작일 재설정
            StudentList.get(SN).setReturnDay(StudentList.get(SN).getRStartDay() + ReturnDay + Overdue);

            // 총 대출일
            StudentList.get(SN).setDays(StudentList.get(SN).getDays() + (StudentList.get(SN).getReturnDay() - StudentList.get(SN).getRStartDay()));
            // 대출 시작일 = 대출 시작일 + 반납일 + 연체일(0);
            StudentList.get(SN).setRStartDay(StudentList.get(SN).getReturnDay());

            // 학생 중복 처리
            StudentList.get(SN).setisPossible(true);
            StudentList.get(SN).setRentalCount(StudentList.get(SN).getRentalCount() + 1);
        }
        // 대출장부 데이터베이스 추가

        // 대출 장부 출력
        PrintRentalBook();
    }
    // 대출 장부 출력
    public void PrintRentalBook() {
        int Month_table[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        for (RentalBookVO vo : RentalBookList) {
            int Year = 2022;
            int Year2 = 2022;
            int Month = 1;
            int Month2 = 1;
            int Day = vo.getRentalDay();
            int Day2 = vo.getReturnDay();

            // 대출일 날짜 구하기
            while(Day > 365){
                Year++;
                Day -= 365;
            }
            for (int i = 0; i < 12; i++) {
                if (Day > Month_table[i]) {
                    Month++;
                    Day -= Month_table[i];
                }
            }
            // 반납일 날짜 구하기
            while (Day2 > 365){
                Year2++;
                Day2 -= 365;
            }
            for (int i = 0; i < 12; i++) {
                if (Day2 > Month_table[i]) {
                    Month2++;
                    Day2 -= Month_table[i];
                }
            }
            System.out.println("학생번호 : " + vo.getStudentNo() + " \t\t 책 번호 : " + vo.getBookNo() +
                    "\t\t대출일 : " + Year + " 년 " + Month + " 월 " + Day + " 일" + " \t\t반납일 : " + Year2 + " 년 " + Month2 + " 월 " + Day2 + " 일\n");
        }
    }

    // 사용자 이름으로 검색
    public void SearchName(String name) {

        int Month_table[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int StudentNum = -1;
        for (StudentVO vo : StudentList) {
            if (vo.getName().equals(name)) {
                StudentNum = vo.getStdNo();
            }
        }
        for (RentalBookVO vo : RentalBookList) {
            if (vo.getStudentNo() == StudentNum) {
                int Year = 2022;
                int Year2 = 2022;
                int Month = 1;
                int Month2 = 1;
                int Day = vo.getRentalDay();
                int Day2 = vo.getReturnDay();
                // 대출일 날짜 구하기
                while(Day > 365){
                    Year++;
                    Day -= 365;
                }
                for (int i = 0; i < 12; i++) {
                    if (Day > Month_table[i]) {
                        Month++;
                        Day -= Month_table[i];
                    }
                }
                // 반납일 날짜 구하기
                while (Day2 > 365){
                    Year2++;
                    Day2 -= 365;
                }
                for (int i = 0; i < 12; i++) {
                    if (Day2 > Month_table[i]) {
                        Month2++;
                        Day2 -= Month_table[i];
                    }
                }
                vo.setBookName(Booklist);
                System.out.println("학생 이름 : " + name + " \t학생번호 : " + vo.getStudentNo() + " \t책 제목 : " + vo.getBookName() +
                        "\n 대출일 : " + Year + " 년 " + Month + " 월 " + Day + " 일" + " \t\t반납일 : " + Year2 + " 년 " + Month2 + " 월 " + Day2 + " 일\n");
            }
        }
    }

    // 도서명으로 검색
    public void SearchBookName(String name) {
        int Month_table[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String BookName = name;
        int BookNo = -1;
        for (RentalBookVO vo : RentalBookList) {
            vo.setBookName(Booklist);
            vo.setStdName(StudentList);
        }
        for (RentalBookVO vo : RentalBookList) {
            if (vo.getBookName().equals(name)) {
                int Year = 2022;
                int Year2 = 2022;
                int Month = 1;
                int Month2 = 1;
                int Day = vo.getRentalDay();
                int Day2 = vo.getReturnDay();
                // 대출일 날짜 구하기
                while(Day > 365){
                    Year++;
                    Day -= 365;
                }
                for (int i = 0; i < 12; i++) {
                    if (Day > Month_table[i]) {
                        Month++;
                        Day -= Month_table[i];
                    }
                }
                // 반납일 날짜 구하기
                while (Day2 > 365){
                    Year2++;
                    Day2 -= 365;
                }
                for (int i = 0; i < 12; i++) {
                    if (Day2 > Month_table[i]) {
                        Month2++;
                        Day2 -= Month_table[i];
                    }
                }
                System.out.println("학생 이름 : " + vo.getStdName() + " \t학생번호 : " + vo.getStudentNo() + " \t책 제목 : " + name +
                        "\n 대출일 : " + Year + " 년 " + Month + " 월 " + Day + " 일" + " \t\t반납일 : " + Year2 + " 년 " + Month2 + " 월 " + Day2 + " 일\n");
            }
        }
    }

    // 대출 도서 랭킹
    public void solveQuiz1() throws SQLException {
        System.out.println("< 30권 책들의 대출 횟수 >");
        ArrayList<BookVO> BookRank = new ArrayList<BookVO>();
        for (BookVO vo : Booklist) {
            BookRank.add(vo);
        }
        Collections.sort(BookRank, ((Comparator<BookVO>) (o1, o2) -> {
            if (o1.getCount() < o2.getCount()) {
                return -1;
            } else if (o1.getCount() > o2.getCount()) {
                return 1;
            }
            return 0;
        }).reversed());
        for (BookVO vo : BookRank) {
            System.out.println("책 제목 : " + vo.getBookName() + "\n 대출 횟수 : " + vo.getCount());
        }
        System.out.println();
    }

    // 대출자 랭킹
    public void solveQuiz2() {
        System.out.println("< 10명의 사용자의 대출 권수 >");
        ArrayList<StudentVO> StudentRank = new ArrayList<StudentVO>();
        for (StudentVO vo : StudentList) {
            StudentRank.add(vo);
        }
        Collections.sort(StudentRank, ((Comparator<StudentVO>) (o1, o2) -> {
            if (o1.getCount() < o2.getCount()) {
                return -1;
            } else if (o1.getCount() > o2.getCount()) {
                return 1;
            }
            return 0;
        }).reversed());
        for (StudentVO vo : StudentRank) {
            System.out.println(" " + vo.getName() + " 의 대출 권수  : " + vo.getCount() + " \t 대출 횟수 : " + vo.getRentalCount());
        }
        System.out.println();
    }

    // 대출 기간이 가장 짧은 도서에 대한 정보
    public void solveQuiz3() {
        System.out.println("< 대출 기간이 가장 짧은 도서 >");
        ArrayList<BookVO> MinBook = new ArrayList<BookVO>();
        int min = Booklist.get(0).getCount();
        for (BookVO vo : Booklist) {
            MinBook.add(vo);
            min = Math.min(vo.getCount(), min);
        }
        for (BookVO vo : MinBook) {
            if (vo.getCount() == min) {
                System.out.println(" 대출 기간이 가장 짧은 도서 : " + vo.getBookName());
            }
        }
        System.out.println();
    }

    // 대출 반납이 가장 빠른 학생에 대한 정보
    public void solveQuiz4() {
        System.out.println("< 대출 반납이 가장 빠른 학생 >");
        double min = (double) StudentList.get(0).getDays() / (double) StudentList.get(0).getRentalCount();
        for (StudentVO vo : StudentList) {
            min = Math.min((double) vo.getDays() / (double) vo.getRentalCount(), min);
        }
        min = Math.round(min * 100) / 100.0;
        for (StudentVO vo : StudentList) {
            double num = (double) vo.getDays() / (double) vo.getRentalCount();
            num = Math.round(num * 100) / 100.0;
            if (num == min) {
                System.out.println(" 대출 반납이 가장 빠른 학생 : " + vo.getName());
            }
        }
        System.out.println();
    }

    // 대출을 가장 많이 하는 학과에 대한 정보
    public void solveQuiz5() {
        // 학과 별 대출 횟수
        int MRCount[] = {0, 0, 0, 0, 0};
        // 목탁디자인과 , 오징어심리학과 , 감귤표장학과 , e스포츠학과 , 성경표지디자인과
        System.out.println("< 대출 을 가장많이 하는 학과 >");

        for (StudentVO vo : StudentList) {
            if (vo.getMajor().equals("목탁디자인과")) {
                MRCount[0] += vo.getRentalCount();
            } else if (vo.getMajor().equals("오징어심리학과")) {
                MRCount[1] += vo.getRentalCount();
            } else if (vo.getMajor().equals("감귤표장학과")) {
                MRCount[2] += vo.getRentalCount();
            } else if (vo.getMajor().equals("e스포츠학과")) {
                MRCount[3] += vo.getRentalCount();
            } else if (vo.getMajor().equals("성경표지디자인과")) {
                MRCount[4] += vo.getRentalCount();
            }
        }
        System.out.println(" 목탁디자인과 대출 횟수 : " + MRCount[0]);
        System.out.println(" 오징어심리학과 대출 횟수 : " + MRCount[1]);
        System.out.println(" 감귤포장학과 대출 횟수 : " + MRCount[2]);
        System.out.println(" e스포츠학과 대출 횟수 : " + MRCount[3]);
        System.out.println(" 성경표지디자인과 대출 횟수 : " + MRCount[4]);

        System.out.println();
    }

    // 대출 반납이 가장 늦은 학과에 대한 정보
    public void solveQuiz6() {
        System.out.println("< 대출 반납이 가장 늦은 학과 >");
        // 학과별 대출 기간
        int MRDays[] = {0, 0, 0, 0, 0};
        // 학과 별 대출 횟수
        int MRCount[] = {0, 0, 0, 0, 0};
        // 목탁디자인과 , 오징어심리학과 , 감귤표장학과 , e스포츠학과 , 성경표지디자인과
        for (StudentVO vo : StudentList) {
            if (vo.getMajor().equals("목탁디자인과")) {
                MRCount[0] += vo.getRentalCount();
                MRDays[0] += vo.getDays();
            } else if (vo.getMajor().equals("오징어심리학과")) {
                MRCount[1] += vo.getRentalCount();
                MRDays[1] += vo.getDays();
            } else if (vo.getMajor().equals("감귤표장학과")) {
                MRCount[2] += vo.getRentalCount();
                MRDays[2] += vo.getDays();
            } else if (vo.getMajor().equals("e스포츠학과")) {
                MRCount[3] += vo.getRentalCount();
                MRDays[3] += vo.getDays();
            } else if (vo.getMajor().equals("성경표지디자인과")) {
                MRCount[4] += vo.getRentalCount();
                MRDays[4] += vo.getDays();
            }
        }
        double Max = (double) MRDays[0] / (double) MRCount[0];
        Max = Math.round(Max * 100) / 100.0;
        int MaxN = 0;
        for (int i = 0; i < 5; i++) {
            double num = (double) MRDays[i] / (double) MRCount[i];
            num = Math.round(num * 100) / 100.0;
            if (num > Max) {
                Max = num;
                MaxN = i;
            }
        }
        System.out.print(" 대출이 반납이 가장 늦은 학과 : ");
        switch (MaxN) {
            case 1:
                System.out.println(" 목탁디자인과");
                break;
            case 2:
                System.out.println(" 오징어심리학과");
                break;
            case 3:
                System.out.println(" 감귤포장학과");
                break;
            case 4:
                System.out.println(" e스포츠학과");
                break;
            default:
                System.out.println(" 성경표지디자인과");
                break;
        }
        System.out.println();
    }

    // 가장 인기 있는 책의 저자
    public void solveQuiz7() {
        System.out.println("< 가장 인기 있는 책의 저자 >");
        ArrayList<BookVO> BRank = new ArrayList<BookVO>();
        for (BookVO vo : Booklist) {
            BRank.add(vo);
        }
        // 최고 대출 횟수
        int Max = BRank.get(0).getCount();
        for (BookVO vo : BRank) {
            Max = Math.max(vo.getCount(), Max);
        }
        for (BookVO vo : BRank) {
            if (vo.getCount() == Max) {
                System.out.println(" 가장 인기 있는 책의 저자 : " + vo.getAuthor());
            }
        }
        System.out.println();
    }

    // 사용자의 연체 횟수 검색
    public void solveQuiz8() {
        System.out.println("< 사용자의 연체 횟수 >");
        for (StudentVO vo : StudentList) {
            System.out.println(vo.getName() + " 의 연체 횟수 : " + vo.getOverdueCount());
        }
    }
}
