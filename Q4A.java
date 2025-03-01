import java.util.*; // Import necessary Java utilities, including collections like List and Map.

/*
 * Algorithm Explanation
The algorithm begins with a list that will contain tweet data which each entry represents a map
 that stores user ID and tweet ID and tweet text and tweet date. The system fills the list with sample tweets as the first step in its process. 
 The algorithm executes hashtag counting through multiple stages starting from tweet traversal to text word splitting and termination at words starting with #. 
 A map stores hashtags that records their count distribution. The algorithm performs a sort of hashtags based on their count frequency which it
  maintains in descending order with additional alphabetical sorting for hash tags that have equivalent counts. The program reveals the most popular 
  hashtags through an organized tabular structure at the end of its process.
 */

 /*
  * Code Explanation
Imports:

The program imports List and Map along with ArrayList as Java utilities to work with collections.
Class Definition:

This class defines itself as Q4A with both the main method and supporting helper methods.
Main Method:

This entry program serves as the main method through which the program starts. The createTweet method operates to fill a tweet list with example tweets.
Counting Hashtags:

The countHashtags method executes a loop operation on each tweet contained in the list. The method procures tweet text from each entry before dividing the content into separate words.
The program evaluates each word for # characters at its beginning. The hashtag gets added to the HashMap (hashtagCounts) when the key represents the hashtag with a value counting its occurrences.
Getting Top Hashtags:

The getTopHashtags method turns hashtagCounts into an entry list for sorting purposes.
The list gets sorted first by count from high to low followed by hashtag name from low to high when counts match.
The method generates a sublist of hashtags which includes the selected N elements according to the set limit.
Displaying Results:

The displayResults method organizes the output data into tabular structure. The program displays a header afterward it enumerates the top hashtags while showing their count within a formatted structure.
The method concludes by printing a footer that finishes the table structure.
Helper Method:

Everything in the helper function createTweet creates an individual map by adding user ID and tweet ID with tweet text and tweet date to the database before outputting the map.
  */


public class Q4A {
    public static void main(String[] args) {
        // Sample input data: user_id, tweet_id, tweet, tweet_date
        List<Map<String, String>> tweets = new ArrayList<>();

        // Add sample tweets to the list.
        tweets.add(createTweet("135", "13", "Enjoying a great start to the day. #HappyDay #MorningVibes", "2024-02-01"));
        tweets.add(createTweet("136", "14", "Another #HappyDay with good vibes! #FeelGood", "2024-02-03"));
        tweets.add(createTweet("137", "15", "Productivity peaks! #WorkLife #ProductiveDay", "2024-02-04"));
        tweets.add(createTweet("138", "16", "Exploring new tech frontiers. #TechLife #Innovation", "2024-02-04"));
        tweets.add(createTweet("139", "17", "Gratitude for today's moments. #HappyDay #Thankful", "2024-02-05"));
        tweets.add(createTweet("140", "18", "Innovation drives us. #TechLife #FutureTech", "2024-02-07"));
        tweets.add(createTweet("141", "19", "Connecting with nature's serenity. #Nature #Peaceful", "2024-02-09"));

        // Count hashtag mentions
        Map<String, Integer> hashtagCounts = countHashtags(tweets);

        // Get the top 3 hashtags
        List<Map.Entry<String, Integer>> topHashtags = getTopHashtags(hashtagCounts, 3);

        // Display the results
        displayResults(topHashtags);
    }

    // Method to create a tweet map
    private static Map<String, String> createTweet(String userId, String tweetId, String tweet, String tweetDate) {
        Map<String, String> tweetMap = new HashMap<>();
        tweetMap.put("user_id", userId);
        tweetMap.put("tweet_id", tweetId);
        tweetMap.put("tweet", tweet);
        tweetMap.put("tweet_date", tweetDate);
        return tweetMap;
    }

    // Method to count hashtags from the tweets
    private static Map<String, Integer> countHashtags(List<Map<String, String>> tweets) {
        Map<String, Integer> hashtagCounts = new HashMap<>();

        for (Map<String, String> tweet : tweets) {
            String tweetText = tweet.get("tweet");
            String[] words = tweetText.split(" ");

            for (String word : words) {
                if (word.startsWith("#")) {
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
// Output:
// +-------------+---------+
// |   HASHTAG   |  COUNT  |
// +-------------+---------+
// | #HappyDay   | 3       |
// | #TechLife   | 2       |
// | #ProductiveDay | 1    |
// +-------------+---------+
// Expected Output:
// +-------------+---------+
// |   HASHTAG   |  COUNT  |
// +-------------+---------+
// | #HappyDay   | 3       |
// | #TechLife   | 2       |
// | #ProductiveDay | 1    |
// +-------------+---------+
