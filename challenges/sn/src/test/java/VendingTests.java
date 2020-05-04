import org.challenges.sn.SparkProduct;
import org.challenges.sn.SparkProductImpl;
import org.challenges.sn.VendingMachine;
import org.challenges.sn.VendingMachineImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VendingTests {

    private static VendingMachine vendingMachine;

    @BeforeClass
    public static void init(){
        vendingMachine = new VendingMachineImpl();
    }
    // we can only use 4 types of sparks

    // we can list the products we should get 3
    @Test
    public void weShouldListTheProducts(){

        List<SparkProduct> expected = new ArrayList<SparkProduct>();
        expected.addAll(Arrays.asList(
                new SparkProductImpl("SparklingWater", 25)
                , new SparkProductImpl(SparkProduct.PASTA, 35)
                , new SparkProductImpl("Sparksoda", 45)));

        List<SparkProduct> actual = vendingMachine.listProducts();
        Assert.assertEquals( expected.size(), actual.size() );
        Assert.assertTrue(actual.containsAll(expected));

    }

    // we want to buy SparkPasta with an excess and get the right change
    @Test
    public void weShouldBuyAndGetChange(){
        SparkProduct product = new SparkProductImpl(SparkProduct.PASTA, 35);
        int expected = 5;

        int balance = vendingMachine.balance();
        int initialLevel = vendingMachine.getStockLevel(product);

        Assert.assertEquals( expected, vendingMachine.buy(product, 40) );
        Assert.assertEquals(initialLevel-1, vendingMachine.getStockLevel(product));
        Assert.assertEquals( balance+product.price(), vendingMachine.balance());

    }
    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionIfBuyWithNoEnoughMoney(){
        SparkProduct product = new SparkProductImpl(SparkProduct.PASTA, 35);

        vendingMachine.buy(product, 30);

    }

    @Test
    public void shouldRefundPreviousSale(){
        SparkProduct product = new SparkProductImpl(SparkProduct.PASTA, 35);

        int initialLevel = vendingMachine.getStockLevel(product);
        int balance = vendingMachine.balance();

        vendingMachine.buy(product, 35);
        Assert.assertEquals( balance+product.price(), vendingMachine.balance());
        Assert.assertEquals(initialLevel-1, vendingMachine.getStockLevel(product));
        Assert.assertEquals(product.price(), vendingMachine.refund(product));
        Assert.assertEquals(initialLevel, vendingMachine.getStockLevel(product));
        Assert.assertEquals( balance, vendingMachine.balance());

    }

    // we can replenish the stock



}
