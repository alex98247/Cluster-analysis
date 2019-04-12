package services.tools;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FormatterServiceImpl<T> implements FormatterService<T> {

    private static final Class[] validTypes = new Class[]{Boolean.TYPE, Double.TYPE, Integer.TYPE, Float.TYPE};

    public double[] formatToDouble(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        double[] doubleValues = Arrays.stream(fields)
                .filter(x -> isValid(x))
                .mapToDouble(x -> convertToDouble(x, entity))
                .toArray();
        return doubleValues;
    }

    private boolean isValid(Field field) {
        if (Arrays.stream(validTypes).anyMatch(field.getType()::equals)) return true;
        return false;
    }

    public double convertToDouble(Field field, T entity) {
        field.setAccessible(true);

        try {
            if (field.getType() == Boolean.TYPE) {
                return field.getBoolean(entity) ? 1 : 0;
            } else {
                String fieldAsString = field.get(entity).toString();
                return Double.parseDouble(fieldAsString);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
