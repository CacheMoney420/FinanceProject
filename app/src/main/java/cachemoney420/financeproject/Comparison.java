package cachemoney420.financeproject;

import java.util.UUID;

public class Comparison {

    private UUID mId;
    private String mOverweight;
    private String mUnderweight;
    private String mRatio;
    private String mRank;

    public Comparison(){
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return mId;
    }

    public Comparison(UUID id) {
        mId = id;
    }

    public String getOverweight() {
        return mOverweight;
    }

    public void setOverweight(String overweight) {
        mOverweight = overweight;
    }

    public String getUnderweight() {
        return mUnderweight;
    }

    public void setUnderweight(String underweight) {
        mUnderweight = underweight;
    }

    public String getRatio() {
        return mRatio;
    }

    public void setRatio(String ratio) {
        mRatio = ratio;
    }

    public String getRank() {
        return mRank;
    }

    public void setRank(String rank) {
        mRank = rank;
    }

}
