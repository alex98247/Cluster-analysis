package tools;

public class Metric {

    public static int levenshtein(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();

        int[][] fisherMatrix = new int[len1 + 1][len2 + 1];
        fisherMatrix[0][0] = 0;

        for (int i = 1; i <= len1; i++) {
            fisherMatrix[i][0] = fisherMatrix[i - 1][0] + 1;
        }
        
        for (int j = 1; j <= len2; j++) {
            fisherMatrix[0][j] = fisherMatrix[0][j - 1] + 1;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len1; j++) {
                fisherMatrix[i][j] = Math.min(fisherMatrix[i - 1][j] + 1, fisherMatrix[i][j - 1] + 1);
                int m = str1.charAt(i-1) == str2.charAt(j-1) ? 0 : 1;
                fisherMatrix[i][j] = Math.min(fisherMatrix[i][j], fisherMatrix[i - 1][j - 1] + m);
            }
        }

        return fisherMatrix[len1][len2];
    }
}
