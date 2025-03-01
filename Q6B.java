import java.io.BufferedReader; // Importing BufferedReader for reading text from a character-input stream
import java.io.InputStreamReader; // Importing InputStreamReader to convert byte streams to character streams
import java.net.HttpURLConnection; // Importing HttpURLConnection for making HTTP requests
import java.net.URL; // Importing URL class to represent a URL
import java.util.LinkedList; // Importing LinkedList to use as a queue for URLs
import java.util.Queue; // Importing Queue interface for URL management
import java.util.concurrent.*; // Importing concurrent utilities for multithreading
import java.util.concurrent.atomic.AtomicInteger; // Importing AtomicInteger for thread-safe integer operations


/*
 * Since I was confronted with the problem of developing a multithreaded web crawler, I knew I needed to implement a Breadth-First Search 
 * (BFS) approach in order to crawl multiple web pages concurrently in an effective way. For this purpose, I decided to use a thread pool to
 *  operate with multiple threads, a queue to store the URLs to crawl, and offer thread-safe access to the shared resources. 

For this, I did the following:

I used a thread pool to manage concurrent tasks, which allowed a specified number of threads to run at a time. This allowed me to control the
 number of threads and prevent the system from being overwhelmed with too many concurrent requests.
I utilized a queue to store URLs to be crawled, and this enabled me to handle URLs in a first-in-first-out manner. This assisted me in not
 skipping URLs and also processing them in the same order as they were enrolled in the queue.
I parallel downloaded web pages using threads, where each thread downloaded the content of a URL and
 processed it individually. This allowed me to use multiple CPU cores and significantly speed up the crawling process.
I dealt with the content of each page, which involved extracting useful information or simply noting down the results. 
This was a crucial role in playing the function of the web crawler, which was gathering information from different web pages.
I handled errors in an appropriate manner to ensure that the crawler would continue to run even if some URLs could not be loaded.
 This involved catching exceptions and logging error messages to allow any issues to be diagnosed.
I used an atomic counter to count the number of tasks done to track progress and let the main thread wait for all tasks to finish before it exits. 
This was important to ensure that the crawler did not terminate too early and that all tasks were run to completion.
By following the steps outlined above, you can create a multithreaded web crawler that is capable of crawling multiple web pages concurrently,
 processing the content of each page, and also handling errors in a proper way.
 */
/* 
 * Algorithm in Step-by-Step Form:
I create a thread pool with a fixed number of threads.
I create a queue to hold the URLs to crawl.
I seed the queue with initial URLs.
I start a loop that continues until the queue is empty:
I dequeue a URL from the queue.
I submit a task to the thread pool to crawl and process the URL.
In the task:
I fetch the contents of the web page using an HTTP GET request.
I perform some operation on the contents (e.g., record the URL and content length).
I handle any exceptions that occur while fetching.
I increment the number of tasks done after each task completes.
I close down the thread pool once all tasks are submitted.
I wait until all tasks complete before printing the number of pages crawled.
*/
public class Q6B {
    private static final int THREAD_POOL_SIZE = 10; // Define the number of threads in the pool
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE); // Create a fixed thread pool
    private static final Queue<String> urlQueue = new LinkedList<>(); // Initialize a queue to hold URLs to be crawled
    private static final AtomicInteger completedTasks = new AtomicInteger(0); // Atomic integer to count completed tasks safely across threads

    public static void main(String[] args) {
        // Add URLs to the queue for crawling
        urlQueue.add("https://google.com"); // Add Google to the queue
        urlQueue.add("https://youtube.com"); // Add YouTube to the queue
        // Additional URLs can be added as needed

        // Start the crawling process
        crawl();
        
        // Shutdown the executor service to stop accepting new tasks
        executorService.shutdown();
        try {
            // Wait for all tasks to complete or timeout after 1 hour
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace(); // Print stack trace if interrupted
        }
        
        // Print the total number of pages crawled
        System.out.println("Crawling completed. Total pages crawled: " + completedTasks.get());
        // Expected output:
        // Crawled URL: https://google.com | Content Length: <length_of_content>
        // Crawled URL: https://youtube.com | Content Length: <length_of_content>
        // Crawling completed. Total pages crawled: 2
    }

    private static void crawl() {
        // Loop until there are no more URLs in the queue
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll(); // Retrieve and remove the head of the queue
            if (url != null) { // Check if the URL is not null
                // Submit a new task to the executor service
                executorService.submit(() -> {
                    try {
                        // Fetch the web page content
                        String content = fetchWebPage(url);
                        // Process the fetched content
                        processContent(url, content);
                    } catch (Exception e) {
                        // Handle any exceptions that occur during fetching
                        System.err.println("Error fetching URL: " + url + " - " + e.getMessage());
                    } finally {
                        // Increment the count of completed tasks
                        completedTasks.incrementAndGet();
                    }
                });
            }
        }
    }

    private static String fetchWebPage(String urlString) throws Exception {
        // Create a URL object from the string
        URL url = new URL(urlString);
        // Open an HTTP connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // Set the request method to GET
        connection.setConnectTimeout(5000); // Set connection timeout to 5 seconds
        connection.setReadTimeout(5000); // Set read timeout to 5 seconds
        
        // Create a BufferedReader to read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder(); // StringBuilder to accumulate the content
        String inputLine;
        
        // Read the response line by line
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine); // Append each line to the content
        }
        in.close(); // Close the BufferedReader
        
        return content.toString(); // Return the complete content as a string
    }

    private static void processContent(String url, String content) {
        // Here you can extract relevant data or index the content
        // For demonstration, print the URL and the length of the content
        System.out.println("Crawled URL: " + url + " | Content Length: " + content.length());
    }
}
// Output:
// Crawled URL: https://google.com | Content Length: 583789
// Crawled URL: https://youtube.com | Content Length: 21354
// Crawling completed. Total pages crawled: 2
// Expected Output:
// Crawled URL: https://google.com | Content Length: 583789
// Crawled URL: https://youtube.com | Content Length: 21354
// Crawling completed. Total pages crawled: 2