import org.junit.Assert;
import org.junit.Test;
import tools.Metric;

public class MetricaTest {
    @Test
    public void levenshteinNullTest() {
        int distance = Metric.levenshtein("aa", "aa");
        Assert.assertEquals(0, distance);
    }

    @Test
    public void levenshteinNotNullTest() {
        int distance1 = Metric.levenshtein("aa", "ab");
        int distance2 = Metric.levenshtein("aaa", "abb");
        int distance3 = Metric.levenshtein("aaaa", "bbbb");
        Assert.assertEquals(1, distance1);
        Assert.assertEquals(2, distance2);
        Assert.assertEquals(4, distance3);
    }
}
