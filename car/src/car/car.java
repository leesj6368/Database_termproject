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
        // 제조사 정보 조회
        String query = "SELECT * FROM 제조사";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                System.out.println("제조사 ID: " + resultSet.getInt("제조사ID") +
                        ", 제조사 이름: " + resultSet.getString("제조사이름"));
            }
        }
    }

    private static void viewCarModelInfo() throws SQLException {
        // 자동차 모델 정보 조회
        String query = "SELECT * FROM 자동차모델";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                System.out.println("모델 ID: " + resultSet.getInt("모델ID") +
                        ", 모델 이름: " + resultSet.getString("모델이름") +
                        ", 제조사 ID: " + resultSet.getInt("제조사ID"));
            }
        }
    }

    private static void viewUsedCarInfo() throws SQLException {
        // 중고차 정보 조회
        String query = "SELECT * FROM 중고차";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                System.out.println("차량 ID: " + resultSet.getInt("자동차ID") +
                        ", 모델 ID: " + resultSet.getInt("모델ID") +
                        ", 연식: " + resultSet.getInt("연식") +
                        ", 가격: " + resultSet.getDouble("가격") +
                        ", 상태: " + resultSet.getString("상태"));
            }
        }
    }


    private static void addUsedCarInfo() throws SQLException {
        // 중고차 정보 등록
        System.out.print("모델 ID 입력: ");
        int modelId = scanner.nextInt();
        System.out.print("연식 입력: ");
        int year = scanner.nextInt();
        System.out.print("가격 입력: ");
        double price = scanner.nextDouble();
        System.out.print("상태 입력: ");
        scanner.nextLine(); // 개행문자 제거
        String status = scanner.nextLine();

        String query = "INSERT INTO 중고차 (모델ID, 연식, 가격, 상태) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, modelId);
            preparedStatement.setInt(2, year);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, status);
            preparedStatement.executeUpdate();
            System.out.println("중고차 정보가 성공적으로 등록되었습니다.");
        }
    }

    private static void updateUsedCarInfo() throws SQLException {
        // 중고차 정보 수정
        System.out.print("수정할 차량 ID 입력: ");
        int carId = scanner.nextInt();
        System.out.print("수정할 가격 입력: ");
        double newPrice = scanner.nextDouble();

        String query = "UPDATE 중고차 SET 가격 = ? WHERE 자동차ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, carId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("중고차 정보가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("수정할 차량이 존재하지 않습니다.");
            }
        }
    }
    private static void deleteUsedCarInfo() throws SQLException {
        // 중고차 정보 삭제
        System.out.print("삭제할 차량 ID 입력: ");
        int carId = scanner.nextInt();

        String query = "DELETE FROM 중고차 WHERE 자동차ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("중고차 정보가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("삭제할 차량이 존재하지 않습니다.");
            }
        }
    }

    private static void viewTransactionHistory() throws SQLException {
        // 거래 내역 조회
        String query = "SELECT * FROM 거래";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                System.out.println("거래 ID: " + resultSet.getInt("거래ID") +
                        ", 차량 ID: " + resultSet.getInt("자동차ID") +
                        ", 고객 ID: " + resultSet.getInt("고객ID") +
                        ", 판매인 ID: " + resultSet.getInt("판매인ID") +
                        ", 거래일자: " + resultSet.getDate("거래일자") +
                        ", 가격: " + resultSet.getDouble("가격"));
            }
        }
    }

    private static void addTransactionHistory() throws SQLException {
        // 거래 내역 등록
        System.out.print("차량 ID 입력: ");
        int carId = scanner.nextInt();
        System.out.print("고객 ID 입력: ");
        int customerId = scanner.nextInt();
        System.out.print("판매인 ID 입력: ");
        int salespersonId = scanner.nextInt();
        System.out.print("거래일자 입력 (yyyy-MM-dd): ");
        scanner.nextLine(); // 개행문자 제거
        String dealDateStr = scanner.nextLine();
        Date dealDate = Date.valueOf(dealDateStr);
        System.out.print("가격 입력: ");
        double amount = scanner.nextDouble();

        String query = "INSERT INTO 거래 (자동차ID, 고객ID, 판매인ID, 거래일자, 가격) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setInt(3, salespersonId);
            preparedStatement.setDate(4, dealDate);
            preparedStatement.setDouble(5, amount);
            preparedStatement.executeUpdate();
            System.out.println("거래 내역이 성공적으로 등록되었습니다.");
        }
    }
}