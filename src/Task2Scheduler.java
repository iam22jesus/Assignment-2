import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task2Scheduler {
    static class PriorityJob extends Job {
        private final int priority;

        public PriorityJob(String id, int processingTime, int priority) {
            super(id, processingTime);
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    public static void main(String[] args) {
        List<PriorityJob> jobs = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/task2-input.txt"));
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ");
                String jobId = data[0];
                int processingTime = Integer.parseInt(data[1]);
                int priority = Integer.parseInt(data[2]);
                jobs.add(new PriorityJob(jobId, processingTime, priority));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            return;
        }

        // Sort jobs by priority (higher first) and then by processing time
        jobs.sort(Comparator.comparingInt(PriorityJob::getPriority).thenComparingInt(Job::getProcessingTime));

        // Output execution order
        System.out.print("Execution order: [");
        for (int i = 0; i < jobs.size(); i++) {
            System.out.print(jobs.get(i).getId());
            if (i < jobs.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Calculate average completion time
        int totalCompletionTime = 0;
        int currentTime = 0;

        for (PriorityJob job : jobs) {
            currentTime += job.getProcessingTime();
            totalCompletionTime += currentTime;
        }

        double averageCompletionTime = (double) totalCompletionTime / jobs.size();
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }
}
