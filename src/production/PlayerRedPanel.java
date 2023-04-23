package production;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PlayerRedPanel extends JPanel{
    private static JRadioButton redPlayerS;
    private static JRadioButton redPlayerO;
    private static JRadioButton redHuman;
    private static JRadioButton redComputer;
    private String RedCurrentTypeOfPlayer;

    public PlayerRedPanel() {
        super(new BorderLayout());

        JLabel redPlayerLabel = new JLabel("Red Player");
        redPlayerS = new JRadioButton("S");
        redPlayerO = new JRadioButton("O",true);

        ButtonGroup redPlayerGroup = new ButtonGroup();
        redPlayerGroup.add(redPlayerS);
        redPlayerGroup.add(redPlayerO);

        //human and computer radio button
        redHuman = new JRadioButton("Human");
        redComputer = new JRadioButton("Computer");

        ButtonGroup activePlayerRed = new ButtonGroup();
        activePlayerRed.add(redComputer);
        activePlayerRed.add(redHuman);

        redHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                RedCurrentTypeOfPlayer = redHuman.getText();
                System.out.println("Red player's choice: " + RedCurrentTypeOfPlayer);
            }
        });
        redComputer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RedCurrentTypeOfPlayer = redComputer.getText();
                System.out.println("Red player's choice: " + RedCurrentTypeOfPlayer);
            }
        });

        JPanel redPlayerButtonsPanel = new JPanel();
        redPlayerButtonsPanel.setLayout(new BoxLayout(redPlayerButtonsPanel, BoxLayout.PAGE_AXIS)); // Use BoxLayout for the vertical layout
        redPlayerButtonsPanel.add(redPlayerLabel);
        redPlayerButtonsPanel.add(redHuman);
        redPlayerButtonsPanel.add(redPlayerS);
        redPlayerButtonsPanel.add(redPlayerO);
        redPlayerButtonsPanel.add(redComputer);

        add(redPlayerButtonsPanel, BorderLayout.CENTER);
    }
    public String getCurrentRedPlayer() {
        return RedCurrentTypeOfPlayer;
    }
    public static JRadioButton getRedPlayerS() {
        return redPlayerS;
    }
    public static JRadioButton getRedPlayerO() {
        return redPlayerO;
    }
    public static JRadioButton getRedComputer(){
        return redComputer;
    }
    public static JRadioButton getRedHuman(){return redHuman;}
}
