package ru.promtalon.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class DataAccessUtil {
    public static void updateIgnoreFields(Object updateObj, Object newData, String[] fields) throws IllegalAccessException {
        updateFields(newData,updateObj,Arrays.asList(fields),true);
    }

    public static void updateFields(Object updateObj, Object newData, String[] fields) throws IllegalAccessException {
        updateFields(newData,updateObj,Arrays.asList(fields),false);
    }
    public static void updateIgnoreFields(Object updateObj, Object newData, List<String> fields) throws IllegalAccessException {
        updateFields(newData,updateObj,fields,true);
    }

    public static void updateFields(Object updateObj, Object newData, List<String> fields) throws IllegalAccessException {
        updateFields(newData,updateObj,fields,false);
    }

    private static void updateFields(Object updateObj, Object newData, List<String> fields, boolean ignore) throws IllegalAccessException {
        for (Field field : updateObj.getClass().getDeclaredFields()) {
            String fullName[] = field.getName().split("\\.");
            String name = fullName[fullName.length-1];
            if (fields.contains(name)==!ignore) {
                field.setAccessible(true);
                field.set(updateObj,field.get(newData));
                field.setAccessible(false);
            }
        }
    }
}
