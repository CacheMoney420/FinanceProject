package cachemoney420.financeproject;

/**
 * Created by Thomas on 4/24/2016.
 */
public class Pair <L,R> {
    private L left;
    private R right;

    public Pair (L left, R right) {
        this.left = left;
        this.right = right;
    }
    public L getLeft(){
        return left;
    }
    public R getRight() {
        return right;
    }
    public void setLeft(L left){
        this.left = left;
    }
    public void setRight(R right){
        this.right = right;
    }
}
