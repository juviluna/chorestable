// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.*;
import java.sql.*;

public class Main {
    //
    public static String url = "jdbc:postgresql://localhost:5432/chores?user=postgres&password=isa1023";

    public static Validation validation = new Validation();
    public static void main(String[] args) {
        // Workflow
        // User and Login
        // Parents can add chores and mark complete.
        // 3 tables
        //1. Users
        //2. Chores
        //3. Children Profile

        // Some users connected to Children Profile, Chores is connected to both users and children profile.



        String userResponse = "";
        Scanner userInput = new Scanner(System.in);


        while(!userResponse.equalsIgnoreCase("q")){
            userResponse = validation.workFlowValidation(userInput);
            // add child
            if (userResponse.equalsIgnoreCase("a")){
                addChild(userInput);

                // add chore to child
            } else if (userResponse.equalsIgnoreCase("c")) {
                    addChore(userInput);

                // update chores status
            } else if (userResponse.equalsIgnoreCase("u")) {
                updateChore(userInput);

                // view chores lists
            } else if (userResponse.equalsIgnoreCase("v")) {




                    userResponse = validation.viewFlowValidation(userInput);
                    if (userResponse.equalsIgnoreCase("c")){
                        userResponse = validation.viewChildChildrenValidation(userInput);
                        if (userResponse.equalsIgnoreCase("d")){
                            viewChoresByChildAndDay(userInput);
                        } else if (userResponse.equalsIgnoreCase("a")) {
                            viewChoresByChildAndAllDays(userInput);
                        }
                    } else if (userResponse.equalsIgnoreCase("a")) {
                        userResponse = validation.viewChildChildrenValidation(userInput);
                        if (userResponse.equalsIgnoreCase("d")){
                            viewChoresByAllChildrenAndDay(userInput);
                        } else if (userResponse.equalsIgnoreCase("a")) {
                            viewChoresByAllChildrenAndAllDays();
                        }
                    }
                }
            }
        }




    // check database for child
    public static boolean checkChild(String childName){
        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "select * from children where name = ?";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, childName);
            ResultSet results = statement.executeQuery();
            return results.next();

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
        return false;
    }

    public static Integer getChildId (String childName){
        int childId = 0;
        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "select id from children where name = ?";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, childName);
            ResultSet results = statement.executeQuery();
            if (results.next()){
                childId = results.getInt("id");
            };

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
        return childId;
    }

    // add child method

    public static void addChild(Scanner userInput){
        System.out.println("Enter child's name:");
        String childName = validation.capitalizeWord(userInput.nextLine());

        while (checkChild(childName)) {
            System.out.println("Child already exists.");
            System.out.println("Enter child's name:");
            childName = validation.capitalizeWord(userInput.nextLine());
        }

        Integer childAge = validation.ageValidation(userInput);

        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "insert into children (name, age) values (?,?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, childName);
            statement.setInt(2, childAge);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Child added to Database.");
            }



            System.out.println("Added Child");

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
    }

    // add chore method

    public static void addChore(Scanner userInput){
        System.out.println("Enter child's name:");
        String childName = validation.capitalizeWord(userInput.nextLine());

        while (!checkChild(childName)) {
            System.out.println("Child doesn't exist.");
            System.out.println("Enter child's name:");
            childName = validation.capitalizeWord(userInput.nextLine());
        }
        Integer childId = getChildId(childName);
        Integer weekday = validation.weekdayValidation(userInput);
        System.out.println("Enter chore");
        String chore = userInput.nextLine();


        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "insert into chores (chore, is_complete, child_id, day_id) values (?,false,?,?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, chore);
            statement.setInt(2, childId);
            statement.setInt(3, weekday);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Chore added to Database.");
            }

        }catch (SQLException error){
            System.out.println(("Error " + error.getMessage()));
        }
    }

    // set chore complete

    public static void updateIsComplete(Boolean status, Integer choreId){

        try{
            Connection connect = DriverManager.getConnection(url);
            String sql = "UPDATE chores SET is_complete = ? WHERE id = ?;";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setBoolean(1, status);
            statement.setInt(2, choreId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Chore updated.");
            }

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
    }


    public static void updateChore (Scanner userInput){
        System.out.println("Enter child's name:");
        String childName = validation.capitalizeWord(userInput.nextLine());

        while (!checkChild(childName)) {
            System.out.println("Child doesn't exist.");
            System.out.println("Enter child's name:");
            childName = validation.capitalizeWord(userInput.nextLine());
        }
        Integer childId = getChildId(childName);
        Integer weekday = validation.weekdayValidation(userInput);
        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "select * from chores where day_id = ? and child_id = ?;";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, weekday);
            statement.setInt(2, childId);
            ResultSet results = statement.executeQuery();

            if (!results.next()) {
                System.out.println("No chores!!");
            }

            while (results.next()){
                System.out.println("[" + results.getInt("id") + "] " + results.getString("chore") + "  complete: " + results.getBoolean("is_complete"));
                String yesNo = validation.yesNoValidation(userInput);
                updateIsComplete(yesNo.equalsIgnoreCase("y"), results.getInt("id"));

            }

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
    }

    // view chores table

    public static void viewChoresByAllChildrenAndAllDays(){
        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "select chores.id, chore, is_complete, days.weekday, children.name from chores inner join children on chores.child_id = children.id inner join days on chores.day_id = days.id order by child_id, day_id ";
            PreparedStatement statement = connect.prepareStatement(sql);
//            statement.setInt(1, weekday);

            ResultSet results = statement.executeQuery();

            if (!results.next()) {
                System.out.println("No chores!!");
            }

            while (results.next()){
                System.out.println("[" + results.getInt("id") + "] " + results.getString("name") + " - " + results.getString("weekday") + " - " + results.getString("chore") + "  complete: " + results.getBoolean("is_complete"));
            }

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
    }

    public static void viewChoresByAllChildrenAndDay(Scanner userInput){
        int weekday = validation.weekdayValidation(userInput);
        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "select chores.id, chore, is_complete, days.weekday, children.name from chores inner join children on chores.child_id = children.id inner join days on chores.day_id = days.id where day_id = ? order by child_id";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, weekday);

            ResultSet results = statement.executeQuery();
            if (!results.next()) {
                System.out.println("No chores today!!");
            }
            while (results.next()){
                System.out.println("[" + results.getInt("id") + "] " + results.getString("name") + " - " + results.getString("weekday") + " - " + results.getString("chore") + "  complete: " + results.getBoolean("is_complete"));
            }

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
    }


    public static void viewChoresByChildAndAllDays(Scanner userInput){
        System.out.println("Enter child's name:");
        String childName = validation.capitalizeWord(userInput.nextLine());



        while (!checkChild(childName)) {
            System.out.println("Child doesn't exist.");
            System.out.println("Enter child's name:");
            childName = validation.capitalizeWord(userInput.nextLine());
        }
        Integer childId = getChildId(childName);

        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "select * from chores where child_id = ?;";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, childId);
            ResultSet results = statement.executeQuery();

            if (!results.next()) {
                System.out.println("No chores!!");
            }

            while (results.next()){
                System.out.println("[" + results.getInt("id") + "] " + results.getString("chore") + "  complete: " + results.getBoolean("is_complete"));
            }

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
    }

    public static void viewChoresByChildAndDay(Scanner userInput){
        System.out.println("Enter child's name:");
        String childName = validation.capitalizeWord(userInput.nextLine());

        while (!checkChild(childName)) {
            System.out.println("Child doesn't exist.");
            System.out.println("Enter child's name:");
            childName = validation.capitalizeWord(userInput.nextLine());
        }
        Integer childId = getChildId(childName);
        Integer weekday = validation.weekdayValidation(userInput);
        try {
            Connection connect = DriverManager.getConnection(url);
            String sql = "select * from chores where day_id = ? and child_id = ?;";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, weekday);
            statement.setInt(2, childId);
            ResultSet results = statement.executeQuery();
            if (!results.next()) {
                System.out.println("No chores today!!");
            }
            while (results.next()){
                System.out.println("[" + results.getInt("id") + "] " + results.getString("chore") + "  complete: " + results.getBoolean("is_complete"));
            }

        }catch (SQLException error){
            System.out.println("Error " + error.getMessage());
        }
    }


}