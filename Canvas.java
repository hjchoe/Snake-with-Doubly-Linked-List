package snake;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

class Canvas extends JPanel
{
    private Snake snek;
    private int targetDirection;
    private int direction;
    private KeySense ka;
    private Field map;
    private Color headColor = new Color(181, 251, 255);
    private Color[] snakeColors = {new Color(171, 0, 255), new Color(204, 144, 255)};
    private ActionListener taskPerformer;
    private ActionListener deathAnimation;
    private Timer timer;
    private Timer deathtimer;
    private int delay = 169; //milliseconds
    private int deathDelay = 369;
    //private int delay = 1000;
    private Interface score;
    private Boolean dead;
    private Clip deathclip;
    private Clip gamemusic;

    private class KeySense extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent ke)
        {
            switch(ke.getKeyCode())
            {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    targetDirection = 0;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    targetDirection = 2;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    targetDirection = 1;
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    targetDirection = 3;
                    break;
                default:
                    break;
            }
        }
    }

    Canvas()
    {
        dead = false;
        snek = new Snake();
        map = new Field();
        score = new Interface();
        direction = 1;
        targetDirection = 1;
        this.ka = new KeySense();
        this.addKeyListener(this.ka);
        this.initUI();
        this.initDeathAnimation();
        this.initAudio();

        taskPerformer = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                if (map.checkMove(snek.getHead().getPosition(), direction))
                {
                    //System.out.println(map.getApple().getX() + " | " + map.getApple().getY());
                    setDirection();
                    Coord target = map.getMove(snek.getHead().getPosition(), direction, snek, score);
                    BodyLink last = snek.shift(target, direction);
                    map.getField()[snek.getHead().getPosition().getY()][snek.getHead().getPosition().getX()].changeType(1);
                    map.getField()[last.getPosition().getY()][last.getPosition().getX()].changeType(0);
                    repaint();
                }
                else
                {
                    System.out.println("DIED");
                    dead = true;
                    deathtimer.setInitialDelay(deathDelay);
                    repaint();
                    deathclip.start();
                    deathtimer.start();
                    gamemusic.stop();
                    Frame frame = (Frame) (JFrame) SwingUtilities.getWindowAncestor(getSelf());
                    frame.showReplay();
                    timer.stop();
                }
            }
        };
        timer = new Timer(delay, taskPerformer);
    }

    private void initAudio()
    {
        try
        {
            URL url = this.getClass().getResource("sfx/death.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            deathclip = AudioSystem.getClip();
            deathclip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try
        {
            URL url = this.getClass().getResource("sfx/gamemusic.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            gamemusic = AudioSystem.getClip();
            gamemusic.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void initDeathAnimation()
    {
        deathAnimation = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                if (snek.getHead() != null)
                {
                    snek.deleteFirst();
                    repaint();
                }
                else deathtimer.stop();
            }
        };
        deathtimer = new Timer(deathDelay, deathAnimation);
    }
    
    private void initUI()
    {
        this.setOpaque(false);
        this.setSize(new Dimension(map.getBoundReal().getX(), map.getBoundReal().getY()));
        this.setLocation((700-map.getBoundReal().getX())/2, (700-map.getBoundReal().getY())/2);
        this.setBackground(new Color(255, 255, 255));
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);
    }

    void setDirection()
    {
        switch (direction)
        {
            case 0:
                if (targetDirection != 2) direction = targetDirection;
                break;
            case 1:
                if (targetDirection != 3) direction = targetDirection;
                break;
            case 2:
                if (targetDirection != 0) direction = targetDirection;
                break;
            case 3:
                if (targetDirection != 1) direction = targetDirection;
                break;
            default:
                break;
        }
    }

    void start() { this.requestFocus(); timer.start(); gamemusic.start(); gamemusic.loop(Clip.LOOP_CONTINUOUSLY); }
    JPanel getSelf() { return this; }

    Interface getInterface() { return this.score; }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int count = 0;
        Boolean appleDrawn = false;
        for (int i = 0; i < map.getField().length; i++)
        {
            for (int j = 0; j < map.getField()[0].length; j++)
            {
                if (map.getField()[i][j].getType() == 2) { g2d.setColor(Color.RED); appleDrawn = true; }
                else if (i == 0 || i == map.getField().length-1 || j == 0 || j == map.getField()[0].length-1) g2d.setColor(Color.BLACK);
                else g2d.setColor(map.getFieldColors()[count % 2]);
                g2d.fill(map.getField()[i][j]);
                count++;
            }
        }
        if (!appleDrawn) map.respawnApple(snek, score);

        BodyLink current = snek.getHead();
        g2d.setColor(headColor);
        if (current != null)
        {
            if (!dead) g2d.fill(map.getField()[current.getPosition().getY()][current.getPosition().getX()]);
            map.getField()[current.getPosition().getY()][current.getPosition().getX()].changeType(1);
            current = current.getNext();
            count = 0;
            while (current != null)
            {
                g2d.setColor(snakeColors[count % 2]);
                g2d.fill(map.getField()[current.getPosition().getY()][current.getPosition().getX()]);
                map.getField()[current.getPosition().getY()][current.getPosition().getX()].changeType(1);
                current = current.getNext();
                count++;
            }
        }
    }
}
