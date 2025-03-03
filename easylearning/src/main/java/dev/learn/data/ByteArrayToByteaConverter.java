package dev.learn.data;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ByteArrayToByteaConverter implements AttributeConverter<byte[], byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(byte[] attribute) {
        return attribute;
    }

    @Override
    public byte[] convertToEntityAttribute(byte[] dbData) {
        return dbData;
    }
}