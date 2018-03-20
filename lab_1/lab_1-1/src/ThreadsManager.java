import javax.swing.*;

public class ThreadsManager {
    private JSlider Slider;
    private int priorityT1 = 1;
    private int priorityT2 = 1;
    private Thread Thread1;
    private Thread Thread2;

    public ThreadsManager(JSlider slider) {
        Slider = slider;
    }

    public int incPriorityT1() {
        if (priorityT1 < 10) {
            priorityT1++;
            Thread1.setPriority(priorityT1);
        }
        return priorityT1;
    }

    public int incPriorityT2() {
        if (priorityT2 < 10) {
            priorityT2++;
            Thread2.setPriority(priorityT2);
        }
        return priorityT2;
    }

    public int decPriorityT1() {
        if (priorityT1 > 1) {
            priorityT1--;
            Thread1.setPriority(priorityT1);
        }
        return priorityT1;
    }

    public int decPriorityT2() {
        if (priorityT2 > 1) {
            priorityT2--;
            Thread2.setPriority(priorityT2);
        }
        return priorityT2;
    }

    private class threadTo10 implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (Slider) {
                    Slider.setValue(10);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class threadTo90 implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (Slider) {
                    Slider.setValue(90);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void start() {
        Thread1 = new Thread(new threadTo10());
        Thread2 = new Thread(new threadTo90());

        Thread1.start();
        Thread2.start();
    }

}
