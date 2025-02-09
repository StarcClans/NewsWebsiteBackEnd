package lt.ca.javau11.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return null;
        }
        return stringList.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
         if (joined == null || joined.isEmpty()) {
            return null;
        }
         return Arrays.stream(joined.split(SPLIT_CHAR))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}