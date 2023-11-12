package car;

import java.sql.*;
import java.util.Scanner;

public class car{

    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // JDBC 연결
            connectToDatabase();

            while (true) {
                // 메뉴 표시
                displayMenu();

                // 사용자 입력 받기
                int choice = scanner.nextInt();
                scanner.nextLine(); // 개행문자 제거

                // 선택된 메뉴 실행
                switch (choice) {
                    case 1:
                        // 제조사 정보 확인
                        viewManufacturerInfo();
                        break;
                    case 2:
                        // 자동차 모델 정보 확인
                        viewCarModelInfo();
                        break;
                    case 3:
                        // 중고차 정보 확인
                        viewUsedCarInfo();
                        break;
                    case 4:
                        // 중고차 정보 등록
                        addUsedCarInfo();
                        break;
                    case 5:
                        // 중고차 정보 수정
                        updateUsedCarInfo();
                        break;
                    case 6:
                        // 중고차 정보 삭제
                        deleteUsedCarInfo();
                        break;
                    case 7:
                        // 거래 내역 확인
                        viewTransactionHistory();
                        break;
                    case 8:
                        // 거래 내역 등록
                        addTransactionHistory();
                        break;
                    case 9:
                        // 종료
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    default:
                        System.out.println("유효하지 않은 선택입니다. 다시 선택해주세요.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 연결 종료
            closeConnection();
        }
    }

    private static void connectToDatabase() throws SQLException {
        // JDBC 연결
        String url = "jdbc:mysql://192.168.202.3:4567/car?useUnicode=true&characterEncoding=utf8";
        String username = "leesj";
        String password = "1234";
        connection = DriverManager.getConnection(url, username, password);
    }

    private static void closeConnection() {
        // 연결 종료
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("===== 중고차 관리 시스템 메뉴 =====");
        System.out.println("1. 제조사 정보 확인");
        System.out.println("2. 자동차 모델 정보 확인");
        System.out.println("3. 중고차 정보 확인");
        System.out.println("4. 중고차 정보 등록");
        System.out.println("5. 중고차 정보 수정");
        System.out.println("6. 중고차 정보 삭제");
        System.out.println("7. 거래 내역 확인");
        System.out.println("8. 거래 내역 등록");
        System.out.println("9. 종료");
        System.out.print("메뉴를 선택하세요: ");
    }

    private static void viewManufacturerInfo() throws SQLException {
        // 제조사 정보 조회 로직 작성
        // ...
    }

    private static void viewCarModelInfo() throws SQLException {
        // 자동차 모델 정보 조회 로직 작성
        // ...
    }

    private static void viewUsedCarInfo() throws SQLException {
        // 중고차 정보 조회 로직 작성
        // ...
    }

    private static void addUsedCarInfo() throws SQLException {
        // 중고차 정보 등록 로직 작성
        // ...
    }

    private static void updateUsedCarInfo() throws SQLException {
        // 중고차 정보 수정 로직 작성
        // ...
    }

    private static void deleteUsedCarInfo() throws SQLException {
        // 중고차 정보 삭제 로직 작성
        // ...
    }

    private static void viewTransactionHistory() throws SQLException {
        // 거래 내역 조회 로직 작성
        // ...
    }

    private static void addTransactionHistory() throws SQLException {
        // 거래 내역 등록 로직 작성
        // ...
    }
}