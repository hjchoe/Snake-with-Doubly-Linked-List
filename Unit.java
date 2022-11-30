package snake;

import java.awt.geom.Rectangle2D;

class Unit extends Rectangle2D.Double
{
    final double size = 25;
    private int type; // 0: field | 1: snake | 2: apple

    Unit(Coord pos, int type)
    {
        this.setRect(pos.getX(), pos.getY(), this.size, this.size);
        this.type = type;
    }

    int getType() { return this.type; }
    void changeType(int type) { this.type = type; }
}
