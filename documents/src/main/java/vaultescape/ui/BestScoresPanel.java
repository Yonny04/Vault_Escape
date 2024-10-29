package vaultescape.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionListener;

public class BestScoresPanel extends JPanel {
    public BestScoresPanel(ActionListener backListener) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(50, 100, 50, 100));
        setBackground(new Color(45, 45, 48));

        JLabel title = new JLabel("Best Scores", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setForeground(new Color(210, 170, 50)); 
        title.setBackground(new Color(210, 170, 50));  
        title.setOpaque(true);  

        String[] columnNames = {"Rank", "Score"};
        Object[][] data = {
            {"1", "SCORE1"},
            {"2", "SCORE2"},
            {"3", "SCORE3"},
            {"4", "SCORE4"},
            {"5", "SCORE5"}
        };

        JTable scoresTable = new JTable(data, columnNames);
        styleTable(scoresTable);
        scoresTable.setEnabled(false);
        scoresTable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        scoresTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(scoresTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(backListener);
        add(backButton, BorderLayout.SOUTH);
    }

    private void styleTable(JTable table){
        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(40);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.BOLD, 24));
        header.setBackground(new Color(82, 45, 61));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(100, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradient = new GradientPaint(0, 0, new Color(220, 180, 60), 0, getHeight(), Color.WHITE);
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}