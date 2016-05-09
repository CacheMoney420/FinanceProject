package cachemoney420.financeproject;

import java.util.UUID;

public class Comparison {

    private UUID mId;
    private String mOverweight;
    private String mUnderweight;
    private Double mRatio;
    private Integer mRank;

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

    public Double getRatio() {
        return mRatio;
    }

    public void setRatio(Double ratio) {
        mRatio = ratio;
    }

    public Integer getRank() {
        return mRank;
    }

    public void setRank(Integer rank) {
        mRank = rank;
    }
}
