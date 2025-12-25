package com.hse.kafka.avro.serializer;


import org.apache.avro.Schema;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;

public class BaseAvroSerializer<T extends SpecificRecordBase> implements Serializer<T> {
    private final Schema schema;
    private final DatumWriter<T> datumWriter;

    public BaseAvroSerializer(Schema schema) {
        this.schema = schema;
        this.datumWriter = new SpecificDatumWriter<>(schema);
    }

    @Override
    public byte[] serialize(String s, T t) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (t != null) {
                BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
                datumWriter.write(t, encoder);
                encoder.flush();
                return outputStream.toByteArray();
            }
            return null;
        } catch (Exception e) {
            throw new SerializationException("не удалось сериализовать данные типа " + schema.getFullName());
        }
    }
}
