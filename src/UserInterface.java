import java.util.Scanner;

public class UserInterface {
    public static void runMainMenu(){
        Scanner scanner = new Scanner(System.in);
        String input;
        int option;
        System.out.println("Hello Admin, what would you like to do?");

        boolean isFinished = false;

        while(!isFinished){
            System.out.println("1: Approve User \t2: Ban User\t3: Exit");

            input = scanner.nextLine();

            if(!InputValidator.inputIsInteger(input)){
                System.out.println(Constants.INVALID_INPUT);
                continue;
            }
            else{
                option = Integer.parseInt(input);
            }

            switch (option) {
                case 0 -> {
                    System.out.println(Constants.EXITING);
                    System.exit(0);
                }

                case 1 -> System.out.println("Hello");

                default -> {
                    System.out.println(Constants.INVALID_INPUT);
                    continue;
                }
            }

            System.out.println("You have chosen option: " + option);
        }
    }
}
