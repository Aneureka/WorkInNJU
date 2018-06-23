package constant;

/**
 * Created by Hiki on 2017/5/14.
 */
public enum PriceType {

    /**
     * 前复权
     */
    BEFORE("before"),

    /**
     * 不复权
     */
    NORMAL("normal"),

    /**
     * 后复权
     */
    AFTER("after");

    private String priceType;

    private PriceType(String priceType){
        this.priceType = priceType;
    }

    @Override
    public String toString(){
        return priceType;
    }

}
