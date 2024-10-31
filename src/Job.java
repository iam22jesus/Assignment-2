public class Job {
    private final String id;
    private final int processingTime;

    // Constructor for Task 1
    public Job(String id, int processingTime) {
        this.id = id;
        this.processingTime = processingTime;
    }

    public String getId() {
        return id;
    }

    public int getProcessingTime() {
        return processingTime;
    }
}
