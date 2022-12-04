package snake;

import java.awt.Font;
import javax.swing.JLabel;

class Interface
{
    private int highscore;
    private int score;
    private JLabel scoreDisplay;
    
    Interface()
    {
        this.highscore = 0;
        this.score = 0;
        this.scoreDisplay = new JLabel("<html>Score: 0 | Highscore: 0</html>");
        this.scoreDisplay.setSize(700, 500);
        this.scoreDisplay.setFont(new Font("Serif", Font.PLAIN, 23));
        this.scoreDisplay.setLocation(110, -130);
        this.scoreDisplay.setVisible(true);
    }

    void point()
    {
        score++;
        this.scoreDisplay.setText("<html>Score: "+Integer.toString(score)+" | Highscore: "+Integer.toString(highscore));
    }
    
    Boolean updateHighscore()
    {
        Boolean state = score == highscore;
        if (state) highscore = score;
        return state;
    }

    JLabel getScoreDisplay() { return this.scoreDisplay; }
}
