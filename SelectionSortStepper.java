import java.util.Arrays;
public class SelectionSortStepper implements SortStepper {
    private int[] data;
    private int i;  // index of current position in outer loop
    private int j; // index for scanning to find minimum
    private int minIndex;
    private boolean needSwap;// true when ready to perform the swap
    private boolean finished;// true when sorting is complete

    public SelectionSortStepper(int[] initialData) {
        reset(initialData);
    }

    @Override
    public void reset(int[] newData) {
        this.data = Arrays.copyOf(newData, newData.length);
        this.i = 0;
        this.j = i + 1;
        this.minIndex  = i;
        this.needSwap  = false;
        this.finished  = false;
    }

    @Override
    public boolean nextStep() {
        if (finished) return false;

        // Scanning phase: find min in data[i..end]
        if (!needSwap) {
            if (j < data.length) {
                // Compare current element to current min
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
                j++;
            } else {
                // Scanning done, ready to swap
                needSwap = true;
            }
        } else {
            // Perform swap between data[i] and data[minIndex]
            int temp = data[i];
            data[i] = data[minIndex];
            data[minIndex]  = temp;
            // Move to next position
            i++;
            if (i >= data.length - 1) {
                finished = true;
            } else {
                minIndex  = i;
                j  = i + 1;
                needSwap  = false;
            }
        }
        return true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public int[] getData() {
        return data;
    }
}