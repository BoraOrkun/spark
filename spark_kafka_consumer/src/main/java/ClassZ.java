import java.io.Serializable;

public class ClassZ implements Serializable {
    private Integer key;
    private Integer value;

    public ClassZ() {
    }

    public ClassZ(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
