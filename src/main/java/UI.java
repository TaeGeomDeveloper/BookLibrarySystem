import java.sql.SQLException;
import java.util.Scanner;
import BL.*;

public class UI {
    public void Bookstore() throws SQLException {
        System.out.println("\n D조 도서실 에 오신것을 환영합니다. ");
        Logic bl = new Logic();
        boolean run = false;
        Scanner scanner = new Scanner(System.in);
        while(!run){
            System.out.println("\n--------------------------------------------------------------------------------------\n");
            System.out.println("\t\t\t\t\t < D조 도서실 도서 업무 전산 시스템 > \n\n\n");
            System.out.println("\t 원하는 검색 방식을 선택 해주세요 \n");
            System.out.println(" 1. 대출 현황 정보 생성 | 2. 대출 이용 현황 정보 검색 | 3. 대출 장부 검색 | 4. 종료 | 5. 직접 대출 입력");
            System.out.println("\n--------------------------------------------------------------------------------------\n");
            System.out.print("선택> ");
            int num = scanner.nextInt();
            boolean isStop = false;
            boolean isStop2 = false;
            switch (num) {
                case 1:
                    System.out.println(" 500건에 대한 대출 현황 정보를 자동 생성 합니다.\n");
                    bl.Insert();
                    System.out.println(" 500건에 대한 대출 현황 정보를 자동 생성 되었습니다.");
                    break;
                case 2:
                    System.out.println(" 대출 이용 현황 정보 를 검색 하겠습니다. ");
                    while (!isStop) {
                        System.out.println("\n--------------------------------------------------------------------------------------\n");
                        System.out.println("\t 원하는 정보를 선택 해주세요 \n");
                        System.out.println(" 1. 대출 도서 상위 정보 검색 | 2. 대출자 상위 정보 검색 | 3. 대출 기간이 가장 짧은 도서 검색 |\n" +
                                " 4. 대출반납이 가장 빠른 학생 검색 | 5. 대출을 가장 많이 하는 학과 검색 | 6. 대출반납이 가장 늦은 학과 검색 |\n" +
                                " 7. 가장 인기있는 책의 저자 검색 | 8. 사용자의 연체 횟수를 검색 | 9. 종료");
                        System.out.println("\n--------------------------------------------------------------------------------------\n");
                        System.out.print("선택> ");
                        int SelectNum = scanner.nextInt();
                        switch (SelectNum) {
                            case 1:
                                System.out.println(" 1. 대출 도서 상위 정보 검색 을 시작합니다. \n");
                                bl.solveQuiz1();
                                break;
                            case 2:
                                System.out.println(" 2. 대출자 상위 정보 검색 을 시작합니다. \n");
                                bl.solveQuiz2();
                                break;
                            case 3:
                                System.out.println(" 3. 대출 기간이 가장 짧은 도서 검색 을 시작합니다. \n");
                                bl.solveQuiz3();
                                break;
                            case 4:
                                System.out.println(" 4. 대출반납이 가장 빠른 학생 검색 을 시작합니다. \n");
                                bl.solveQuiz4();
                                break;
                            case 5:
                                System.out.println(" 5. 대출을 가장 많이 하는 학과 검색 을 시작합니다. \n");
                                bl.solveQuiz5();
                                break;
                            case 6:
                                System.out.println(" 6. 대출반납이 가장 늦은 학과 검색 을 시작합니다. \n");
                                bl.solveQuiz6();
                                break;
                            case 7:
                                System.out.println(" 7. 가장 인기있는 책의 저자 검색 을 시작합니다. \n");
                                bl.solveQuiz7();
                                break;
                            case 8:
                                System.out.println(" 8. 사용자의 연체 횟수 검색 을 시작합니다. \n");
                                bl.solveQuiz8();
                                break;
                            case 9:
                                System.out.println(" 검색을 종료합니다. \n");
                                isStop = true;
                                break;
                            default:
                                System.out.println(" 잘못 입력 하셨습니다. ");
                                break;
                        }
                    }
                    break;
                case 3:
                    while (!isStop2) {
                        System.out.println("\n 대출 장부를 검색 하겠습니다. ");
                        System.out.println("\n--------------------------------------------------------------------------------------\n");
                        System.out.println("\t 원하는 정보를 선택 해주세요 \n");
                        System.out.println(" 1. 사용자 이름 으로 검색하기 | 2. 도서명 으로 검색하기 | 3. 종료  |\n" );
                        System.out.println("\n--------------------------------------------------------------------------------------\n");
                        System.out.print("선택> ");
                        int SelectNum2 = scanner.nextInt();
                        switch (SelectNum2) {
                            case 1 :
                                String N = scanner.nextLine();
                                System.out.print("\n 사용자 이름 : ");
                                String Sname = scanner.nextLine();
                                bl.SearchName(Sname);
                                break;
                            case 2 :
                                String N2 = scanner.nextLine();
                                System.out.print("\n 책 이름 : ");
                                String Bname = scanner.nextLine();
                                bl.SearchBookName(Bname);
                                break;
                            default:
                                isStop2 = true;
                                break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다"
                            + "\n이용해주셔서 감사합니다.");
                    run = true;
                    break;
                case 5:
                    System.out.println("수동으로 대출 하겠습니다.");
                    bl.manualInsert();
                    break;

                default:
                    System.out.println("잘못 입력하셨습니다. ");
                    break;
            }
        }
        scanner.close();
    }
}
