package irita.sdk.constant.enums;

public enum RoleEnum {
    SUPER_ADMIN("super_admin"),
    ADMIN("admin"),
    HASH_ADMIN("hash_admin"),
    ;

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
