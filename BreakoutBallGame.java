import javax.swing.*;

public class BreakoutBallGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePlay gamePlay = new GamePlay();
        frame.setBounds(200,100,700,600);
        frame.setTitle("BREAKOUT BALL");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(gamePlay);
        frame.setVisible(true);
    }
}
