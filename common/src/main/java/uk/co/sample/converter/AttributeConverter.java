package uk.co.sample.converter;

public interface AttributeConverter<X, Y> {

    public Y convertToDatabaseColumn(X javaValue);
    public X convertToEntityAttribute(Y dbValue);

}
