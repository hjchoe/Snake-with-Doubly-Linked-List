package snake;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.Color;
import java.awt.Font;

class Frame extends JFrame
{
    private MainMenu menu;
    private Canvas panel;
    private ImageIcon sunImage;
    private JLabel sun;
    private Clip menumusic;
    private JButton replayButton;

    Frame()
    {
        menu = new MainMenu();
        panel = new Canvas();
        sunImage = new ImageIcon(getClass().getResource("images/sun.png"));
        Image image = sunImage.getImage(); // transform it 
        Image newimg = image.getScaledInstance(69, 69,  java.awt.Image.SCALE_SMOOTH);
        sunImage = new ImageIcon(newimg);
        sun = new JLabel(sunImage);
        replayButton = new JButton();

        this.setTitle("Snake");
        this.setPreferredSize(new Dimension(700, 700));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(182, 255, 255));
    	this.pack();
		this.setLocationRelativeTo(null);
        this.setVisible(true);
		this.setFocusable(false);
		this.setLayout(null);

		sun.setSize(500, 500);
        //title.setHorizontalAlignment(SwingConstants.CENTER);
        //title.setVerticalAlignment(SwingConstants.CENTER);
		sun.setLocation(380, -200);
		sun.setVisible(true);
        sun.setFocusable(false);

        replayButton.setText("replay");
		replayButton.setSize(150, 60);
        replayButton.setFont(new Font("Serif", Font.PLAIN, 30));
        replayButton.setHorizontalAlignment(SwingConstants.CENTER);
        replayButton.setVerticalAlignment(SwingConstants.CENTER);
		replayButton.setLocation(275, 585);
        replayButton.setBackground(new Color(198, 132, 255));
        replayButton.setForeground(Color.BLACK);
        //replayButton.setBorderPainted(false);
		replayButton.setVisible(true);
        replayButton.setFocusable(false);
        
        this.add(menu);

        menu.getStartButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                menu.stopTimer();
                remove(menu);
                add(panel);
                add(panel.getInterface().getScoreDisplay());
                add(sun);
                revalidate();
                repaint();
                menumusic.stop();
                panel.start();
            }
        });

        replayButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                add(panel);
                add(panel.getInterface().getScoreDisplay());
                add(sun);
                revalidate();
                repaint();
                menumusic.stop();
                panel.start();
            }
        });

        initAudio();
        menumusic.loop(Clip.LOOP_CONTINUOUSLY);
        menumusic.start();
    }

    private void initAudio()
    {
        try
        {
            URL url = this.getClass().getResource("sfx/menumusic.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            menumusic = AudioSystem.getClip();
            menumusic.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    Canvas getPanel() { return panel; }

    void showReplay()
    {
        this.add(replayButton);
        this.revalidate();
        this.repaint();
    }
}
