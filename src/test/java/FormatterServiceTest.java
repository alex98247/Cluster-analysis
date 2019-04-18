import org.junit.Assert;
import org.junit.Test;
import services.formatterService.FormatterServiceImpl;

import java.lang.reflect.Field;

public class FormatterServiceTest {
    IntModel intModel = new IntModel(5);
    BoolModel boolModel = new BoolModel(true);
    CoomonModel coomonModel = new CoomonModel(11.6f, 12.6, 6, false, "sdf");

    @Test
    public void FieldIntTest() {
        Field field = intModel.getClass().getDeclaredFields()[0];
        FormatterServiceImpl<IntModel> formatService = new FormatterServiceImpl<>();
        double result = formatService.convertToDouble(field, intModel);
        Assert.assertEquals(5.0, result, 0);
    }

    @Test
    public void FieldBooleanTest() {
        Field field = boolModel.getClass().getDeclaredFields()[0];
        FormatterServiceImpl<BoolModel> formatService = new FormatterServiceImpl<>();
        double result = formatService.convertToDouble(field, boolModel);
        Assert.assertEquals(1.0, result, 0);
    }

    @Test
    public void FieldCommonTest() {
        Field[] fields = coomonModel.getClass().getDeclaredFields();
        FormatterServiceImpl<CoomonModel> formatService = new FormatterServiceImpl<>();
        double[] result = formatService.formatToDouble(coomonModel);
        Assert.assertArrayEquals(new double[] {11.6, 12.6, 6.0, 0.0}, result, 0);
    }
}

class IntModel {
    int age;

    IntModel(int age) {
        this.age = age;
    }
}

class BoolModel {
    boolean age;

    BoolModel(boolean age) {
        this.age = age;
    }
}

class CoomonModel {
    float f;
    double d;
    int i;
    boolean b;
    String str;

    CoomonModel(float f, double d, int i, boolean b, String str) {
        this.f = f;
        this.d = d;
        this.i = i;
        this.b = b;
        this.str = str;
    }
}
