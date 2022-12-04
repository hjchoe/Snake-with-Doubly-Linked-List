package snake;

import java.awt.Color;
import java.util.Random;

class Field
{
    private Unit[][] field;
    private Color[] fieldColors = {new Color(107, 235, 128), new Color(74, 222, 99)};
    private Coord boundReal;
    private Coord boundIndex;
    private Random random;
    private Coord apple;

    Field()
    {
        this.random = new Random();
        this.field = new Unit[17][19];
        this.boundReal = new Coord(475, 425);
        //this.boundReal = new Coord(425, 375);
        this.boundIndex = new Coord(19, 17);
        this.initField();
        this.apple = new Coord(12, 8);
        this.field[apple.getY()][apple.getX()].changeType(2);
    }

    private void initField()
    {
        int x = 0;
        int y = 0;

        for (int i = 0; i < this.field.length; i++)
        {
            for (int j = 0; j < this.field[0].length; j++)
            {
                this.field[i][j] = new Unit(new Coord(x, y), 0);
                x += 25;
            }
            x = 0;
            y += 25;
        }
    }

    Boolean checkMove(Coord position, int direction)
    {
        switch (direction)
        {
            case 0:
                return (position.getY() != 0 && this.field[position.getY()-1][position.getX()].getType() != 1);
            case 1:
                return (position.getX() != this.boundIndex.getX()-1 && this.field[position.getY()][position.getX()+1].getType() != 1);
            case 2:
                return (position.getY() != this.boundIndex.getY()-1 && this.field[position.getY()+1][position.getX()].getType() != 1);
            case 3:
                return (position.getX() != 0 && this.field[position.getY()][position.getX()-1].getType() != 1);
            default:
                return false;
        }
    }

    Coord getMove(Coord position, int direction, Snake snaky, Interface scoringsystem)
    {
        Coord target;
        switch (direction)
        {
            case 0:
                target = new Coord(position.getX(), position.getY()-1);
                break;
            case 1:
                target = new Coord(position.getX()+1, position.getY());
                break;
            case 2:
                target = new Coord(position.getX(), position.getY()+1);
                break;
            case 3:
                target = new Coord(position.getX()-1, position.getY());
                break;
            default:
                target = new Coord(-1, -1);
        }
        if (this.field[target.getY()][target.getX()].getType() == 2) respawnApple(snaky, scoringsystem);
        return target;
    }

    void respawnApple(Snake snaky, Interface scoringsystem)
    {
        int x = this.apple.getX();
        int y = this.apple.getY();
        Coord temp = new Coord(x, y);
        
        do
        {
            temp.setX(1+this.random.nextInt(field[0].length-2));
            temp.setY(1+this.random.nextInt(field.length-2));
        }
        while (this.field[temp.getY()][temp.getX()].getType() == 1);
        this.apple.setX(temp.getX());
        this.apple.setY(temp.getY());
        this.field[this.apple.getY()][this.apple.getX()].changeType(2);
        snaky.grow();
        scoringsystem.point();
    }

    Unit[][] getField() { return this.field; }
    Color[] getFieldColors() { return this.fieldColors; }
    Coord getBoundReal() { return this.boundReal; }
    Coord getBoundIndex() { return this.boundIndex; }
    Coord getApple() { return this.apple; }
}
