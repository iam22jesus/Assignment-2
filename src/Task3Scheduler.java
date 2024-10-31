import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task3Scheduler {
    static class DynamicJob extends Job {
        private final int arrivalTime;

        public DynamicJob(String id, int processingTime, int arrivalTime) {
            super(id, processingTime);
            this.arrivalTime = arrivalTime;
        }

        public int getArrivalTime() {
            return arrivalTime;
        }
    }

    public static void main(String[] args) {
        List<DynamicJob> jobs = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/task3-input.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // Trim whitespace
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] data = line.split(" "); // Split by space
                if (data.length != 3) {
                    System.out.println("Invalid input format: " + line);
                    continue; // Skip invalid lines
                }
                String jobId = data[0];
                int processingTime = Integer.parseInt(data[1]);
                int arrivalTime = Integer.parseInt(data[2]);
                jobs.add(new DynamicJob(jobId, processingTime, arrivalTime));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numbers in input file.");
            return;
        }

        // Sort jobs by arrival time
        jobs.sort(Comparator.comparingInt(DynamicJob::getArrivalTime));

        // Scheduler simulation
        PriorityQueue<DynamicJob> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(DynamicJob::getProcessingTime));
        List<String> executionOrder = new ArrayList<>();
        int currentTime = 0;
        int jobIndex = 0;
        int totalCompletionTime = 0;

        while (jobIndex < jobs.size() || !priorityQueue.isEmpty()) {
            // Add all jobs that have arrived by current time
            while (jobIndex < jobs.size() && jobs.get(jobIndex).getArrivalTime() <= currentTime) {
                priorityQueue.add(jobs.get(jobIndex));
                jobIndex++;
            }

            // If there are jobs in the queue, process the next one
            if (!priorityQueue.isEmpty()) {
                DynamicJob job = priorityQueue.poll();
                executionOrder.add(job.getId());
                currentTime += job.getProcessingTime();
                totalCompletionTime += currentTime; // Add to total completion time
            } else {
                // If no jobs are available, jump to the next job's arrival time
                if (jobIndex < jobs.size()) {
                    currentTime = jobs.get(jobIndex).getArrivalTime();
                }
            }
        }

        // Calculate average completion time
        double averageCompletionTime = (double) totalCompletionTime / jobs.size();

        // Output execution order
        System.out.print("Execution order: [");
        for (int i = 0; i < executionOrder.size(); i++) {
            System.out.print(executionOrder.get(i));
            if (i < executionOrder.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Output average completion time
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }
}
