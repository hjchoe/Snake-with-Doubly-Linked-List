package snake;

class Snake
{
    private BodyLink head;
    private BodyLink tail;
    private BodyLink last;

    Snake()
    {
        this.head = null;
        this.tail = null;
        this.insertFirst(new Coord(1, 7), 1, false);
        this.insertFirst(new Coord(2, 7), 1, false);
        this.insertFirst(new Coord(3, 7), 1, false);
    }

    BodyLink getHead() { return this.head; }
    BodyLink getTail() { return this.tail; }

    Boolean isEmpty() { return this.head == null; }

    void insertFirst(Coord position, int direction, Boolean rotation)
    {
        BodyLink newBodyLink = new BodyLink(position, direction, rotation);

        if (isEmpty()) this.tail = newBodyLink;
        else this.head.setPrevious(newBodyLink);
        newBodyLink.setNext(this.head);
        this.head = newBodyLink;
    }

    void insertLast(Coord position, int direction, Boolean rotation)
    {
        BodyLink newBodyLink = new BodyLink(position, direction, rotation);
        if (isEmpty()) this.head = newBodyLink;
        else
        {
            this.tail.setNext(newBodyLink);
            newBodyLink.setPrevious(this.tail);
        }
        this.tail = newBodyLink;
    }

    BodyLink deleteLast()
    {
        BodyLink temp = this.tail;
        if (head.getNext() == null) this.head = null;
        else this.tail.getPrevious().setNext(null);
        this.tail = this.tail.getPrevious();
        return temp;
    }

    BodyLink shift(Coord position, int direction)
    {
        insertFirst(position, direction, direction % 2 == 0);
        this.last = deleteLast();
        return this.last;
    }

    void grow()
    {
        insertLast(this.last.getPosition(), this.last.getDirection(), this.last.getDirection() % 2 == 0);
    }
}

class BodyLink
{
    private Coord position; // x,y positional coordinates
    private int direction; // 0 : North | 1 : East | 2 : South | 3 : West
    private Boolean rotation; // true = vertical | false = horizontal
    private BodyLink next;
    private BodyLink previous;

    BodyLink(Coord position, int direction, Boolean rotation)
    {
        this.position = position;
        this.direction = direction;
        this.rotation = rotation;
    }

    Coord getPosition() { return this.position; }
    int getDirection() { return this.direction; }
    Boolean getRotation() { return this.rotation; }
    BodyLink getNext() { return this.next; }
    BodyLink getPrevious() { return this.previous; }

    void setPosition(Coord position) { this.position = position; }
    void setDirection(int direction) { this.direction = direction; }
    void setRotation(Boolean rotation) { this.rotation = rotation; }
    void setNext(BodyLink next) { this.next = next; }
    void setPrevious(BodyLink previous) { this.previous = previous; }
}