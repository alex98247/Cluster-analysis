import org.apache.commons.codec.language.DoubleMetaphone;

import java.util.HashSet;

public class Metrica {
    public static double levenshtein(String str1, String str2) {
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
        for (int i = 1; i <= len1; ++i) {
            for (int j = 1; j <= len2; ++j) {
                fisherMatrix[i][j] = Math.min(fisherMatrix[i - 1][j] + 1, fisherMatrix[i][j - 1] + 1);
                int m = str1.charAt(i-1) == str2.charAt(j-1) ? 0 : 1;
                fisherMatrix[i][j] = Math.min(fisherMatrix[i][j], fisherMatrix[i - 1][j - 1] + m);
            }
        }
        int maxLength = len1 > len2? len1 : len2;
        int levenshteinDistance = fisherMatrix[len1][len2];
        return 1 - levenshteinDistance/maxLength;
    }
    public static int MAX_SHINGLE_SIZE = 2;

    public static HashSet<String> getShingles(String string){
        HashSet<String> shingles = new HashSet<String>();
        int curPosition = 0, prevPosition = 0, length = string.length();
        int step = MAX_SHINGLE_SIZE;
        while (curPosition <= length - 1 && step != 0) {
            step = MAX_SHINGLE_SIZE;
            if (curPosition + step - 1 >= length) {
                step = length - curPosition;
            }
            String shingle = string.substring(curPosition, curPosition + step);
            shingles.add(shingle);
            curPosition += step;
        }
        return shingles;
    }

    public static double getJaccardSimilarity(String string1, String string2){
        HashSet<String> shingles1 = getShingles(string1);
        HashSet<String> shingles2 = getShingles(string2);
        HashSet<String> intersection = new HashSet<String>();
        HashSet<String> union = new HashSet<String>(shingles2);
        for (String shingle:shingles1) {
            if (shingles2.contains(shingle)){
                intersection.add(shingle);
            }
            union.add(shingle);
        }
        return 1.0D * intersection.size()/union.size();
    }
    public static double getJaccardDistance(String string1, String string2){
        return 1.0D - getJaccardSimilarity(string1, string2);
    }
    public static double getDistance(String string1, String string2){
        DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
        double distance = getJaccardDistance(doubleMetaphone.doubleMetaphone(string1), doubleMetaphone.doubleMetaphone(string2));
        //System.out.println(distance);
        return distance;
    }
}
