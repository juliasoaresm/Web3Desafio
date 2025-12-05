package com.devcaotics.airBnTruta.misc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class GenericFormHandler {

    public List<Field> getAttributeNames(Class clazz){
        //estratégia com os campos de um objeto
        Field fields[] = clazz.getDeclaredFields();
        
        List<Field> fieldList = Arrays.stream(fields)
            // .map(Field::getName)
            // .collect(Collectors.toList());

        .filter(f -> !f.isSynthetic())
        .collect(Collectors.toList());

        return fieldList;
    }

    public Object getAttributeValue(Object o, String fieldName){

        Method[] methods = o.getClass().getDeclaredMethods();

        Method method = Arrays.stream(methods)
            .filter(m -> m.getName().equalsIgnoreCase("get"+fieldName));
            .findFirst().orElse(null);
        if (method != null) {
        try {
            Object oReturn = method.invoke(o);
            return oReturn;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return null;
        }
        }

    }

}
