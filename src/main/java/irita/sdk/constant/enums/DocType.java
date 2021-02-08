package irita.sdk.constant.enums;

public enum DocType {
    BUSINESS_LICENSE(0, "营业执照"),
    WATER_INTAKING_PERMIT(1, "取水许可证"),
    PESTICIDE_BUSINESS_LICENSE(2, "农药经营许可"),
    FOREST_SEED_PRODUCTION_OPERATION_LICENSE(3, "林木种子生产经营许可"),
    CONSTRUCTION_PROJECT_PLANNING_PERMIT(4, "建设工程规划许可证"),
    PUBLIC_PLACES_HYGIENE_LICENSE(5, "公共场所卫生许证"),
    FILING_NOTICE(6, "备案通知书"),
    DUJIANGYAN_SIGNBOARD_INSTALLATION_PERMIT(7, "都江堰市招牌设置许可证"),
    HAZARDOUS_CHEMICALS_BUSINESS_LICENSE(8, "危险化学品经营许可证"),
    SOIL_WATER_CONSERVATION_PLAN_REPLY(9, "水土保持方案批复"),
    NIGHT_CONSTRUCTION_PERMIT(10, "夜间施工许可证");

    private final int code;
    private final String comment;

    DocType(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    public String getComment() {
        return comment;
    }
}
