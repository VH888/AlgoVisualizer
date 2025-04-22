public interface SortStepper {
    /**
     * Perform exactly one comparison and (if needed) one swap.
     * @return true if a swap occurred on this step, false otherwise.
     */
    //For more clarification we Comparing two elements in considered an operation
    //If we swap we say true if we are only comparing then false
    boolean nextStep();

    /** @return true once the array is fully sorted. */
    boolean isFinished();

    /**
     * Give this stepper a fresh copy of the data to sort
     * (for example: after Shuffle or resizing).
     */
    void reset(int[] newData);

    /** @return the current contents of the array */
    int[] getData();
}

