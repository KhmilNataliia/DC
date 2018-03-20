import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Form extends JFrame{
    private JPanel panel1;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JButton start;
    private JProgressBar pBar1;
    private JProgressBar pBar2;
    private JProgressBar pBar3;
    private JProgressBar pBar4;
    private JProgressBar pBar6;
    private JProgressBar pBar5;
    private JProgressBar pBar7;
    private JProgressBar pBar8;
    private JCheckBox CheckBox1;
    private JCheckBox CheckBox2;
    private JCheckBox CheckBox3;
    private JCheckBox CheckBox4;
    private JCheckBox checkBox5;
    private ArrayList<JProgressBar> Bars;
    private ArrayList<JCheckBox> Boxes;
    private int Beans = 0;
    private int Parts = 0;
    private int Vp;
    private int Vs;
    private boolean found = false;

    private void search(int B, int P, JProgressBar bar) {
        Boxes.get(B-1).setSelected(false);
        for (int i = 1; i <= 10; i++) {
            try {
                if (B % 3 == 0) {
                    Thread.sleep(200);
                }
                if (B % 3 == 1) {
                    Thread.sleep(100);
                }
                if (B % 3 == 2) {
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (found) {
                Boxes.get(B-1).setSelected(true);
                return;
            }
            if (P == Vp && Vs == i) {
                found = true;
                JOptionPane.showMessageDialog(null, "Винни Пух найден на " + String.valueOf(P) +
                        " участке, в " + String.valueOf(i) + " секции, " + String.valueOf(B) + "-й стаей пчёл. Винни Пух наказан!");
                Boxes.get(B-1).setSelected(true);
                return;
            }
            bar.setValue(i);
        }
        Boxes.get(B-1).setSelected(true);
    }

    private class thread implements Runnable {
        private int b;

        public thread(int B) {
            b = B;
        }

        @Override
        public void run() {
                while (Bars.size() > 0 && !found) {
                    JProgressBar bar = Bars.remove(0);
                    search(b,Integer.parseInt(bar.getName()), bar);
                }
        }
    }

    private void reset() {
        Bars = new ArrayList<JProgressBar>();
        Boxes = new ArrayList<JCheckBox>();

        found = false;

        Beans = 0;
        Parts = 0;

        pBar1.setValue(0);
        pBar1.setVisible(false);
        pBar2.setValue(0);
        pBar2.setVisible(false);
        pBar3.setValue(0);
        pBar3.setVisible(false);
        pBar4.setValue(0);
        pBar4.setVisible(false);
        pBar5.setValue(0);
        pBar5.setVisible(false);
        pBar6.setValue(0);
        pBar6.setVisible(false);
        pBar7.setValue(0);
        pBar7.setVisible(false);
        pBar8.setValue(0);
        pBar8.setVisible(false);

        CheckBox1.setSelected(true);
        CheckBox1.setVisible(false);
        CheckBox2.setSelected(true);
        CheckBox2.setVisible(false);
        CheckBox3.setSelected(true);
        CheckBox3.setVisible(false);
        CheckBox4.setSelected(true);
        CheckBox4.setVisible(false);
        checkBox5.setSelected(true);
        checkBox5.setVisible(false);
    }

    public Form() {
        super("жестокие пчёлы...");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();

        Bars = new ArrayList<JProgressBar>();
        Boxes = new ArrayList<JCheckBox>();

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                if (Integer.parseInt(String.valueOf(spinner1.getValue())) > 1 && Integer.parseInt(String.valueOf(spinner1.getValue())) <= 5) {
                    Beans = Integer.parseInt(String.valueOf(spinner1.getValue()));
                } else {
                    JOptionPane.showMessageDialog(null,"количество стай пчёл должно быть в диапазоне (1;5]");
                    return;
                }
                if (Integer.parseInt(String.valueOf(spinner2.getValue())) > 1 && Integer.parseInt(String.valueOf(spinner2.getValue())) <= 8) {
                    Parts = Integer.parseInt(String.valueOf(spinner2.getValue()));
                } else {
                    JOptionPane.showMessageDialog(null,"количество участков леса должно быть в диапазоне (1;8]");
                    return;
                }
                pBar1.setVisible(true);
                Bars.add(pBar1);
                pBar2.setVisible(true);
                Bars.add(pBar2);
                if (Parts > 2) {
                    pBar3.setVisible(true);
                    Bars.add(pBar3);
                    if (Parts > 3) {
                        pBar4.setVisible(true);
                        Bars.add(pBar4);
                        if (Parts > 4) {
                            pBar5.setVisible(true);
                            Bars.add(pBar5);
                            if (Parts > 5) {
                                pBar6.setVisible(true);
                                Bars.add(pBar6);
                                if (Parts > 6) {
                                    pBar7.setVisible(true);
                                    Bars.add(pBar7);
                                    if (Parts > 7) {
                                        pBar8.setVisible(true);
                                        Bars.add(pBar8);
                                    }
                                }
                            }
                        }
                    }
                }
                CheckBox1.setVisible(true);
                Boxes.add(CheckBox1);
                CheckBox2.setVisible(true);
                Boxes.add(CheckBox2);
                if (Beans > 2) {
                    CheckBox3.setVisible(true);
                    Boxes.add(CheckBox3);
                    if (Beans > 3) {
                        CheckBox4.setVisible(true);
                        Boxes.add(CheckBox4);
                        if (Beans > 4) {
                            checkBox5.setVisible(true);
                            Boxes.add(checkBox5);
                        }
                    }
                }
                Random random = new Random();
                Vp = random.nextInt()%Parts;
                if (Vp < 0) { Vp = -Vp; }
                Vs = random.nextInt()%10;
                if (Vs < 0) { Vs = - Vs; }
                if (Vs == 0) { Vs = 10; }
                if (Vp == 0) { Vp = Parts; }
                    ArrayList<Thread> Ts = new ArrayList<Thread>();
                    for (int i = 1; i <= Beans; i++) {
                        Ts.add(new Thread(new thread(i)));
                    }
                    for (int i = 1; i <= Beans; i++) {
                        Ts.get(i-1).start();
                    }
            }
        });
    }
}
