
package gui;

import javax.swing.*;
import java.awt.*;

public class BattleGUI extends JFrame {
    private JLabel roundLabel;
    private JLabel topicLabel;
    private JTextArea rapperAText;
    private JTextArea rapperBText;
    private JLabel winnerLabel;
    private JLabel scoreALabel;
    private JLabel scoreBLabel;

    public BattleGUI() {
        setTitle("🔥 Batalha da AldeIA 🔥");
        setSize(700, 500);
        setLayout(new BorderLayout());

        JPanel scorePanel = new JPanel(new GridLayout(1, 2));
        scorePanel.setBackground(Color.BLACK);
        scoreALabel = new JLabel("🎤 Rapper A: 0", SwingConstants.CENTER);
        scoreBLabel = new JLabel("🎤 Rapper B: 0", SwingConstants.CENTER);
        scoreALabel.setForeground(Color.CYAN);
        scoreBLabel.setForeground(Color.ORANGE);
        scoreALabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        scoreBLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        scorePanel.add(scoreALabel);
        scorePanel.add(scoreBLabel);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(30, 30, 30));
        roundLabel = new JLabel("Round: 0", SwingConstants.CENTER);
        topicLabel = new JLabel("Tema: ", SwingConstants.CENTER);
        roundLabel.setForeground(Color.WHITE);
        topicLabel.setForeground(Color.LIGHT_GRAY);
        roundLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        topicLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        topPanel.add(roundLabel);
        topPanel.add(topicLabel);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        rapperAText = new JTextArea("Rapper A rima: ");
        rapperBText = new JTextArea("Rapper B rima: ");
        styleTextArea(rapperAText, new Color(20, 20, 20), Color.CYAN);
        styleTextArea(rapperBText, new Color(20, 20, 20), Color.ORANGE);
        centerPanel.add(new JScrollPane(rapperAText));
        centerPanel.add(new JScrollPane(rapperBText));

        winnerLabel = new JLabel("🎯 Vencedor: ", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        winnerLabel.setOpaque(true);
        winnerLabel.setBackground(new Color(50, 50, 50));
        winnerLabel.setForeground(Color.GREEN);

        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.add(scorePanel, BorderLayout.NORTH);
        northWrapper.add(topPanel, BorderLayout.CENTER);

        add(northWrapper, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(winnerLabel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void styleTextArea(JTextArea area, Color bg, Color fg) {
        area.setBackground(bg);
        area.setForeground(fg);
        area.setFont(new Font("Monospaced", Font.PLAIN, 16));
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);
    }

    public void updateRound(int round) {
        roundLabel.setText("🎵 Round: " + round);
    }

    public void updateTopic(String topic) {
        topicLabel.setText("🎯 Tema: " + topic);
    }

    public void updateRhymeA(String rhyme) {
        rapperAText.setText("Rapper A rima: " + rhyme);
    }

    public void updateRhymeB(String rhyme) {
        rapperBText.setText("Rapper B rima: " + rhyme);
    }

    public void updateScores(int scoreA, int scoreB) {
        scoreALabel.setText("🎤 Rapper A: " + scoreA);
        scoreBLabel.setText("🎤 Rapper B: " + scoreB);
    }

    public void showWinner(String winner) {
        winnerLabel.setText("🏆 Vencedor: " + winner + " 🏆");
        if (winner.contains("A")) {
            winnerLabel.setForeground(Color.CYAN);
        } else if (winner.contains("B")) {
            winnerLabel.setForeground(Color.ORANGE);
        } else {
            winnerLabel.setForeground(Color.LIGHT_GRAY);
        }
    }
}
