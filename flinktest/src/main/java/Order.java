import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;


public class Order extends ObjectNode {
    private String name;
    private Integer num;
    private Long timedate;

    public Order(JsonNodeFactory nc) {
        super(nc);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getTimedate() {
        return timedate;
    }

    public void setTimedate(Long timedate) {
        this.timedate = timedate;
    }
}
