package testscripts;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.ProductCatalogue;
import testcomponents.BaseTest;

public class ErrorsValidationTest extends BaseTest {
	
	
	@Test(groups = {"ErrorHandling"}, retryAnalyzer = testcomponents.Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException 
	{

		LP.loginApplication("sunny938650@gmail.com", "Kratos@1235");
		Assert.assertEquals("Incorrect email or password.", LP.getErrorMessage());
	}
	
    //this method is to check if the wrong product has been added to the cart
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{
		String productName = "ZARA COAT 3";
		ProductCatalogue pc = LP.loginApplication("sunny938650@gmail.com", "Kratos@1234");
		pc.addProductToCart(productName);
		CartPage cp = pc.goToCartPage();
		Boolean match = cp.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}

}
