import org.junit.Assert;
import org.junit.Test;
import services.tools.MetricService;

public class MetricaTest {
    @Test
    public void levenshteinNullTest() {
        int distance = MetricService.levenshtein("aa", "aa");
        Assert.assertEquals(0, distance);
    }

    @Test
    public void levenshteinNotNullTest() {
        int distance1 = MetricService.levenshtein("aa", "ab");
        int distance2 = MetricService.levenshtein("aaa", "abb");
        int distance3 = MetricService.levenshtein("aaaa", "bbbb");
        Assert.assertEquals(1, distance1);
        Assert.assertEquals(2, distance2);
        Assert.assertEquals(4, distance3);
    }
}
