import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner cin = new Scanner(System.in);
        UserSystem system = new UserSystem();
        SignIn sIn = new SignIn(system);
        SignUp sUp = new SignUp(system);
        while (true) {
            System.out.println("----------------Registration----------------");
            System.out.print("1.SignIn\n2.SignUp\nEnter your choice: ");
            int choice = cin.nextInt();
            cin.nextLine();
            if (choice == 1) {
                sIn.loginUser();
                break;
            } else if (choice == 2) {
                sUp.registerUser();
            }
        }
    }
}