    /*
    written by finalzyyy
    copyright 2025 
    */
import java.util.Scanner;

public class StaffTest {
    static Staff loggedInUser = null;
    static int employeeCount = 0;
    static int adminCount = 0;
    static Admin[] admin = new Admin[100];
    static Employee[] employee = new Employee[100];
    

    public static void main(String[] args) { // main function
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (loggedInUser == null) {
                System.out.println("\n --- -------------------- ---");
                System.out.println("|   |  Staff Management  |   |");
                System.out.println(" --- -------------------- ---");
                System.out.println("| 1.|Login               |   |");
                System.out.println("| 2.|Create Account      |   |");
                System.out.println("| 3.|Exit                |   |");
                System.out.println(" --- -------------------- ---");
                System.out.print("Select an option\t: ");
    
                int option = getValidOption(scanner, 1, 3); //function at line 103
    
                if (option == 1) {
                    login(scanner); // function at line 71
                } else if (option == 2) {
                    createAccount(scanner); // function at line 96
                } else if (option == 3) {
                    System.out.println(" --- -------------------- ---");
                    System.out.println("|   |      GoodBye!      |   |");
                    System.out.println(" --- -------------------- ---");
                    System.out.println(" --- Exiting Staff Management ---\n");
                    break;
                }
            } else {
                System.out.println("\n === Welcome, " + (loggedInUser instanceof Admin ? "Admin" : "Employee") + " " + loggedInUser.getId() + " ===");
                System.out.println(" --- -------------------- ---");
                System.out.println("| 1.|Log Out             |   |");
                System.out.println("| 2.|Check Personal Data |   |");
                System.out.println("| 3.|Update Personal Data|   |");
                if (loggedInUser instanceof Admin) {
                    System.out.println("| 4.|Delete Employee     |   |");
                }
                System.out.println(" --- -------------------- ---");
                System.out.print("Select an option\t: ");

                
                // Adjust max option for input validation
                int choice = getValidOption(scanner, 1, loggedInUser instanceof Admin ? 4 : 3); //function at line 103
                
                if (choice == 1) {
                    loggedInUser = null;
                    System.out.println(" === Logged out successfully. === ");
                } else if (choice == 2) {
                    checkPersonalData(); // function at line 218
                } else if (choice == 3) {
                    loggedInUser.updateData(scanner); // function at staff.jave line 62

                } else if (choice == 4 && loggedInUser instanceof Admin) {
                    deleteEmployee(scanner); // function at line 166
                }
    
            }
        }

    }

    static void login(Scanner scanner) { // login function
        System.out.print("Enter ID\t\t: ");
        String id = scanner.nextLine();
        System.out.print("Enter password\t\t: ");
        String password = scanner.nextLine().trim();

        for (int i = 0; i < adminCount; i++) {
            if (admin[i].getId().equals(id) && admin[i].getPassword().equals(password)) {
                loggedInUser = admin[i];
                System.out.println(" === Admin login successful! === ");
                return;
            }
        }

        for (int i = 0; i < employeeCount; i++) {
            if (employee[i].getId().equals(id) && employee[i].getPassword().equals(password)) {
                loggedInUser = employee[i];
                System.out.println(" === Employee login successful! === ");
                return;
            }
        }

        System.out.println("!! Invalid ID or password !!");
    }

    static void createAccount(Scanner scanner) { //create account function
        System.out.println(" --- -------------------- ---");
        System.out.println("| 1.|Create as Admin     |   |");
        System.out.println("| 2.|Create as Employee  |   |");
        System.out.println(" --- -------------------- ---");
        System.out.print("Select an option\t: ");

        int role = getValidOption(scanner, 1, 2); //function at line 103

        System.out.print("Enter Name\t\t: ");
        String name = scanner.nextLine();

        String idPattern = (role == 1) ? "A\\d{4}" : "E\\d{4}";
        String id;
        while (true) {
            System.out.print("Enter ID (format " + (role == 1 ? "A0001" : "E0001") + ")\t: ");
            id = scanner.nextLine().trim().toUpperCase();
            if (!id.matches(idPattern)) {
                System.out.println("!! Invalid ID format. Please follow the format " + (role == 1 ? "A0001" : "E0001") + " !!");
            } else if ((role == 1 && findAdmin(id) != -1) || (role == 2 && findEmployee(id) != -1)) { //function at line 192, 184
                System.out.println("!! ID already exists !!");
            } else {
                break;
            }
        }


        String password;
        while (true) {
            System.out.print("Enter Password (8 num)\t: ");
            password = scanner.nextLine().trim();
            if (password.matches("\\d{8}")) {
                break;
            } else {
                System.out.println("!! Invalid password. It must be only 8 digits. !!");
            }
        }

        String phone;
        while (true) {
            System.out.print("Enter Phone Number\t: ");
            phone = scanner.nextLine().trim();
            if (phone.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("!! Invalid phone number. It must be only 11 digits. !!");
            }
        }

        int gender;
        while (true) {
            System.out.print("Enter Gender (1=M / 2=F): ");
            try {
                gender = Integer.parseInt(scanner.nextLine().trim());
                if (gender == 1 || gender == 2) break;
                else System.out.println("!! Please enter 1 for Male or 2 for Female. !!");
            } catch (NumberFormatException e) {
                System.out.println("!! Invalid input. Please enter a number. !!");
            }
        }

        if (role == 1) {
            admin[adminCount++] = new Admin(id, password, name, phone, gender);
            System.out.println(" \n=== Admin account created! ===");
        } else {
            employee[employeeCount++] = new Employee(id, password, name, phone, gender);
            System.out.println(" \n=== Employee account created! ===");
        }
    }

    static void deleteEmployee(Scanner scanner) { // delete function (admin only)
        System.out.print("Enter ID to delete\t: ");
        String target = scanner.nextLine();

        int index = findEmployee(target);
        if (index == -1) {
            System.out.println("!! Employee not found !!");
            return;
        }

        for (int i = index; i < employeeCount - 1; i++) {
            employee[i] = employee[i + 1];
        }
        employee[--employeeCount] = null;
        System.out.println(" === Employee deleted successfully === ");
    }

    //find the id A/E0001 - 0100 function
    static int findEmployee(String id) {
        for (int i = 0; i < employeeCount; i++) {
            if (employee[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    static int findAdmin(String id) {
        for (int i = 0; i < adminCount; i++) {
            if (admin[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    static int getValidOption(Scanner scanner, int min, int max) { //get valid option function, i use try catch because it is easy to maintain the maximum and minimum
        int option = -1;
        while (true) {
            try {
                option = Integer.parseInt(scanner.nextLine().trim());
                if (option < min || option > max) {
                    System.out.println("!! Invalid choice! Enter between " + min + " and " + max + " !!");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("!! Invalid input! Please enter a number !!");
            }
        }
        return option;
    }

    static void checkPersonalData() { //check personal data function 
        System.out.println(" \n=== Personal Data === ");
        System.out.println("Position\t: " + (loggedInUser instanceof Admin ? "Admin" : "Employee"));
        System.out.println("Name\t\t: " + loggedInUser.getName());
        System.out.println("ID\t\t: " + loggedInUser.getId());
        System.out.println("Phone\t\t: " + loggedInUser.getPhone());
        System.out.println("Gender\t\t: " + (loggedInUser.getGender() == 1 ? "Male" : "Female"));
        
        System.out.println("\nPress [Enter] to Continue...");
        Scanner scanner = new Scanner(System.in); //just igonre it or you guys can help me how to improve it :)
        scanner.nextLine();
    }
}
