package dtm;

import org.testng.annotations.Test;

public class CheckoutTest {
    @Test(groups = {"smoke", "regression"})
    public void testCheckoutSuccess() {
        System.out.println("Chay CheckoutTest -> testCheckoutSuccess (Nhóm: smoke, regression)");
    }

    @Test(groups = {"regression"})
    public void testCheckoutEmptyCart() {
        System.out.println("Chay CheckoutTest -> testCheckoutEmptyCart (Nhóm: regression)");
    }
}