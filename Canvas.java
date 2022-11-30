package snake;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

class Canvas extends JPanel
{
    private Snake snek;
    private int targetDirection;
    private int direction;
    private KeySense ka;
    private Field map;
    private Color headColor = new Color(181, 251, 255);
    //private Color[] snakeColors = {new Color(255, 0, 230), new Color(255, 144, 245)};
    private Color[] snakeColors = {new Color(171, 0, 255), new Color(204, 144, 255)};
    private ActionListener taskPerformer;
    private Timer timer;
    private int delay = 169; //milliseconds
    //private int delay = 1000;

    private class KeySense extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent ke)
        {
            switch(ke.getKeyCode())
            {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    System.out.println("UP");
                    targetDirection = 0;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    System.out.println("DOWN");
                    targetDirection = 2;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    System.out.println("RIGHT");
                    targetDirection = 1;
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    System.out.println("LEFT");
                    targetDirection = 3;
                    break;
                default:
                    break;
            }
        }
    }

    Canvas()
    {
        snek = new Snake();
        map = new Field();
        direction = 1;
        targetDirection = 1;
        this.ka = new KeySense();
        this.addKeyListener(this.ka);
        this.initUI();

        taskPerformer = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                if (map.checkMove(snek.getHead().getPosition(), direction))
                {
                    System.out.println(map.getApple().getX() + " | " + map.getApple().getY());
                    setDirection();
                    BodyLink last = snek.shift(map.getMove(snek.getHead().getPosition(), direction, snek), direction);
                    map.getField()[snek.getHead().getPosition().getY()][snek.getHead().getPosition().getX()].changeType(1);
                    map.getField()[last.getPosition().getY()][last.getPosition().getX()].changeType(0);
                    repaint();
                }
                else
                {
                    System.out.println("DIED");
                }
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();
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

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int count = 0;
        for (int i = 0; i < map.getField().length; i++)
        {
            for (int j = 0; j < map.getField()[0].length; j++)
            {
                if (map.getField()[i][j].getType() == 2) g2d.setColor(Color.RED);
                else if (i == 0 || i == map.getField().length-1 || j == 0 || j == map.getField()[0].length-1) g2d.setColor(Color.BLACK);
                else g2d.setColor(map.getFieldColors()[count % 2]);
                g2d.fill(map.getField()[i][j]);
                count++;
            }
        }

        BodyLink current = snek.getHead();
        g2d.setColor(headColor);
        g2d.fill(map.getField()[current.getPosition().getY()][current.getPosition().getX()]);
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
