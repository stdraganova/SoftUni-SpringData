package jdbc.exercise;

import java.sql.*;
import java.util.*;

import static javax.swing.UIManager.getInt;

public class MinionsDB {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        Connection connection = establishConnection();
        
        //Exercise 1
        getVillainsNames(scanner, connection);
        
        //Exercise 2
        getMinionsNames(scanner, connection);
        
        //Exercise 3
        addMinion(scanner, connection);
        
        //Exercise 4
        changeTownNames(scanner, connection);

        //Exercise 5
        removeVillain(scanner, connection);
        
        //Exercise 6 
        printAllMinions(connection);
        
        //Exercise 7 
        increaseMinionsAge(scanner, connection);

        //Exercise 8
        increaseMinionsAgeProcedure(scanner, connection);
    }

    private static void increaseMinionsAgeProcedure(Scanner scanner, Connection connection) throws SQLException {
        int minionID = Integer.parseInt(scanner.nextLine());

        //Using CallableStatement because I need to call a udp Procedure.
        CallableStatement callableStatement = connection.prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, minionID);
        callableStatement.execute();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, age FROM minions " +
                "WHERE id = ?");
        preparedStatement.setInt(1, minionID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
    }

    private static void increaseMinionsAge(Scanner scanner, Connection connection) throws SQLException {
        int[] minionsID = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();

        for (int id : minionsID) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE minions " +
                    "SET age = age + 1 " +
                    "WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("SELECT name, age FROM minions " +
                    "WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, age FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }
    }

    private static void printAllMinions(Connection connection) throws SQLException {
        PreparedStatement preparedStatement= connection.prepareStatement("SELECT name FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayDeque<String> minionsNames = new ArrayDeque<>();

        while (resultSet.next()) {
            minionsNames.add(resultSet.getString("name"));
        }

        while (!minionsNames.isEmpty()){
            System.out.println(minionsNames.removeFirst());

            if (!minionsNames.isEmpty()) {
                System.out.println(minionsNames.removeLast());
            }
        }
    }

    private static void removeVillain(Scanner scanner, Connection connection) throws SQLException {
        int villainID = Integer.parseInt(scanner.nextLine());

        //First removing the villain from the many-to-many table
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM minions_villains " +
                "WHERE villain_id = ? ");
        preparedStatement.setInt(1, villainID);

        int minionsCount = preparedStatement.executeUpdate();

        if (minionsCount <= 0){
            System.out.println("No such villain was found");
            return;
        }
        preparedStatement= connection.prepareStatement("SELECT name FROM villains " +
                "WHERE id = ?");
        preparedStatement.setInt(1, villainID);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        String villainName = resultSet.getString(1);

        preparedStatement = connection.prepareStatement("DELETE FROM villains WHERE id = ? ");
        preparedStatement.setInt(1, villainID);
        preparedStatement.execute();

        System.out.printf("%s was deleted%n", villainName);
        System.out.printf("%d minions released%n", minionsCount);
    }

    private static void changeTownNames(Scanner scanner, Connection connection) throws SQLException {
        String country = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT UPPER(name) FROM towns t " +
                "WHERE t.country = ?");
        preparedStatement.setString(1, country);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> townsList = new ArrayList<>();

        while (resultSet.next()) {
            townsList.add(resultSet.getString(1));
        }

        System.out.printf("%d town names were affected.%n", townsList.size());
        System.out.println("[" + String.join(", ", townsList) + "]");
    }

    private static void addMinion(Scanner scanner, Connection connection) throws SQLException {
        String[] minionTokens = scanner.nextLine().split("\\s+");
        String villainName = scanner.nextLine().split("\\s+")[1];

        String minionName = minionTokens[1];
        int minionAge = Integer.parseInt(minionTokens[2]);
        String minionTown = minionTokens[3];

        // Check if the town exists or add it
        int townID = getTownId(connection, minionTown);
        if (townID == -1) {
            townID = insertTown(connection, minionTown);
            System.out.printf("Town %s was added to the database.%n", minionTown);
        }

        // Create the minion
        int minionId = insertMinion(connection, minionName, minionAge, townID);

        // Check if the villain exists or add it
        int villainID = getVillainId(connection, villainName);
        if (villainID == -1) {
            villainID = insertVillain(connection, villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        // Set the new minion to be servant to this villain
        assignMinionToVillain(connection, minionId, villainID);
        System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);
    }

    private static int getTownId(Connection connection, String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.id FROM towns t WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return -1; // Return -1 if town does not exist
    }

    private static int insertTown(Connection connection, String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO towns (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, townName);
        preparedStatement.executeUpdate();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        keys.next();
        return keys.getInt(1);
    }

    private static int insertMinion(Connection connection, String minionName, int minionAge, int townID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO minions (name, age, town_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, townID);
        preparedStatement.executeUpdate();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        keys.next();
        return keys.getInt(1);
    }

    private static int getVillainId(Connection connection, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return -1; // Return -1 if villain does not exist
    }

    private static int insertVillain(Connection connection, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO villains (name, evilness_factor) VALUES (?, 'evil')", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, villainName);
        preparedStatement.executeUpdate();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        keys.next();
        return keys.getInt(1);
    }

    private static void assignMinionToVillain(Connection connection, int minionId, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO minions_villains (minion_id, villain_id) VALUES (?, ?)");
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.executeUpdate();
    }


    private static void getMinionsNames(Scanner scanner, Connection connection) throws SQLException {
        int villainID = Integer.parseInt(scanner.nextLine());

        //Getting villain name
        PreparedStatement getVillainName = connection.prepareStatement("SELECT name FROM villains " +
                "WHERE id = ? ");
        getVillainName.setInt(1, villainID);

        ResultSet resultSetVillainName = getVillainName.executeQuery();
        if (!resultSetVillainName.next()){
            System.out.printf("No villain with ID %d exists in the database.%n", villainID);
        }else {
            System.out.printf("Villain: %s%n", resultSetVillainName.getString("name"));
        }

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.name, m.age FROM minions m " +
                "JOIN minions_villains mv ON m.id = mv.minion_id "+
                "WHERE villain_id = ? ");

        preparedStatement.setInt(1, villainID);

        ResultSet resultSetMinions = preparedStatement.executeQuery();

        int count = 1;
        while (resultSetMinions.next()) {
            String minionName = resultSetMinions.getString("name");
            int age = resultSetMinions.getInt("age");
            System.out.printf("%d. %s %d%n", count, minionName, age);
            count++;
        }

    }

    private static void getVillainsNames(Scanner scanner, Connection connection) throws SQLException {
        String name = scanner.nextLine();

        //Creating PreparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT v.name, COUNT(*) AS minions_count FROM villains v " +
                "JOIN minions_villains mv ON v.id = mv.villain_id " +
                "WHERE v.name = ?" +
                "GROUP BY v.name " +
                "HAVING minions_count > 15 " +
                "ORDER BY minions_count DESC ");

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int minionsCount = resultSet.getInt("minions_count");

            System.out.printf("%s %d", name, minionsCount);
        }
    }

    private static Connection establishConnection() throws SQLException {
        //Creating properties
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "@Jbs980304");

        //Establishing connection to the MySQL server
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        return connection;
    }
}
