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
    private JFrame alert;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem smallTable;
    private JMenuItem mediumTable;
    private JMenuItem bigTable;
    private JMenuItem clearTable;

    private JPanel upperPanel;
    private JLabel turnDisplay;

    private JPanel gamePanel;
    private JButton[] buttons;

    private boolean xTurn;
    private int performedActions = 0;
    private int gameType = 6;
    char[][] playerTable;

    public Game() {
        declarations();

        menu.add(smallTable);
        menu.add(mediumTable);
        menu.add(bigTable);
        menuBar.add(menu);
        menuBar.add(clearTable);
        menuBar.setBackground(Color.white);
        smallTable.setBackground(Color.white);
        mediumTable.setBackground(Color.white);
        bigTable.setBackground(Color.white);
        clearTable.setBackground(Color.white);
        smallTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameType = 6;
                buildGamePanel();
            }
        });
        mediumTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameType = 10;
                buildGamePanel();
            }
        });
        bigTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameType = 14;
                buildGamePanel();
            }
        });
        clearTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });


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
        gamePanel.setBackground(new Color(75, 64, 99));

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
        clearTable = new JMenuItem("Clear table");
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
        gamePanel.removeAll();
        for (int i = 0; i < Math.pow(gameType, 2); i++) {
            buttons[i] = new JButton();
            buttons[i].setOpaque(true);
            buttons[i].setBackground(new Color(111, 95, 144));
            buttons[i].setFont(new Font("Arial", Font.BOLD, 30));
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(75, 64, 99)));
            buttons[i].addActionListener(this);
            gamePanel.add(buttons[i]);
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void clear() {
        buildGamePanel();
        turnDisplay();
        JOptionPane.showMessageDialog(alert, "Game cleared! have fun!");
    }

    private void checkHabitat(int id) {

    }

    private int horizontalCheck(int counter,int iterate, boolean a1, boolean a2, int start01, int start02) {
        if(playerTable[start01][start02-iterate] == playerTable[start01][start02] && a1 && start02-iterate >= 0) counter++;
        else a1 = false;

        if(playerTable[start01][start02+iterate] == playerTable[start01][start02] && a2 && start02+iterate <= playerTable[start01].length) counter++;
        else  a2 = false;

        if(a1 || a2) return horizontalCheck(counter, iterate+1, a1, a2, start01, start02);
        else return counter;
    }

    private int verticalCheck(int counter,int iterate, boolean a1, boolean a2, int start01, int start02) {
        if(playerTable[start01-iterate][start02] == playerTable[start01][start02] && a1 && start01-iterate >= 0) counter++;
        else a1 = false;

        if(playerTable[start01+iterate][start02] == playerTable[start01][start02] && a2 && start01+iterate <= playerTable.length) counter++;
        else a2 = false;

        if(a1 || a2) return horizontalCheck(counter, iterate+1, a1, a2, start01, start02);
        else return counter;

    }

    private int lTRCheck(int counter, boolean a1, boolean a2, int start01, int start02) {
        return -1;
    }

    private int rTLCheck(int counter, boolean a1, boolean a2, int start01, int start02) {
        return -1;
    }

    private int buttonsId(JButton b) {
        int id = 0;
        for (JButton button: buttons) {
            if(b == button) return id;
            id++;
        }
        return -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton performed = Arrays.stream(buttons).filter((JButton b) -> b == e.getSource()).collect(Collectors.toList()).get(0);
        if(performed.getText().equals("")) {
            turnDisplay.setText(xTurn ? "O-s turn" : "X-s turn");
            performed.setText(xTurn ? "X" : "O");
            performed.setForeground(xTurn ? new Color(255, 123, 137) : new Color(165, 202, 210));
            xTurn = !xTurn;
            performedActions++;
            checkHabitat(buttonsId(performed));
        }
    }
}
