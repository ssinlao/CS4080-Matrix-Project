import java.io.*;
import java.util.*;

public class MatrixOperations {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            if (choice == 4) {
                running = false;
                System.out.println("Exiting program. Goodbye!");
                continue;
            }

            try {
                Matrix m1 = inputMatrix("Matrix 1");
                Matrix m2 = inputMatrix("Matrix 2");

                Matrix result = null;
                String operation = "";

                switch (choice) {
                    case 1:
                        result = Matrix.add(m1, m2);
                        operation = "Addition";
                        break;
                    case 2:
                        result = Matrix.subtract(m1, m2);
                        operation = "Subtraction";
                        break;
                    case 3:
                        result = Matrix.multiply(m1, m2);
                        operation = "Multiplication";
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        waitForUser();
                        continue;
                }

                System.out.println("\n" + operation + " Result:");
                result.print();

            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
            }

            waitForUser();
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MATRIX OPERATIONS MENU (Java Version)");
        System.out.println("=".repeat(50));
        System.out.println("1. Matrix Addition");
        System.out.println("2. Matrix Subtraction");
        System.out.println("3. Matrix Multiplication");
        System.out.println("4. Exit");
        System.out.println("=".repeat(50));
    }

    private static Matrix inputMatrix(String name) throws IOException {
        System.out.println("\n" + name + " Input:");
        System.out.println("1. Enter manually");
        System.out.println("2. Read from file");
        int choice = getIntInput("Choose input method: ");

        if (choice == 1) {
            return inputMatrixManually();
        } else if (choice == 2) {
            return readMatrixFromFile();
        } else {
            throw new IllegalArgumentException("Invalid input method!");
        }
    }

    private static Matrix inputMatrixManually() {
        int rows = getIntInput("Enter number of rows: ");
        int cols = getIntInput("Enter number of columns: ");

        Matrix matrix = new Matrix(rows, cols);

        System.out.println("Enter matrix elements row by row:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.setElement(i, j, getFloatInput(
                    "Element [" + i + "][" + j + "]: "
                ));
            }
        }

        return matrix;
    }

    private static Matrix readMatrixFromFile() throws IOException {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine();

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String[] dimensions = reader.readLine().trim().split("\\s+");
        int rows = Integer.parseInt(dimensions[0]);
        int cols = Integer.parseInt(dimensions[1]);

        Matrix matrix = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            String[] elements = reader.readLine().trim().split("\\s+");
            for (int j = 0; j < cols; j++) {
                matrix.setElement(i, j, Float.parseFloat(elements[j]));
            }
        }

        reader.close();
        System.out.println("Matrix loaded successfully from " + filename);
        return matrix;
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter an integer.");
            }
        }
    }

    private static float getFloatInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static void waitForUser() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
