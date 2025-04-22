import java.util.Arrays;
/**
 * Each nextStep() does exactly one compare (and swap if needed).
 */
public class BubbleSortStepper implements SortStepper {
    private int[] data;
    private int  i; // pass counter (0 through n-1)
    private int  j; // index within the current pass (0 through n-i-1)
    private boolean swapped;
    private boolean finished; // true once the array is fully sorted
    /**
     * Constructor takes an initial array and calls reset() to copy it.
     */
    public BubbleSortStepper(int[] initialData) {
        reset(initialData);
    }
    /**
     * reset: make a fresh copy of the array and
     *        reset all indices and flags to start sorting a new one.
     */
    @Override
    public void reset(int[] newData) {
        // Copy the array so we don’t modify the caller’s original
        this.data = Arrays.copyOf(newData, newData.length);
        this.i = 0; // start at the first pass
        this.j = 0; // start comparing at the beginning
        this.swapped = false;  // no swaps yet in this new pass
        this.finished = false; // not finished
    }

    /**
     * nextStep: perform a single comparison (and swap if needed).
     * Returns true if you want to repaint after this step.
     */
    @Override
    public boolean nextStep() {
        // If we’re already done, nothing to do
        if (finished) {
            return false;
        }
        // compare data[j] vs data[j+1]
        if (j < data.length - i - 1) {
            // If out of order, swap them
            if (data[j] > data[j + 1]) {
                int temp = data[j];
                data[j] = data[j + 1];
                data[j + 1] = temp;
                swapped = true;     // note that we did a swap
            }
            j++; // move to the next pair in this pass

        } else {
            // End of a full pass through the array
            if (!swapped) {
                // No swaps → array is sorted
                finished = true;
            } else {
                //next pass:
                i++;
                j = 0;
                swapped = false; // reset the swap flag
            }
        }

        return true;
    }

    /**
     * isFinished: returns true once sorting is complete.
     */
    @Override
    public boolean isFinished() {
        return finished;
    }

    /**
     * getData: returns the current (partially sorted) array for rendering.
     */
    @Override
    public int[] getData() {
        return data;
    }
}
