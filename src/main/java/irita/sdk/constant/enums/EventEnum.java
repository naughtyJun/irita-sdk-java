package irita.sdk.constant.enums;

public enum EventEnum {
    MessageCodeId("message", "code_id");


    private final String type;
    private final String key;

    EventEnum(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }
}
