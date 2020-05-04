package org.challenges.sn;

import java.util.*;

public class VendingMachineImpl implements VendingMachine {

    private final LinkedList<SparkProduct> sales;
    private final Map<SparkProduct,Integer> stock = new HashMap<>();

    public VendingMachineImpl(){
        sales = new LinkedList<>();
        stock.put( new SparkProductImpl("SparklingWater", 25), 10);
        stock.put( new SparkProductImpl("SparkPasta", 35), 10);
        stock.put( new SparkProductImpl("Sparksoda", 45), 10);
    }

    public List<SparkProduct> listProducts() {
        return new ArrayList<>(stock.keySet());
    }

    public int buy(SparkProduct product, int sparks) {

        if( product.price() > sparks  )
            throw new RuntimeException("not enough money");

        sales.add(product);
        Integer level = stock.get(product);
        stock.put(product, level-1);

        return sparks - product.price();
    }

    @Override
    public int refund(SparkProduct product) {

        SparkProduct lastSale = sales.getLast();
        if( ! lastSale.equals(product) )
            throw new RuntimeException("not the last sale");

        sales.removeLast();

        Integer level = stock.get(product);
        stock.put(product, level+1);

        return lastSale.price();
    }

    @Override
    public int getStockLevel(SparkProduct product) {
        return stock.get(product).intValue();
    }

    @Override
    public int balance() {
        return sales.stream().mapToInt(o -> o.price()).sum();
    }

}
