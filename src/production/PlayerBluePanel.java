package production;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PlayerBluePanel extends JPanel {
    private static JRadioButton bluePlayerS;
    private static JRadioButton bluePlayerO;
    private static JRadioButton blueComputer;
    private static JRadioButton blueHuman;
    private String BlueCurrentPlayerType;
    public String getCurrentBluePlayer() {
        return BlueCurrentPlayerType;
    }
    public PlayerBluePanel() {
        super(new BorderLayout());

        JLabel bluePlayer = new JLabel("Blue Player");
        bluePlayerS = new JRadioButton("S",true);
        bluePlayerO = new JRadioButton("O");

        // S or O radio Buttons
        ButtonGroup bluePlayerGroup = new ButtonGroup();
        bluePlayerGroup.add(bluePlayerS);
        bluePlayerGroup.add(bluePlayerO);

        //Radio buttons for human and computer
        blueHuman = new JRadioButton("Human");
        blueComputer = new JRadioButton("Computer");

        //Human or computer  group Buttons
        ButtonGroup activePlayerBlue= new ButtonGroup();
        activePlayerBlue.add(blueHuman);
        activePlayerBlue.add(blueComputer);

        blueHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BlueCurrentPlayerType = blueHuman.getText();
                System.out.println("Blues player's choice: "+ BlueCurrentPlayerType);
            }
        });
        blueComputer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BlueCurrentPlayerType = blueComputer.getText();
                System.out.println("Blues player's choice: "+ BlueCurrentPlayerType);
            }
        });

        JPanel bluePlayerButtonsPanel = new JPanel();
        bluePlayerButtonsPanel.setLayout(new BoxLayout(bluePlayerButtonsPanel, BoxLayout.PAGE_AXIS));
        bluePlayerButtonsPanel.add(bluePlayer);
        bluePlayerButtonsPanel.add(blueHuman);
        bluePlayerButtonsPanel.add(bluePlayerS);
        bluePlayerButtonsPanel.add(bluePlayerO);
        bluePlayerButtonsPanel.add(blueComputer);

        add(bluePlayerButtonsPanel, BorderLayout.WEST);
    }
    public static JRadioButton getBluePlayerS() {
        return bluePlayerS;
    }
    public static JRadioButton getBluePlayerO() {
        return bluePlayerO;
    }
    public static JRadioButton getBlueComputer(){
        return blueComputer;
    }
    public static JRadioButton getBlueHuman(){
        return blueHuman;
    }
}
