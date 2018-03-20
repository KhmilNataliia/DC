import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form extends JFrame{
    private JPanel panel1;
    private JButton Start;
    private JSlider slider1;
    private JButton Increase1;
    private JButton Increase2;
    private JButton Decrease1;
    private JButton Decrease2;
    private JTextField Pr1;
    private JTextField Pr2;
    private ThreadsManager threadsManager;

    public Form() {
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
        threadsManager = new ThreadsManager(slider1);

        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                threadsManager.start();
                Start.setEnabled(false);
                Increase1.setEnabled(true);
                Increase2.setEnabled(true);
                Decrease1.setEnabled(true);
                Decrease2.setEnabled(true);
            }
        });

        Increase1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = threadsManager.incPriorityT1();
                Pr1.setText(String.valueOf(p));
            }
        });

        Increase2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = threadsManager.incPriorityT2();
                Pr2.setText(String.valueOf(p));
            }
        });

        Decrease1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = threadsManager.decPriorityT1();
                Pr1.setText(String.valueOf(p));
            }
        });

        Decrease2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = threadsManager.decPriorityT2();
                Pr2.setText(String.valueOf(p));
            }
        });
    }
}
