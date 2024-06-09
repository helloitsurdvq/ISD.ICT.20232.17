package utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.lang.reflect.Field;

/**
 * The {@link utils.CustomMap JSON} class represents JSON objects.
 * To create a new JSON object,
 * JSON jsonObject = new JSON();
 * jsonObject.put("key", value);
 */
public class CustomMap extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private static int offset = 0; // to trace the current index when calling a function

    /**
     * Return a {@link java.util.Map Map} that represents the mapping among
     * attribute names and their values of an object.
     */
    public static Map<String, Object> toMyMap(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Map<String, Object> map = new CustomMap();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            try {
                if (!value.getClass().getPackage().getName().startsWith("java")) {
                    value = CustomMap.toMyMap(value).toString();
                }
            } catch (Exception ex) {
                ;
            }
            map.put(field.getName(), value);
            field.setAccessible(false);
        }
        return map;
    }

    /**
     * Return a {@link java.lang.String String} that represents the term in between
     * 2 double quote.
     */
    private static String getNextTerm(String str, int idx) {
        if (str == null || idx >= str.length() || str.charAt(idx) != '"') {
            throw new IllegalArgumentException("Cannot resolve the input.");
        }

        if (str.charAt(idx + 1) == '"') {
            return new String();
        }

        int i = idx + 1;
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(str.charAt(i));
            i++;
            if (i == str.length()) {
                throw new IllegalArgumentException("Cannot resolve the input.");
            }
        } while (str.charAt(i) != '"');

        String result = sb.toString();
        offset = result.length() + 2;
        return sb.toString();
    }

    /**
     * Return a {@link utils.CustomMap CustomMap} that represents the interested substring in a {@link java.lang.String String}.
     */
    public static CustomMap toCustomMap(String str, int idx) throws IllegalArgumentException {
        if (str == null || str.length() < 2 || str.charAt(idx) != '{') {
            throw new IllegalArgumentException("Cannot resolve the input.");
        } else if (idx >= str.length()) {
            return null;
        }

        CustomMap root = new CustomMap();
        StringBuilder sb = new StringBuilder();
        int i = idx;
        sb.append(str.charAt(i));

        i++;
        try {
            while (true) {
                if (str.charAt(i) != '"') {
                    throw new IllegalArgumentException("Cannot resolve the input.");
                }
                String key;
                try {
                    key = getNextTerm(str, i);
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Cannot resolve the input.");
                }
                if (key == null) {
                    throw new IllegalArgumentException("Cannot resolve the input.");
                }

                sb.append(str.subSequence(i, i + offset));
                i += offset;
                offset = 0;
                sb.append(str.charAt(i));
                if (str.charAt(i) != ':') {
                    throw new IllegalArgumentException("Cannot resolve the input.");
                }
                i++;
                Object value;
                if (str.charAt(i) == '{') {
                    value = toCustomMap(str, i);
                    sb.append(str.subSequence(i, i + offset));
                    i += offset;
                    offset = 0;
                } else if (str.charAt(i) == '"') {
                    try {
                        value = getNextTerm(str, i);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("Cannot resolve the input.");
                    }
                    if (value == null) {
                        throw new IllegalArgumentException("Cannot resolve the input.");
                    }
                    sb.append(str.subSequence(i, i + offset));
                    i += offset;
                    offset = 0;
                } else {
                    throw new IllegalArgumentException("Cannot resolve the input.");
                }
                root.put(key, value);
                if (str.charAt(i) == ',') {
                    sb.append(str.charAt(i));
                    i++;
                } else if (str.charAt(i) == '}') {
                    sb.append(str.charAt(i));
                    break;
                } else {
                    throw new IllegalArgumentException("Cannot resolve the input.");
                }
            }
            offset = sb.toString().length();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot resolve the input.");
        }
        return root;
    }

    /**
     * Return a {@link java.lang.String String} that represents the JSON object.
     */
    public String toJSON() {
        int max = size() - 1;
        if (max == -1)
            return "{}";

        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Object>> it = entrySet().iterator();

        sb.append('{');
        for (int i = 0; ; i++) {
            Map.Entry<String, Object> e = it.next();
            String key = e.getKey();
            Object value = e.getValue();
            sb.append('"' + key.toString() + '"');
            sb.append(':');
            sb.append(value instanceof CustomMap ? ((CustomMap) value).toJSON() : ('"' + value.toString() + '"'));
            if (i == max)
                return sb.append('}').toString();
            sb.append(",");
        }
    }
}
