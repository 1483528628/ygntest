import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class OrderSchema implements DeserializationSchema<Order>, SerializationSchema<String> {
    @Override
    public Order deserialize(byte[] message) throws IOException {
        return null;
    }

    @Override
    public boolean isEndOfStream(Order nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(String element) {
        return new byte[0];
    }

    @Override
    public TypeInformation<Order> getProducedType() {
        return null;
    }
}
