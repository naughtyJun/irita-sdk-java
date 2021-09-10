package irita.sdk.module.nft;

public class BurnNFTRequest {
    private String denom;
    private String id;

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
