import java.util.Scanner;

public class Staff {
    protected String id;
    protected String password;
    protected String name;
    protected String phone;
    protected int gender;
    

    public Staff(String id, String password, String name, String phone, int gender) {//staff is super class
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
    }

    //getter setter
    public String getId() {
        return id;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String newPhone) {
        this.phone = newPhone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int newGender) {
        this.gender = newGender;
    }
    

    
    public void updateData(Scanner scanner) {
        System.out.println("=== Update Employee Data ===");
        System.out.println("=== leave blank data will not change ===");

    
        System.out.print("Enter new Name\t\t: ");// name update with no any validation
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            setName(newName);
        }
    
        while (true) {
            System.out.print("Enter new Password\t: ");// Password update with 8-digit validation
            String newPassword = scanner.nextLine().trim();
            if (newPassword.isEmpty()) break;
            if (newPassword.matches("\\d{8}")) {
                setPassword(newPassword);
                break;
            } else {
                System.out.println("!! Invalid password. It must be exactly 8 digits. Try again or leave blank.");
            }
        }

        while (true) {
            System.out.print("Enter new Phone\t\t: "); // Phone update with 11-digit validation
            String newPhone = scanner.nextLine().trim();
            if (newPhone.isEmpty()) break;
            if (newPhone.matches("\\d{11}")) {
                setPhone(newPhone);
                break;
            } else {
                System.out.println("!! Invalid phone. It must be exactly 11 digits.");
            }
        }
    
        while (true) {
            System.out.print("Enter new Gender (1=M / 2=F) : "); // Gender update with 1/2 validation (it also can be improved to f , m selection)
            String genderInput = scanner.nextLine().trim();
            if (genderInput.isEmpty()) break;
            if (genderInput.equals("1") || genderInput.equals("2")) {
                setGender(Integer.parseInt(genderInput));
                break;
            } else {
                System.out.println("!! Invalid gender input. Please enter 1 or 2.");
            }
        }
    
        System.out.println("=== Employee data updated! ===");
    }
    

}
