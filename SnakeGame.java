/*
 * James Choe
 * hchoe4@u.rochester.edu
 * Project 3 12/8/22
 */

package snake;

public class SnakeGame
{
    static Frame frame;

    public static void main(String[] args)
    {
        frame = new Frame();
    }
}

/*
 * SNAKE
 * - head (has direction)
 * - body (has direction)
 * - tail (has direction, new tail is added when apple eaten, should duplicate prev tail)
 * 
 * APPLE
 * 
 * FIELD
 * x = 17 | 425
 * y = 15 | 375
 */

// find ./snake/ -type f -name "*.java" > sources.txt
// javac @sources.txt
// java snake.SnakeGame
// find ./snake/ -type f -name "*.java" > sources.txt && javac @sources.txt && java snake.SnakeGame



/*
 * play button
 * play again button
 * 
 * figure out why apple respawn breaks sometimes
 * test failsafe boolean state check: if apple is never drawn each frame respawn apple
 * 
 * bag of possible tiles
 * snake shift and grow add and remove from possible tiles bag
 * respawn also adds and removes possible tiles bag
 * instead of random x and y choose random from 0 to length of possible tiles bag
 */