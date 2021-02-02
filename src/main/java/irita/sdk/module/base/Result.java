package irita.sdk.module.base;

import irita.sdk.constant.enums.EventEnum;

import java.util.Optional;

/*
public class ResultTx {
    private long gasWanted;
    private long gasUsed;
    private byte[] data;
    private StringEvent[] events;
    private String hash;
    private long height;

    // The caller handles the case not found
    public String getEventValue(EventEnum eventEnum) {
        for (StringEvent e : events) {
            if (eventEnum.getType().equals(e.getType())) {
                for (StringEvent.Attribute attr : e.getAttributes()) {
                    if (eventEnum.getKey().equals(attr.key)) {
                        return attr.value;
                    }
                }
            }
        }
        return null;
    }

    public long getGasWanted() {
        return gasWanted;
    }

    public void setGasWanted(long gasWanted) {
        this.gasWanted = gasWanted;
    }

    public long getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(long gasUsed) {
        this.gasUsed = gasUsed;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public StringEvent[] getEvents() {
        return events;
    }

    public void setEvents(StringEvent[] events) {
        this.events = events;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}
*/


public class Result {
    private Check_tx check_tx;
    private Deliver_tx deliver_tx;
    private String hash;
    private String height;

    public void setCheck_tx(Check_tx check_tx) {
        this.check_tx = check_tx;
    }

    public Check_tx getCheck_tx() {
        return check_tx;
    }

    public void setDeliver_tx(Deliver_tx deliver_tx) {
        this.deliver_tx = deliver_tx;
    }

    public Deliver_tx getDeliver_tx() {
        return deliver_tx;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

}