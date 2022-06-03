import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    public static void runMainMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        int option;
        System.out.println("Hello Admin, what would you like to do?");

        boolean isFinished = false;

        while (!isFinished) {
            System.out.println("1: Approve User\t2: Ban User\t3: Generate Random Integer\t0: Exit");
            try {
            input = scanner.nextLine();

                if (!InputValidator.inputIsInteger(input)) {
                    throw new InputMismatchException(Constants.INVALID_INPUT);
                } else {
                    option = Integer.parseInt(input);
                }


            switch (option) {
                case 0 -> {
                    System.out.println(Constants.EXITING);
                    System.exit(0);
                }

                case 1 -> {
                    System.out.println("Hello");
                    System.out.println("Approving user...");
                    isFinished = true;
                }

                case 3 -> {
                    System.out.println("Your random number is: " + Tools.generateRandomInteger());
                }

                default -> {
                    System.out.println(Constants.INVALID_INPUT);
                   continue;
                }
            }

            System.out.println("You have chosen option: " + option);

            } catch (InputMismatchException ex) {
                System.out.println(Constants.INVALID_INPUT);
                System.out.println("1: Approve User\t2: Ban User\t3: Exit");
                scanner.nextLine();
            }

        }
    }
}
