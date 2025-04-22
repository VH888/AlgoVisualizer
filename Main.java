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
            // 1) Mutable holders & initial data
            final int[] sizeHolder = new int[1];
            sizeHolder[0] = 15;
            SortStepper defaultStepper = new BubbleSortStepper(makeRandomArray(sizeHolder[0]));
            final SortStepper[] stepperRef = new SortStepper[]{defaultStepper};

            // 2) Display setup
            Display display = new Display(1920, 1080);

            // 3) Animation Timer
            Timer timer = new Timer(100, (ActionEvent e) -> {
                SortStepper stepper = stepperRef[0];
                if (!stepper.isFinished()) {
                    stepper.nextStep();
                    display.render(stepper.getData());
                } else {
                    ((Timer)e.getSource()).stop();
                }
            });

            // 4) Start Button
            JButton startButton = new JButton("Start");
            startButton.addActionListener(e -> {
                timer.start();
                startButton.setEnabled(false);
            });

            // 5) Shuffle Button
            JButton shuffleButton = new JButton("Shuffle");
            shuffleButton.addActionListener(e -> {
                timer.stop();
                int[] newData = makeRandomArray(sizeHolder[0]);
                stepperRef[0].reset(newData);
                display.render(stepperRef[0].getData());
                startButton.setEnabled(true);
            });

            // 6) Size Slider
            JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, sizeHolder[0]);
            sizeSlider.setMajorTickSpacing(10);
            sizeSlider.setMinorTickSpacing(1);
            sizeSlider.setPaintTicks(true);
            sizeSlider.setPaintLabels(true);
            sizeSlider.setOpaque(false);
            sizeSlider.addChangeListener((ChangeEvent e) -> {
                if (!sizeSlider.getValueIsAdjusting()) {
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
            JComponent glass = (JComponent) display.getGlassPane();
            glass.setVisible(true);
            glass.setOpaque(false);
            glass.setLayout(new BorderLayout());

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
            display.pack();
            display.setVisible(true);
    }
}






