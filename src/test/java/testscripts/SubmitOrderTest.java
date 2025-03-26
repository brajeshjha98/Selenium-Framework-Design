package testscripts;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutPage;
import pages.ConfirmationPage;
import pages.OrderPage;
import pages.ProductCatalogue;
import testcomponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
	
	public String ProductName = "ZARA COAT 3";
	public String CountryName = "India";
	
	//This test is placing order and check whether correcct message is displaying on Confirmation Page or not
	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException {
	
		//loginApplication is a method in LandingPage which accepts email and password as arguments and
		//return object of ProductCatalogue class
		ProductCatalogue pc = LP.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = pc.getProductList();
		pc.addProductToCart(input.get("product"));
		CartPage cp = pc.goToCartPage();
		Boolean match = cp.VerifyProductDisplay(input.get("product"));
		CheckoutPage checkoutPage = cp.goToCheckout();
		Assert.assertTrue(match);
		checkoutPage.selectCountry(CountryName);
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		String ThanksPage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(ThanksPage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}
	
	//This test is checking whether the product ordered is displaying on Orders Page or not and this test 
	//depends on submitOrder test to execute as submitorder test will place the order of desired product
	//and this test will verify if that product name of the order placed is being displayed on Order page 
	//or not
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue ProductCatalogue = LP.loginApplication("sunny938650@gmail.com", "Kratos@1234");
		OrderPage ordersPage = ProductCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(ProductName));
		
}
		
	//Extent Reports - 
	@DataProvider
	public Object[][] getData() throws IOException
	{	
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//testcomponents//CredentialsData.json");
		return new Object[][]  {{data.get(0)}, {data.get(1)}};
		
	}
	
		
//	 @DataProvider
//	  public Object[][] getData()
//	  {
//	    return new Object[][]  {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"}, {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
//	    
//	  }
	
// Creating Hashmap for each set of data makes the work difficult as In real time scenario we have multiple
// set of data and we can not create these many hasmaps and store the value in them manually. In real time
// we have Json file and we need to read that Json file and convert it to Hashmaps and make store all Hashmaps
// into a list and can use that list to run the test with each set of data
	
//	HashMap<String,String> map = new HashMap<String,String>();
//	map.put("email", "anshika@gmail.com");
//	map.put("password", "Iamking@000");
//	map.put("product", "ZARA COAT 3");
//	
//	HashMap<String,String> map1 = new HashMap<String,String>();
//	map1.put("email", "shetty@gmail.com");
//	map1.put("password", "Iamking@000");
//	map1.put("product", "ADIDAS ORIGINAL");

}
