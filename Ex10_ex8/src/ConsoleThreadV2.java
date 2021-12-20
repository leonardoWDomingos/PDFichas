import java.util.Scanner;

public class ConsoleThreadV2 extends Thread{
    private Scanner scanner;

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        String command;
        do{
            command = scanner.nextLine();
            System.out.println("Command V2: " + command);
        }while (!command.equals("exit"));
    }
}
