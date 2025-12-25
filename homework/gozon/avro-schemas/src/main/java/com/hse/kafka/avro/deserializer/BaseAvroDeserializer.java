package com.hse.kafka.avro.deserializer;

import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;


public class BaseAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
    private final DatumReader<T> datumReader;
    private final Schema schema;

    public BaseAvroDeserializer(Schema schema) {
        this.schema = schema;
        this.datumReader = new SpecificDatumReader<>(schema);
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        try {
            if (bytes != null) {
                BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
                return datumReader.read(null, decoder);
            } else {
                return null;
            }
        } catch (Exception exception) {
            throw new SerializationException("не удалось десериализовать данные типа " + schema.getFullName());
        }
    }
}
