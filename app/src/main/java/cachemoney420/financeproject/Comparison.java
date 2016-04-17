package cachemoney420.financeproject;

import java.util.UUID;

public class Comparison {

    private UUID mId;
    private String mTag1;
    private String mTag2;
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

    public String getTag1() {
        return mTag1;
    }

    public void setTag1(String tag1) {
        mTag1 = tag1;
    }

    public String getTag2() {
        return mTag2;
    }

    public void setTag2(String tag2) {
        mTag2 = tag2;
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
