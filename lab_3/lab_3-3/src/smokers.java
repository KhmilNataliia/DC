import java.util.Random;

public class smokers {
    private volatile int semaphoreTobacco = 0;
    private volatile int semaphoreMatch = 0;
    private volatile int semaphorePaper = 0;
    private volatile int twoOnTable = 0;


    private  class TobaccoThread extends Thread {

        @Override
        public void run() {
            while (true) {
                while (semaphoreTobacco != 1) {
                }
                if (twoOnTable == 1) {
                    System.out.println("thread with tobacco made cigarette and smokes...");
                    twoOnTable--;
                } else {
                    System.out.println("tobacco is put on the table.");
                }
                semaphoreTobacco--;
            }
        }
    }

    private  class MatchThread extends Thread {

        @Override
        public void run() {
            while (true) {
                while (semaphoreMatch != 1) {
                }
                if (twoOnTable == 1) {
                    System.out.println("thread with match made cigarette and smokes...");
                    twoOnTable--;
                } else {
                    System.out.println("match is put on the table.");
                }
                semaphoreMatch--;
            }
        }
    }

    private  class PaperThread extends Thread {

        @Override
        public void run() {
            while (true) {
                while (semaphorePaper != 1) {
                }
                if (twoOnTable == 1) {
                    System.out.println("thread with paper made cigarette and smokes...");
                    twoOnTable --;
                } else {
                    System.out.println("paper is put on the table.");
                }
                semaphorePaper --;
            }
        }
    }

    private class Manager extends Thread {

        @Override
        public void run() {
            while (true) {
                while (semaphorePaper == 1 || semaphoreMatch == 1 || semaphoreTobacco == 1 || twoOnTable == 1) {}
                Random random = new Random();
                int first = random.nextInt(3);
                while (first < 0) {
                    first = random.nextInt(3);
                }
                int second = random.nextInt(3);
                while (second < 0 || second == first) {
                    second = random.nextInt(3);
                }
                try {
                    Thread.sleep(2000);
                    switch (first) {
                        case 0: {
                            semaphoreTobacco++;
                            break;
                        }
                        case 1: {
                            semaphoreMatch++;
                            break;
                        }
                        case 2: {
                            semaphorePaper++;
                            break;
                        }
                    }
                     Thread.sleep(5000);
                    switch (second) {
                        case 0: {
                            semaphoreTobacco++;
                            break;
                        }
                        case 1: {
                            semaphoreMatch++;
                            break;
                        }
                        case 2: {
                            semaphorePaper++;
                            break;
                        }
                    }
                    while (semaphorePaper == 1 || semaphoreMatch == 1 || semaphoreTobacco == 1) {}
                    twoOnTable++;
                    if (first == 0) {
                        if (second == 1) {
                            semaphorePaper++;
                        } else {
                            semaphoreMatch++;
                        }
                    }
                    if (first == 1) {
                        if (second == 2) {
                            semaphoreTobacco++;
                        } else {
                            semaphorePaper++;
                        }
                    }
                    if (first == 2) {
                        if (second == 1) {
                            semaphoreTobacco++;
                        } else {
                            semaphoreMatch++;
                        }
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void demo() {
        Thread T1 = new Thread(new TobaccoThread());
        Thread T2 = new Thread(new MatchThread());
        Thread T3 = new Thread(new PaperThread());
        Thread T4 = new Thread(new Manager());

        T1.start();
        T2.start();
        T3.start();
        T4.start();
    }
}
