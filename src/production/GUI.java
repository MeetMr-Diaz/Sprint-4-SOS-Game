package production;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
public class GUI extends JFrame{
    private final PlayerBluePanel playerBluePanel;
    private final PlayerRedPanel playerRedPanel;
    public static int CELL_SIZE = 100;
    public static final int GRID_WIDTH = 2;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final int CELL_PADDING = CELL_SIZE / 15;
    public static final int SYMBOL_STROKE_WIDTH = 8;
    private JLabel gameStatusBar;
    private BoardCanvas BoardCanvas;
    private boolean flag;
    private final JFrame frame;
    private final SOSGame game;
    private JRadioButton bluePlayerS;
    private JRadioButton bluePlayerO;
    private JRadioButton blueComputer;
    private JRadioButton blueHuman;
    private JRadioButton redPlayerS;
    private JRadioButton redPlayerO;
    private JRadioButton redComputer;
    private JRadioButton redHuman;
    public GUI(SOSGame game) {
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SOS game");
        setSize(850,850);

        playerBluePanel = new PlayerBluePanel();
        playerRedPanel = new PlayerRedPanel();

        setContentPane();
        pack();
        setVisible(true);
        frame = this;
        flag = false;
    }
    private void setContentPane() {

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BorderLayout());

        BoardCanvas = new BoardCanvas();
        BoardCanvas.setPreferredSize(new Dimension(CELL_SIZE * game.size, CELL_SIZE * game.size));
        BoardCanvas.setBounds(200, 100, 350, 350);
        boardPanel.add(BoardCanvas,BorderLayout.NORTH);

        gameStatusBar = new JLabel("  ");
        gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        gameStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0));
        boardPanel.add(gameStatusBar,BorderLayout.SOUTH);
        gameStatusBar.setPreferredSize(new Dimension(20,75));

        JPanel gameChoicePanel = new JPanel();
        gameChoicePanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 5));
        JLabel sosLabel = new JLabel("SOS");
        JRadioButton SimpleBtn = new JRadioButton("Simple game",true);
        JRadioButton GeneralBtn = new JRadioButton("General Game");
        JLabel boardLabel = new JLabel("Board Size");

        JTextArea sizeInput = new JTextArea();
        sizeInput.setPreferredSize(new Dimension(20,20));

        ButtonGroup sosGroup = new ButtonGroup();
        sosGroup.add(SimpleBtn);
        sosGroup.add(GeneralBtn);

        SimpleBtn.addActionListener(modeActionListener(SOSGame.GameModeType.Simple, game));
        GeneralBtn.addActionListener(modeActionListener(SOSGame.GameModeType.General, game));

        JPanel westPanel =new JPanel(new BorderLayout());
        westPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 5));

        blueComputer= PlayerBluePanel.getBlueComputer();
        bluePlayerS = PlayerBluePanel.getBluePlayerS();
        bluePlayerO = PlayerBluePanel.getBluePlayerO();
        blueHuman = PlayerBluePanel.getBlueHuman();
        westPanel.add(playerBluePanel, BorderLayout.CENTER);
        westPanel.setPreferredSize(new Dimension(120,250));

        JPanel eastSide = new JPanel(new BorderLayout());
        eastSide.setBorder(BorderFactory.createEmptyBorder(50, 20, 80, 15));
        redPlayerS = PlayerRedPanel.getRedPlayerS();
        redPlayerO = PlayerRedPanel.getRedPlayerO();
        eastSide.setPreferredSize(new Dimension(130,250));
        eastSide.add(playerRedPanel,BorderLayout.NORTH);
        redHuman = PlayerRedPanel.getRedHuman();
        redComputer = PlayerRedPanel.getRedComputer();

        gameChoicePanel.add(sosLabel);
        gameChoicePanel.add(SimpleBtn);
        gameChoicePanel.add(GeneralBtn);
        gameChoicePanel.add(boardLabel);
        gameChoicePanel.add(sizeInput);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameChoicePanel, BorderLayout.NORTH);
        contentPane.add(boardPanel,BorderLayout.CENTER);
        contentPane.add(eastSide,BorderLayout.EAST);
        contentPane.add(westPanel,BorderLayout.WEST);

        JButton newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("New Game")){
                    frame.dispose();
                    SwingUtilities.invokeLater(new Runnable(){
                        @Override
                        public void run() {

                            int size = 3; // default size to 3
                            String input = sizeInput.getText();

                            if(input != null && !input.isEmpty()) {
                                size = Integer.parseInt(input);
                            }

                            if(validSize(size)){
                                CELL_SIZE = 300 / size;
                                BoardCanvas.setPreferredSize(new Dimension(CELL_SIZE * size, CELL_SIZE * size));
                                BoardCanvas.setSize(BoardCanvas.getPreferredSize());
                                BoardCanvas.repaint();

                                frame.setPreferredSize(new Dimension(CELL_SIZE * size +200, CELL_SIZE * size + 200));
                                frame.pack();
                                new GUI(new SOSGame(size));
                            }
                            else {

                                JOptionPane.showMessageDialog(sizeInput, "Please choose a valid size from 3-9", "Invalid game size", JOptionPane.ERROR_MESSAGE);
                                new GUI(new SOSGame(3));
                            }
                        }
                    });
                }
            }
            public boolean validSize(int size) {
                return size >= 3 && size <= 9;
            }
        });
        eastSide.add(newGameBtn,BorderLayout.SOUTH);
    }
    private static ActionListener modeActionListener(SOSGame.GameModeType gameModeType, SOSGame game) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setCurrentGameType(gameModeType);
            }
        };
    }
    class BoardCanvas extends JPanel {
        public  BoardCanvas() {
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    String bluePlayer = playerBluePanel.getCurrentBluePlayer();
                    String redPlayer = playerRedPanel.getCurrentRedPlayer();
                    int rowSelected = e.getY() / CELL_SIZE;
                    int colSelected = e.getX() / CELL_SIZE;
                    int type;

                    if (game.getGameState() == SOSGame.GameState.PLAYING) {
                        if (game.getTurn()=='B') {
                            type = bluePlayerS.isSelected() ? 0 : 1;
                            if (bluePlayer.equals("Human")){
                                game.makeMove(rowSelected, colSelected, type);
                                if (redPlayer.equals("Computer")){
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    }
                                    game.makeRandomMove(type == 0 ? 1 : 0);
                                }
                            } else if (bluePlayer.equals("Computer")){
                                game.makeRandomMove(type);
                            }
                        }
                        else if (game.getTurn()=='R') {
                            type = redPlayerS.isSelected() ? 0 : 1;
                            if (redPlayer.equals("Human")) {
                                game.makeMove(rowSelected, colSelected, type);
                            } else if (redPlayer.equals("Computer")) {
                                game.makeRandomMove(type);
                            }

                            if (bluePlayer.equals("Computer")){
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                                 game.makeRandomMove(type == 0 ? 1 : 0);
                            }
                        }

                    } else {
                        game.resetGame();
                    }
                    repaint();
                }
            });
        }
            @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoardSymbol(g);
            drawWinningLines(g);
            printStatusBar();
        }
        private void drawGridLines(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < game.getTotalRows(); ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF, CELL_SIZE * game.getTotalRows() - 1, GRID_WIDTH,
                        GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < game.getTotalColumns(); ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH,
                        CELL_SIZE * game.getTotalColumns() - 1, GRID_WIDTH, GRID_WIDTH);
            }
        }
        private void drawBoardSymbol(Graphics g) {
            Graphics2D symbolType = (Graphics2D) g;
            symbolType.setColor(Color.BLACK);
            symbolType.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < game.getTotalRows(); ++row) {
                for (int col = 0; col < game.getTotalColumns(); ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (game.getCell(row, col) == SOSGame.Cell.S) {

                        symbolType.setColor(Color.BLUE);
                        symbolType.drawArc(x1 + CELL_SIZE / 5, y1, CELL_SIZE / 2 - CELL_PADDING, CELL_SIZE / 2 - CELL_PADDING,
                                60, 210);
                        symbolType.drawArc(x1 + CELL_SIZE / 5, y1 + CELL_SIZE / 2 - CELL_PADDING, CELL_SIZE / 2 - CELL_PADDING,
                                CELL_SIZE / 2 - CELL_PADDING, 240, 210);
                    } else if (game.getCell(row, col) == SOSGame.Cell.O) {
                        symbolType.setColor(Color.RED);
                        int symbolSize = (int) (CELL_SIZE * 0.5);//cell ratio
                        symbolType.drawOval(x1 + (CELL_SIZE - symbolSize) / 20, y1 + (CELL_SIZE - symbolSize) / 100, symbolSize, symbolSize);
                    }
                }
            }
        }
        private void drawWinningLines(Graphics g) {
            ArrayList<ArrayList<Integer>> info = game.getSosInfo();
            Graphics2D winningLines = (Graphics2D) g;
            if (info == null)
                return;
            for (ArrayList<Integer> it : info) {
                if (it.size() > 1) {
                    if (it.get(0) == 0)
                        winningLines.setColor(Color.BLUE); // for line to mark sos
                    else
                        winningLines.setColor(Color.RED);
                    for (int i = 1; i < it.size(); i += 4) {
                        int x1 = it.get(i + 1) * CELL_SIZE + CELL_SIZE / 2;
                        int y1 = it.get(i) * CELL_SIZE + CELL_SIZE / 2;
                        int x2 = it.get(i + 3) * CELL_SIZE + CELL_SIZE / 2;
                        int y2 = it.get(i + 2) * CELL_SIZE + CELL_SIZE / 2;
                        winningLines.drawLine(x1, y1, x2, y2);
                    }
                }
            }
        }
        private void printStatusBar() {
            if (game.getGameState() == SOSGame.GameState.PLAYING) {

                gameStatusBar.setForeground(Color.BLACK);
                if (game.getTurn() == 'B') {
                    gameStatusBar.setText("Blue Player's Turn");
                } else {
                    gameStatusBar.setText("Red Player's Turn");
                }
            } else if (game.getGameState() == SOSGame.GameState.DRAW) {
                gameStatusBar.setForeground(Color.BLACK);
                gameStatusBar.setText("It's a Draw! Click to play again.");
                if(!flag){
                    flag = true;
                }
            } else if (game.getGameState() == SOSGame.GameState.BLUE_WON) {
                gameStatusBar.setForeground(Color.BLUE);//for the status print
                gameStatusBar.setText("Blue player Won! Click to play again.");
                if(!flag){
                    flag=true;
                }
            } else if (game.getGameState() == SOSGame.GameState.RED_WON) {
                gameStatusBar.setForeground(Color.RED);// for the status print
                gameStatusBar.setText("Red player Won! Click to play again.");
                if(!flag){
                    flag = true;
                }
            }
        }
    }
   public static void main(String[] args) {
       GUI gui = new GUI(new SOSGame(3));
   }
}