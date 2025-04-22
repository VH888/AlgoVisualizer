import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.nio.Buffer;
import java.util.Arrays;
public class Display extends JFrame {
    private Canvas canvas;
    private BufferStrategy bufferStrategy; //For double buffering (rendering)
    private int width,height;
    public Display(int width, int height){
        this.width = width;
        this.height = height;
        //Shutdown on X
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        canvas = new Canvas();
        //Screensize
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        //puts canvas into the JFrame content pane
        add(canvas);
        pack();

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        //Center and Appear (lines 20,21)
        setLocationRelativeTo(null);
        setVisible(true);
    }
    //Rendering
    public void render(int[] data){
        Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        //exception handling
        try{
            //Clear frame so that we start fresh each time we draw
            graphics2D.clearRect(0,0,width,height);
            //find our max in the Array, if empty set to 1 (dummy number)
            int max = Arrays.stream(data).max().orElse(1);
            int barWidth = width/data.length; //width for each rectangle
            //Draw each rectangle
            for(int i = 0; i < data.length; i++){
                int barHeight = (int) (data[i] * 5);
                int x = i * barWidth; //Left edge of this specific rectangle
                int y = height - barHeight; // draw from bottom
                //keep in mind that (0,0) is top left of the screen
                graphics2D.setColor(Color.blue); // Color for bar
                graphics2D.fillRect(x,y,barWidth-2,barHeight); //Draw the Bar
            }
        }
        finally {
            graphics2D.dispose();
            bufferStrategy.show();
        }
    }
}
