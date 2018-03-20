import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Form extends JFrame{
    private JProgressBar storageBar;
    private JProgressBar takenBar;
    private JTextField priceField;
    private JButton start;
    private JPanel panel1;
    private JProgressBar trackBar;
    private JButton restartButton;

    private int trackWeight = 0;
    private int prevTruckWeight = 0;
    private int storageWeight = 600;
    private int takenWeight = 0;
    private int price = 0;
    private Random random;
    private int mutex = 1;
    private boolean full = false;
    private Thread Thread1;
    private Thread Thread2;
    private Thread Thread3;


    private void Ivanov() {
        if (mutex == 1) {
            mutex = 0;
            int w = random.nextInt() % 50;
            if (w < 0) { w = -w; }
            if (storageWeight >= w && takenWeight + w <= 50) {
                storageWeight -= w;
                takenWeight += w;
                storageBar.setValue(storageWeight);
                storageBar.setString(String.valueOf(storageWeight));
                takenBar.setValue(takenWeight);
                takenBar.setString(String.valueOf(takenWeight));
            }
            mutex = 1;
        }
    }

    private void Petrov() {
        if (mutex == 1) {
            mutex = 0;
            if (takenWeight > 0) {
                int w;
                if (takenWeight != 1) {
                  w  = random.nextInt() % takenWeight;
                  if (w < 0) { w = -w; }
                } else {
                    w = 1;
                }
                if (trackWeight + w > 250) {
                    w = 250 - trackWeight;
                    full = true;
                }
                takenWeight -= w;
                trackWeight += w;
                takenBar.setValue(takenWeight);
                takenBar.setString(String.valueOf(takenWeight));
                trackBar.setValue(trackWeight);
                trackBar.setString(String.valueOf(trackWeight));
            }
            mutex = 1;
        }
    }

    private void Necheporchuk() {
        int p = random.nextInt()%100;
        if (p < 0) { p = -p; }
        int w = trackWeight;
        price += p * (w - prevTruckWeight);
        prevTruckWeight = w;
        priceField.setText(String.valueOf(price) + "UAN");
    }

    private class thread1 implements Runnable {

        @Override
        public void run() {
            while (!full) {
                Ivanov();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (mutex == 0) {}
            storageWeight += takenWeight;
            takenWeight = 0;
            storageBar.setValue(storageWeight);
            takenBar.setValue(takenWeight);
            takenBar.setString(String.valueOf(takenWeight));
            storageBar.setString(String.valueOf(storageWeight));
        }
    }

    private class thread2 implements Runnable {

        @Override
        public void run() {
            while (!full) {
                Petrov();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class thread3 implements Runnable {

        @Override
        public void run() {
            while (!full) {
               Necheporchuk();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Form() {
        super("как прапорщики тирили имущество со склада...");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();

        random = new Random();

        Thread1 = new Thread(new thread1());
        Thread2 = new Thread(new thread2());
        Thread3 = new Thread(new thread3());

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setEnabled(false);
                Thread1.start();
                Thread2.start();
                Thread3.start();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trackWeight = 0;
                prevTruckWeight = 0;
                storageWeight = 600;
                takenWeight = 0;
                price = 0;
                mutex = 1;
                full = false;
                trackBar.setValue(0);
                takenBar.setValue(0);
                storageBar.setValue(600);
                priceField.setText("0UAN");
                takenBar.setString(String.valueOf(takenWeight));
                storageBar.setString(String.valueOf(storageWeight));
                trackBar.setString(String.valueOf(trackWeight));
                Thread1 = new Thread(new thread1());
                Thread2 = new Thread(new thread2());
                Thread3 = new Thread(new thread3());
                start.setEnabled(true);
            }
        });
    }
}
