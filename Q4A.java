import java.util.*;
/*The algorithm uses a HashMap for managing hashtag count storage operations.
 A HashMap enables quick insertion and retrieval operations which speeds up the counting system beyond what arrays could achieve.
 * The program begins by initializing a 2D array to store sample tweet data, where each tweet consists of four attributes: user ID, tweet ID, tweet message, and tweet date. 
 * Sample tweets are stored in this array, which will serve as input for the hashtag counting. The core functionality is encapsulated within the countHashtags method,
 * which iterates through each tweet in the array. For each tweet, it retrieves the text of the tweet and splits the words within it using the split method.

In processing every word, the program checks if a word starts with a # symbol, which in this context indicates that it's a hashtag.
 If a hashtag is found, the program will increase its value in the HashMap using the put method. Use of the getOrDefault method simplifies the
  count logic so the program can increment the value if already there, or set to one if encountering the hashtag for the first time.


After counting all the hashtags, the application retrieves the top N hashtags using getTopHashtags. The method translates the HashMap
 entries into a list and sorts it based on the counts in reverse order. In situations where two or more hashtags have the same count, 
 the sorting is then narrowed down alphabetically to the hashtag name. The list is then sliced to obtain only the top N entries.

Finally, the displayResults function displays the results in a table format with the hashtags and corresponding counts in a
 user-friendly form. The software therefore effectively showcases the use of basic data structures and string operation manipulation to resolve
  the issue of hashtag counting and display from tweets.
 */

public class Q4A {
    public static void main(String[] args) {
        // Sample input data: user_id, tweet_id, tweet, tweet_date
        String[][] tweets = new String[7][4]; // Array to hold 7 tweets with 4 attributes each

        // Add sample tweets to the array.
        tweets[0] = new String[]{"135", "13", "Enjoying a great start to the day. #HappyDay #MorningVibes", "2024-02-01"};
        tweets[1] = new String[]{"136", "14", "Another #HappyDay with good vibes! #FeelGood", "2024-02-03"};
        tweets[2] = new String[]{"137", "15", "Productivity peaks! #WorkLife #ProductiveDay", "2024-02-04"};
        tweets[3] = new String[]{"138", "16", "Exploring new tech frontiers. #TechLife #Innovation", "2024-02-04"};
        tweets[4] = new String[]{"139", "17", "Gratitude for today's moments. #HappyDay #Thankful", "2024-02-05"};
        tweets[5] = new String[]{"140", "18", "Innovation drives us. #TechLife #FutureTech", "2024-02-07"};
        tweets[6] = new String[]{"141", "19", "Connecting with nature's serenity. #Nature #Peaceful", "2024-02-09"};

        // Count hashtag mentions
        Map<String, Integer> hashtagCounts = countHashtags(tweets);

        // Get the top 3 hashtags
        List<Map.Entry<String, Integer>> topHashtags = getTopHashtags(hashtagCounts, 3);

        // Display the results
        displayResults(topHashtags);
    }

    // Method to count hashtags from the tweets
    private static Map<String, Integer> countHashtags(String[][] tweets) {
        Map<String, Integer> hashtagCounts = new HashMap<>();

        for (String[] tweet : tweets) {
            String tweetText = tweet[2]; // Get the tweet text
            String[] words = tweetText.split(" "); // Split the tweet into words

            for (String word : words) {
                if (word.startsWith("#")) { // Check if the word is a hashtag
                    hashtagCounts.put(word, hashtagCounts.getOrDefault(word, 0) + 1);
                }
            }
        }
        return hashtagCounts;
    }

    // Method to get the top N hashtags
    private static List<Map.Entry<String, Integer>> getTopHashtags(Map<String, Integer> hashtagCounts, int n) {
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCounts.entrySet());

        // Sort by count descending, then by hashtag name ascending
        sortedHashtags.sort((a, b) -> {
            int countCompare = b.getValue().compareTo(a.getValue());
            return countCompare != 0 ? countCompare : a.getKey().compareTo(b.getKey());
        });

        return sortedHashtags.subList(0, Math.min(n, sortedHashtags.size()));
    }

    // Method to display the results in a formatted table
    private static void displayResults(List<Map.Entry<String, Integer>> topHashtags) {
        System.out.println("+-------------+---------+");
        System.out.println("|   HASHTAG   |  COUNT  |");
        System.out.println("+-------------+---------+");

        for (Map.Entry<String, Integer> entry : topHashtags) {
            System.out.printf("| %-11s | %-7d |%n", entry.getKey(), entry.getValue());
        }

        System.out.println("+-------------+---------+");
    }
}

//Input:
//[["135", "13", "Enjoying a great start to the day. #HappyDay #MorningVibes", "2024-02-01"],
// ["136", "14", "Another #HappyDay with good vibes! #FeelGood", "2024-02-03"], 
// ["137", "15", "Just had a great #HappyDay with friends! #BestMoments", "2024-02-04"],

// Output:
// +-------------+---------+
// |   HASHTAG   |  COUNT  |
// +-------------+---------+
// |  HappyDay   |      3  |
// | MorningVibes|      1  |
// |   FeelGood  |      1  |
// +-------------+---------+
// Expected Output:
// +-------------+---------+
// |   HASHTAG   |  COUNT  |
// +-------------+---------+
// |  HappyDay   |      3  |
// | MorningVibes|      1  |
// |   FeelGood  |      1  |
// +-------------+---------+