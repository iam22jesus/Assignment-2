import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task1Scheduler {
    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/task1-input.txt"));
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ");
                String jobId = data[0];
                int processingTime = Integer.parseInt(data[1]);
                jobs.add(new Job(jobId, processingTime));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            return;
        }

        // Sort jobs by processing time
        jobs.sort(Comparator.comparingInt(Job::getProcessingTime));

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

        for (Job job : jobs) {
            currentTime += job.getProcessingTime();
            totalCompletionTime += currentTime;
        }

        double averageCompletionTime = (double) totalCompletionTime / jobs.size();
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }
}
