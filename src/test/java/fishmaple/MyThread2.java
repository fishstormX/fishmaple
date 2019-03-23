package fishmaple;


    public class MyThread2 extends Thread {
        private TTT task;
        public MyThread2(TTT task) {
            super();
            this.task = task;
        }
        @Override
        public void run() {
            super.run();
            task.doLongTimeTask();
        }
    }

