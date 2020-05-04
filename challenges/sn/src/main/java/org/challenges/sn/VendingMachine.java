package org.challenges.sn;

import java.util.List;

public interface VendingMachine {

    List<SparkProduct> listProducts();
    int buy(SparkProduct product, int sparks);
    int refund(SparkProduct product);
    int getStockLevel(SparkProduct product);
    int balance();

}
