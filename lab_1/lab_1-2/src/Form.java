import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form extends JFrame{
    private JPanel panel1;
    private JSlider slider1;
    private JButton Start2;
    private JButton Stop2;
    private JButton Stop1;
    private JButton Start1;
    private Thread Thread1;
    private Thread Thread2;
    private int semaphore = 0;

    private class threadTo10 implements Runnable {
        @Override
        public void run() {
            semaphore = 1;
            while (true) {
                if (Thread1.isInterrupted()) {
                    return;
                } else {
                    slider1.setValue(10);
                }
            }
        }
    }

    private class threadTo90 implements Runnable {
        @Override
        public void run() {
            semaphore = 1;
            while (true) {
                if (Thread2.isInterrupted()) {
                    return;
                } else {
                    slider1.setValue(90);
                }
            }
        }
    }

    public Form() {
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();

        Start1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (semaphore == 0) {
                    Thread1 = new Thread(new threadTo10());
                    Thread1.setPriority(1);
                    Stop2.setEnabled(false);
                    Thread1.start();
                } else {
                    JOptionPane.showMessageDialog(null,"another thread is working in critical section");
                    return;
                }
            }
        });

        Start2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (semaphore == 0) {
                    Thread2 = new Thread(new threadTo90());
                    Thread2.setPriority(10);
                    Stop1.setEnabled(false);
                    Thread2.start();
                } else {
                    JOptionPane.showMessageDialog(null,"another thread is working in critical section");
                    return;
                }
            }
        });

        Stop1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Thread1 != null && Thread1.isAlive()) {
                    Thread1.interrupt();
                    Stop2.setEnabled(true);
                    semaphore = 0;
                }
            }
        });

        Stop2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Thread2 != null && Thread2.isAlive()) {
                    Thread2.interrupt();
                    Stop1.setEnabled(true);
                    semaphore = 0;
                }
            }
        });
    }
}
