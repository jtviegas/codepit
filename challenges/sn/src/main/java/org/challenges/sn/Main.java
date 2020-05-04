package org.challenges.sn;

import static java.lang.String.format;

public class Main {

    public static void main(String[] args){

        SparkProduct product = new SparkProductImpl(SparkProduct.PASTA, 35);
        VendingMachine vendingMachine = new VendingMachineImpl();

        System.out.println(format("products: %s", vendingMachine.listProducts()));
        System.out.println(format("balance: %s", vendingMachine.balance()));
        System.out.println(format("stock level for product %s: %s", product.getName(), vendingMachine.getStockLevel(product)));
        System.out.println(format("buying product %s", product.getName()));
        vendingMachine.buy(product, 35);
        System.out.println(format("stock level for product %s: %s", product.getName(), vendingMachine.getStockLevel(product)));
        System.out.println(format("balance: %s", vendingMachine.balance()));
        System.out.println(format("refunding product %s", product.getName()));
        vendingMachine.refund(product);
        System.out.println(format("balance: %s", vendingMachine.balance()));
        System.out.println(format("stock level for product %s: %s", product.getName(), vendingMachine.getStockLevel(product)));


    }

}
