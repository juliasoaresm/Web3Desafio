package com.devcaotics.airBnTruta.misc;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class FormHandler {

    public List<String> getAttributeNames(Class<?> clazz){
        //estratégia com os campos de um objeto
        Field fields[] = clazz.getDeclaredFields();
        
        List<String> names = Arrays.stream(fields)
            .filter(field -> field.isAnnotationPresent(FieldOrder.class))
            .sorted(Comparator.comparingInt(field -> field.getAnnotation(FieldOrder.class).value()))
            .map(Field::getName)
            .collect(Collectors.toList());

        return names;
    }

    private String toLowerCaseFirstLetter(String methodName) {
        if (methodName.length() > 0) {
            return Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
        }
        return methodName;
    }

    public Object getAttributeValue(Object o, String fieldName)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        String getMethodName = "get" + capitalizedFieldName;
        String isMethodName = "is" + capitalizedFieldName;
        Method method = o.getClass().getDeclaredMethod(getMethodName);

        if (method == null) {
            try {
                method = o.getClass().getDeclaredMethod(isMethodName);
            } catch (NoSuchMethodException e) {
                // Re-lança a exceção original ou uma customizada se ambos falharem
                throw new NoSuchMethodException("Getter (get/is) não encontrado para o campo: " + fieldName);
            }
        }
        
        return method.invoke(o);
        //Method[] methods = o.getClass().getDeclaredMethods();

       // Method method = Arrays.stream(methods)
        //    .filter(m -> m.getName().equalsIgnoreCase("get"+fieldName))
        //    .findFirst().orElse(null);

       // try {
       //     Object oReturn = method.invoke(o);
       //     return oReturn;
      //  } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
         //   e.printStackTrace();
         //   return null;
       // }

    }

    public <T> T bind(Class<T> targetClass, Map<String, String> formData) throws Exception {
        T entity = targetClass.getDeclaredConstructor().newInstance();
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String fieldName = entry.getKey();
            String stringValue = entry.getValue();

            try {
                // 3. Encontra o setter
                Method method = findSetter(targetClass, fieldName);
                if (method == null) continue; // Pula se não houver setter

                // 4. Pega o tipo esperado pelo setter
                Class<?> targetType = method.getParameterTypes()[0];

                // 5. Converte o valor String para o tipo esperado
                Object convertedValue = convertValue(stringValue, targetType);

                // 6. Invoca o setter no objeto
                method.invoke(entity, convertedValue);

            } catch (NoSuchMethodException e) {
                // Pode ignorar campos do formulário que não existem na classe
                System.err.println("Campo não encontrado ou tipo incompatível: " + fieldName);
            }
        }
        
        return entity;
    }
    private Method findSetter(Class<?> clazz, String fieldName) throws NoSuchMethodException {
        String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        String setMethodName = "set" + capitalizedFieldName;

        // Procura por todos os métodos que começam com set
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(setMethodName) && method.getParameterCount() == 1) {
                return method;
            }
        }
        throw new NoSuchMethodException("Setter não encontrado para o campo: " + fieldName);
    }
    
    /**
     * **O MAIS CRUCIAL:** Converte um valor String (do formulário) para o tipo de destino.
     */
    private Object convertValue(String value, Class<?> targetType) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        // Conversão para tipos primitivos e wrappers comuns
        if (targetType == String.class) {
            return value;
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value);
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value);
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.parseDouble(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            // Trata "on", "true", "1" como true
            return "on".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value) || "1".equals(value);
        }
        
        // **TODO:** Adicionar suporte a outros tipos como Date, LocalDate, BigDecimal, etc.
        // Para tipos complexos (como outra Entidade), você precisaria de um mecanismo de lookup (ex: Repository)
        // usando o ID que viria do formulário.
        
        throw new IllegalArgumentException("Tipo de conversão não suportado: " + targetType.getName());
    }
    public <T> T bindExisting(T targetObject, Map<String, String> formData) throws Exception {
        
        Class<?> targetClass = targetObject.getClass();

        // Itera sobre os dados do formulário e aplica ao objeto existente
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String fieldName = entry.getKey();
            String stringValue = entry.getValue();

            try {
                Method method = findSetter(targetClass, fieldName);
                if (method == null) continue;

                Class<?> targetType = method.getParameterTypes()[0];
                Object convertedValue = convertValue(stringValue, targetType);

                // Invoca o setter no objeto EXISTENTE
                method.invoke(targetObject, convertedValue);

            } catch (NoSuchMethodException e) {
                // Silenciosamente ignora campos do formulário que não possuem setter no objeto.
            }
        }
        
        return targetObject;
    }
public String toTitleCase(String fieldName) {
    if (fieldName == null || fieldName.isEmpty()) {
        return "";
    }
    return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
}
}
