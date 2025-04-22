import java.util.Arrays;
public class InsertionSortStepper implements SortStepper {
    private int[] data;
    private int i; // index of the key being inserted
    private int j;  // index used to shift elements
    private int key; // value being inserted
    private boolean shifting;// true when in shifting phase
    private boolean finished;// true when sorting is complete

    public InsertionSortStepper(int[] initialData) {
        reset(initialData);
    }

    @Override
    public void reset(int[] newData) {
        this.data = Arrays.copyOf(newData, newData.length);
        this.i = 1;
        this.j  = i - 1;
        this.key = data[i];
        this.shifting  = true;
        this.finished  = false;
        if (data.length <= 1) {
            // Nothing to sort
            this.finished = true;
        }
    }

    @Override
    public boolean nextStep() {
        if (finished) return false;

        if (shifting) {
            // Shift elements that are greater than key
            if (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j--;
            } else {
                // End of shifting phase
                shifting = false;
            }
        } else {
            // Place key at the correct position
            data[j + 1] = key;
            // Move to next key
            i++;
            if (i >= data.length) {
                finished = true;
            } else {
                key = data[i];
                j = i - 1;
                shifting  = true;
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
