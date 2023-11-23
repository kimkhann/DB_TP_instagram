import java.sql.*;
import java.util.Scanner;

// login 기능 (id 중복 허용 X -> Exception handle 필요)
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 입력 받기
//	            System.out.print("ID를 입력하세요: ");
//	            String user_id = scanner.nextLine();
//
//	            System.out.print("PW 입력하세요: ");
//	            String pw = scanner.nextLine();

        System.out.print("ID를 입력하세요: ");
        String user_id_input = scanner.nextLine();

        System.out.print("PW 입력하세요: ");
        String pw_input = scanner.nextLine();

        // 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost:3306/insta_db";
        String username = "root";
        String password = "Rlarjs1!";

        // 데이터베이스 연결
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // SQL 쿼리 작성
            String sql_register = "INSERT INTO user (user_id, pw) VALUES (?, ?)";
            String sql_login = "SELECT CASE WHEN EXISTS (SELECT 1 FROM login WHERE user_id = ? AND pw = ?) THEN TRUE ELSE FALSE END AS result";
            // PreparedStatement를 사용하여 쿼리 실행
//	                try (PreparedStatement preparedStatement = connection.prepareStatement(sql_register)) {
//	                    // 파라미터 설정
//	                    preparedStatement.setString(1, user_id);
//	                    preparedStatement.setString(2, pw);
//
//	                    // 쿼리 실행 및 데이터베이스에 데이터 저장
//	                    int rowsAffected = preparedStatement.executeUpdate();
//	                    if (rowsAffected > 0) {
//	                        System.out.println("데이터베이스에 정보가 성공적으로 저장되었습니다.");
//	                    } else {
//	                        System.out.println("정보를 저장하는데 문제가 발생했습니다.");
//	                    }
//	                }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql_login)) {

                preparedStatement.setString(1, user_id_input);
                preparedStatement.setString(2, pw_input);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        boolean loginResult = resultSet.getBoolean("result");
                        if (loginResult) {
                            System.out.println("로그인 성공!");
                        } else {
                            System.out.println("로그인 실패: 유효하지 않은 사용자 ID 또는 비밀번호");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}