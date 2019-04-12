package services.tools;

import java.lang.reflect.Field;

public interface FormatterService<T> {
    double[] formatToDouble(T entity);
    double convertToDouble(Field field, T entity);
}
