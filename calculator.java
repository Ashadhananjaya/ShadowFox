import java.util.Scanner;

public class calculator{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalc = true;

        while (continueCalc) {
            System.out.println("\n=== Enhanced Console-based Calculator ===");
            System.out.println("1. Basic Arithmetic");
            System.out.println("2. Scientific Calculator");
            System.out.println("3. Unit Conversion");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    basicArithmetic(scanner);
                    break;
                case 2:
                    scientificCalculator(scanner);
                    break;
                case 3:
                    unitConversion(scanner);
                    break;
                case 4:
                    continueCalc = false;
                    System.out.println("Exiting Calculator. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    // Basic Arithmetic Operations
    public static void basicArithmetic(Scanner scanner) {
        System.out.print("Enter first number: ");
        double a = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double b = scanner.nextDouble();
        System.out.println("Choose operation: + - * /");
        char op = scanner.next().charAt(0);

        switch (op) {
            case '+': System.out.println("Result: " + (a + b)); break;
            case '-': System.out.println("Result: " + (a - b)); break;
            case '*': System.out.println("Result: " + (a * b)); break;
            case '/':
                if (b != 0) System.out.println("Result: " + (a / b));
                else System.out.println("Error: Division by zero.");
                break;
            default: System.out.println("Invalid operator.");
        }
    }

    // Scientific Calculator
    public static void scientificCalculator(Scanner scanner) {
        System.out.println("1. Square Root");
        System.out.println("2. Exponentiation (a^b)");
        System.out.print("Choose an operation: ");
        int op = scanner.nextInt();

        switch (op) {
            case 1:
                System.out.print("Enter a number: ");
                double num = scanner.nextDouble();
                if (num >= 0)
                    System.out.println("Square root: " + Math.sqrt(num));
                else
                    System.out.println("Error: Negative number.");
                break;

            case 2:
                System.out.print("Enter base: ");
                double base = scanner.nextDouble();
                System.out.print("Enter exponent: ");
                double exp = scanner.nextDouble();
                System.out.println("Result: " + Math.pow(base, exp));
                break;

            default:
                System.out.println("Invalid scientific operation.");
        }
    }

    // Unit Conversion
    public static void unitConversion(Scanner scanner) {
        System.out.println("1. Temperature (Celsius <-> Fahrenheit)");
        System.out.println("2. Currency (USD <-> INR)");
        System.out.print("Choose conversion: ");
        int conv = scanner.nextInt();

        switch (conv) {
            case 1:
                System.out.println("a. Celsius to Fahrenheit");
                System.out.println("b. Fahrenheit to Celsius");
                char tempOp = scanner.next().charAt(0);
                System.out.print("Enter temperature: ");
                double temp = scanner.nextDouble();

                if (tempOp == 'a')
                    System.out.println("Fahrenheit: " + (temp * 9 / 5 + 32));
                else if (tempOp == 'b')
                    System.out.println("Celsius: " + ((temp - 32) * 5 / 9));
                else
                    System.out.println("Invalid option.");
                break;

            case 2:
                System.out.println("a. USD to INR");
                System.out.println("b. INR to USD");
                char curOp = scanner.next().charAt(0);
                System.out.print("Enter amount: ");
                double amount = scanner.nextDouble();

                final double USD_TO_INR = 83.0; // example rate

                if (curOp == 'a')
                    System.out.println("INR: ₹" + (amount * USD_TO_INR));
                else if (curOp == 'b')
                    System.out.println("USD: $" + (amount / USD_TO_INR));
                else
                    System.out.println("Invalid option.");
                break;

            default:
                System.out.println("Invalid conversion choice.");
        }
    }
}
