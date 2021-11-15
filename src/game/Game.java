package game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Game extends JFrame implements ActionListener {
    private JFrame frame;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem smallTable;
    private JMenuItem mediumTable;
    private JMenuItem bigTable;

    private JPanel upperPanel;
    private JLabel turnDisplay;

    private JPanel gamePanel;
    private JButton[] buttons;

    private boolean xTurn;
    private int gameType = 6;

    public Game() {
        declarations();

        menu.add(smallTable);
        menu.add(mediumTable);
        menu.add(bigTable);
        menuBar.add(menu);
        menuBar.setBackground(Color.white);
        smallTable.setBackground(Color.white);
        mediumTable.setBackground(Color.white);
        bigTable.setBackground(Color.white);


        turnDisplay();
        turnDisplay.setHorizontalAlignment(JLabel.CENTER);
        turnDisplay.setVerticalAlignment(JLabel.CENTER);
        turnDisplay.setPreferredSize(new Dimension(614, 140));
        turnDisplay.setFont(new Font("Arial", Font.BOLD, 40));
        turnDisplay.setForeground(new Color(255,255,255));
        upperPanel.add(turnDisplay);
        upperPanel.setBackground(new Color(75, 64, 99));
        upperPanel.setBounds(0,0,600,140);

        gamePanel.setBounds(0,140,600,600);
        gamePanel.setBackground(Color.blue);

        frame.add(upperPanel);
        frame.add(gamePanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(menuBar);
        frame.setSize(614, 800);
        frame.setVisible(true);
    }

    private void declarations() {
        frame = new JFrame();
        menuBar = new JMenuBar();
        menu = new JMenu("Table sizes");
        smallTable = new JMenuItem("6X6");
        mediumTable = new JMenuItem("10X10");
        bigTable = new JMenuItem("14X14");
        upperPanel = new JPanel();
        turnDisplay = new JLabel();
        gamePanel = new JPanel();
        buildGamePanel();
    }

    private void turnDisplay() {
        Random rand = new Random();
        xTurn = rand.nextBoolean();
        turnDisplay.setText(xTurn ? "X-s turn" : "O-s turn");
    }

    private void buildGamePanel() {
        buttons = new JButton[ (int) Math.pow(gameType, 2) ];

        gamePanel.setLayout( new GridLayout(gameType, gameType));

        for (int i = 0; i < Math.pow(gameType, 2); i++) {
            System.out.println("button");
            buttons[i] = new JButton();
            buttons[i].setOpaque(true);
            buttons[i].setBackground(new Color(111, 95, 144));
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(75, 64, 99)));
            buttons[i].addActionListener(this);
            gamePanel.add(buttons[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton performed = Arrays.stream(buttons).filter((JButton b) -> b == e.getSource()).collect(Collectors.toList()).get(0);
        performed.setText(xTurn && performed.getText() == "" ? "X" : "O");
        performed.setForeground(xTurn && performed.getText() == "" ? new Color(255, 123, 137) : new Color(165, 202, 210));
        xTurn = !xTurn;
    }
}
