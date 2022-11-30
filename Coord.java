package snake;

class Coord
{
    private int x;
    private int y;

    Coord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    int getX() { return this.x; }
    int getY() { return this.y; }

    void setX(int x) { this.x = x; }
    void setY(int y) { this.y = y; }
}
