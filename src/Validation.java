import java.util.HashMap;
import java.util.Scanner;

public class Validation {
    public static String workFlowValidation(Scanner userInput){
        System.out.println("Would you like to [a]dd child, add to [c]hores list, [u]pdate chore status, [v]iew child chore list or [q]uit?");
        String userResponse = userInput.nextLine();
        while(!userResponse.equalsIgnoreCase("a") && !userResponse.equalsIgnoreCase("c") && !userResponse.equalsIgnoreCase("u") && !userResponse.equalsIgnoreCase("v") && !userResponse.equalsIgnoreCase("q")){
            System.out.println("Invalid Option!!");
            System.out.println("Would you like to [a]dd child, add to [c]hores list, [u]pdate chore status, [v]iew child chore list or [q]uit?");
            userResponse = userInput.nextLine();
        }
        return userResponse;
    }

    public static Integer weekdayValidation(Scanner userInput){
        HashMap<String, Integer> weekdays = new HashMap<>();
        weekdays.put("Monday", 1);
        weekdays.put("Tuesday", 2);
        weekdays.put("Wednesday", 3);
        weekdays.put("Thursday", 4);
        weekdays.put("Friday", 5);
        weekdays.put("Saturday", 6);
        weekdays.put("Sunday", 7);
        System.out.println("What day? [Monday], [Tuesday], [Wednesday], [Thursday], [Friday], [Saturday] or [Sunday]");
        String userResponse = userInput.nextLine();
        while(!userResponse.equalsIgnoreCase("Monday") && !userResponse.equalsIgnoreCase("Tuesday") && !userResponse.equalsIgnoreCase("Wednesday") && !userResponse.equalsIgnoreCase("Thursday") && !userResponse.equalsIgnoreCase("Friday") && !userResponse.equalsIgnoreCase("Saturday") && !userResponse.equalsIgnoreCase("Sunday")){
            System.out.println("Invalid Option!!");
            System.out.println("What day? [Monday], [Tuesday], [Wednesday], [Thursday], [Friday], [Saturday] or [Sunday]");
            userResponse = userInput.nextLine();
        }
        return weekdays.get(capitalizeWord(userResponse));
    }

    public static String capitalizeWord(String word) {
        String cap = "";
        if (!word.isEmpty()) {
            cap = word.substring(0, 1).toUpperCase() + word.substring(1);
        }
        return cap;
    }


    public static String yesNoValidation(Scanner userInput){
        System.out.println("Is this chore complete [y]es or [n]o?");
        String userResponse = userInput.nextLine();
        while (!userResponse.equalsIgnoreCase("y") && !userResponse.equalsIgnoreCase("n")){
            System.out.println("Invalid Option!!");
            System.out.println("Is this chore complete [y]es or [n]o?");
            userResponse = userInput.nextLine();
        }
        return userResponse;
    }


    public static String viewFlowValidation(Scanner userInput){
        System.out.println("Would you like to view by [c]hild or by [a]ll children?");
        String userResponse = userInput.nextLine();
        while (!userResponse.equalsIgnoreCase("c") && !userResponse.equalsIgnoreCase("a")){
            System.out.println("Invalid Option!!");
            System.out.println("Would you like to view by [c]hild or by [a]ll children?");
            userResponse = userInput.nextLine();
        }
        return userResponse;
    }


    public static String viewChildChildrenValidation(Scanner userInput){
        System.out.println("Would you like to view by [d]ay or by [a]ll days?");
        String userResponse = userInput.nextLine();
        while (!userResponse.equalsIgnoreCase("d") && !userResponse.equalsIgnoreCase("a")){
            System.out.println("Invalid Option!!");
            System.out.println("Would you like to view by [d]ay or by [a]ll days?");
            userResponse = userInput.nextLine();
        }
        return userResponse;
    }

    public static int ageValidation(Scanner userInput) {
        boolean notValidAge = true;
        int intChildAge = 0;

        while (notValidAge) {
            System.out.println("Enter child's age:");
            String childAge = userInput.nextLine();
            try
            {
                intChildAge = Integer.parseInt(childAge);
                notValidAge = false;

            }
            catch (NumberFormatException e)
            {
                System.out.println(childAge + " is not a valid integer");
            } }

        return intChildAge;
    }
}
