import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Main {

    /**
     * Helper to build a random int[] of the given size.
     */
    private static int[] makeRandomArray(int size) {
        Random rnd = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rnd.nextInt(200) + 1;  // values 1–200
        }
        return arr;
    }

    public static void main(String[] args) {
            /*
            Create initial container refrence so that JFrame elements to access the data since they require a final type (lambda)
            The final type is a static structure which points to a dynamic value
             */
            final int[] sizeHolder = new int[1];
            sizeHolder[0] = 15;
            SortStepper defaultStepper = new BubbleSortStepper(makeRandomArray(sizeHolder[0]));
            final SortStepper[] stepperRef = new SortStepper[]{defaultStepper};

            // Display setup
            Display display = new Display(1920, 1080);


            /*
            Sets how frequent we should perform a sorting step followed by a re-render of the current
            Rectangles new position.

            The larger the number passed into Timer, the slower the animation will be.
             */
            Timer timer = new Timer(100, (ActionEvent e) -> {
                SortStepper stepper = stepperRef[0];
                if (!stepper.isFinished()) {
                    stepper.nextStep();
                    display.render(stepper.getData());
                } else {
                    //Once the array is fully sorted, we stop rendering
                    ((Timer)e.getSource()).stop();
                }
            });

            // Start Button
            JButton startButton = new JButton("Start");
            startButton.addActionListener(e -> {
                //Once clicked, the animation starts and we also disable the option to press start again
                timer.start();
                startButton.setEnabled(false);
            });

            // 5) Shuffle Button
            JButton shuffleButton = new JButton("Shuffle");
            shuffleButton.addActionListener(e -> {
                //Animation stops and we make a brandnew random array (shuffle)
                //Reset the sorting algos state and re render
                timer.stop();
                int[] newData = makeRandomArray(sizeHolder[0]);
                stepperRef[0].reset(newData);
                display.render(stepperRef[0].getData());
                startButton.setEnabled(true);
            });

            // 6) Size Slider
            JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, sizeHolder[0]);
            //Visuals
            sizeSlider.setMajorTickSpacing(10);
            sizeSlider.setMinorTickSpacing(1);
            sizeSlider.setPaintTicks(true);
            sizeSlider.setPaintLabels(true);
            sizeSlider.setOpaque(false);
            sizeSlider.addChangeListener((ChangeEvent e) -> {
                if (!sizeSlider.getValueIsAdjusting()) {
                    //Stop the animation, make a new random array based on the size selected on the slider
                    timer.stop();
                    sizeHolder[0] = sizeSlider.getValue();
                    int[] newData = makeRandomArray(sizeHolder[0]);
                    stepperRef[0].reset(newData);
                    display.render(stepperRef[0].getData());
                    startButton.setEnabled(true);
                }
            });

            // 7) Algorithm Selector
            String[] algos = {"Bubble", "Selection", "Insertion"};
            //Selection box
            JComboBox<String> algoBox = new JComboBox<>(algos);
            algoBox.addActionListener(e -> {
                timer.stop();
                int[] current = stepperRef[0].getData();
                String choice = (String) algoBox.getSelectedItem();
                switch (choice) {
                    case "Selection":
                        stepperRef[0] = new SelectionSortStepper(current);
                        break;
                    case "Insertion":
                        stepperRef[0] = new InsertionSortStepper(current);
                        break;
                    default:
                        stepperRef[0] = new BubbleSortStepper(current);
                }
                display.render(stepperRef[0].getData());
                startButton.setEnabled(true);
            });

            // 8) Assemble Controls on Glass Pane
            //Glass pane makes sure our buttons sit on top of our Canvas
            JComponent glass = (JComponent) display.getGlassPane();
            glass.setVisible(true);
            glass.setOpaque(false);
            glass.setLayout(new BorderLayout());

            //Add the buttons to the panel control and make them visible
            JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            controls.setOpaque(false);
            controls.add(new JLabel("Size:"));
            controls.add(sizeSlider);
            controls.add(new JLabel("Algo:"));
            controls.add(algoBox);
            controls.add(startButton);
            controls.add(shuffleButton);

            glass.add(controls, BorderLayout.NORTH);

            // 9) Pack & show
        //Pack re adjusts window and display
            display.pack();
            display.setVisible(true);
    }
}






