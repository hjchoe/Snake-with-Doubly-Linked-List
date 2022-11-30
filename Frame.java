package snake;

import javax.swing.JFrame;
import java.awt.Dimension;

class Frame extends JFrame
{
    private Canvas panel;

    Frame()
    {
        panel = new Canvas();

        this.setTitle("Snake");
        this.setPreferredSize(new Dimension(700, 700));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.pack();
		this.setLocationRelativeTo(null);
        this.setVisible(true);
		this.setFocusable(false);
		this.setLayout(null);

        this.add(panel);
    }

    Canvas getPanel() { return panel; }
}
