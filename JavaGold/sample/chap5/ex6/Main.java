class Staff {
    private String name;
    private boolean taskDone;
    public Staff(String name, boolean taskDone) {
        this.name = name;
        this.taskDone = taskDone;
    }
    public void operate(Staff other) {
        while (!other.isTaskDone()) {
            System.out.println(other.getName() + " is waiting for "
                    + this.name + " to finish the task...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
        System.out.println(this.name + " finished the task!");
        this.taskDone = true;
    }
    public String getName() { return this.name; }
    public boolean isTaskDone() { return this.taskDone; }
}
public class Main {
    public static void main(String[] args) {
        Staff firstStaff = new Staff("Duke", false);
        Staff secondStaff = new Staff("Anna", false);
        new Thread(() -> firstStaff.operate(secondStaff)).start();
        new Thread(() -> secondStaff.operate(firstStaff)).start();
    }
}