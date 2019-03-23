package fishmaple;

public class MyThread1 extends Thread {
    private TTT task;
    public MyThread1(TTT task) {
        super();
        this.task = task;
    }
    @Override
    public void run() {
        super.run();
        synchronized ("mydog") {
            task.doLongTimeTask();
        }
    }
}