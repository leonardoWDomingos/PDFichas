import java.util.Scanner;

public class ConsoleThreadV1 implements Runnable{
    private boolean stopThread = false;

    public boolean getStopThread(){
        boolean tempStopThread;
        synchronized (this){
            tempStopThread = stopThread;
        }
        return tempStopThread;
    }

    public void setStopThread(boolean stopThread){
        synchronized (this){
            this.stopThread = stopThread;
        }
    }

    private Scanner scanner;
    @Override
    public void run() {
        scanner = new Scanner(System.in);
        String command;

        do{
            command = scanner.nextLine();
            System.out.println("Command V1: " + command);
        }while (!command.equals("exit"));

        setStopThread(true);
    }
}
