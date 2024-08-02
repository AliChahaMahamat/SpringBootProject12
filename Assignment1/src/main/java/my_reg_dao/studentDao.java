package my_reg_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import my_reg_model.student;

public class studentDao {

    private final String jdbcURL = "jdbc:postgresql://localhost:5432/reg_student";
    private final String jdbcUsername = "postgres";
    private final String jdbcPassword = "ali123";

    private static final String INSERT_STUDENT_SQL = "INSERT INTO student (id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String SELECT_STUDENT_BY_ID = "SELECT id, first_name, last_name FROM student WHERE id = ?";

    public studentDao() {
        try {
            Class.forName("org.postgresql.Driver"); // Explicitly load the PostgreSQL driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            printSQLException(e);
            throw e;
        }
    }

    public void insertStudent(student student) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            throw e;
        }
    }

    public student selectStudent(int id) {
        student student = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                student = new student(id, firstName, lastName);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return student;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static void studentDao(studentDao studentDao) {
        // This method seems unnecessary, consider removing or implementing as needed
    }
}
