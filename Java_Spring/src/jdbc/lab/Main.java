package jdbc.lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        //Setting the username
        properties.setProperty("user", "root");
        //Setting the password
        properties.setProperty("password", "@Jbs980304");

        //Creating connection via DriverManager
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo",
                properties);

        //Reading the input username
        String username = scanner.nextLine();

        //Creating the Statement that will be executed
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT first_name, last_name, COUNT(*) AS games_count FROM users u " +
                "JOIN users_games ug ON u.id = ug.user_id " +
                "WHERE u.user_name = ? " +
                "GROUP BY u.id, u.first_name, u.last_name");

        //На първата ? идва username
        preparedStatement.setString(1, username);

        //Getting Result Set by using executeQuery()
        ResultSet resultSet = preparedStatement.executeQuery();

        //Putting the info of the Result Set into proper variables
        //It can be used the index of the column or the name of the column
        //If there is no such username the ResultSet will be empty
        if (resultSet.next()) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            int gamesCount = resultSet.getInt("games_count");

            //Printing the result
            System.out.printf("User: %s", username).append(System.lineSeparator());
            System.out.printf("%s %s has played %d games", firstName, lastName, gamesCount).append(System.lineSeparator());
        }else {
                System.out.println("No such user exists");
        }

        //Closing all the threads
        scanner.close();
        connection.close();
        resultSet.close();
    }
}
