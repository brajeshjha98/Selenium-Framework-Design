package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.AbstractComponents;

public class CartPage extends AbstractComponents 
{

	WebDriver driver;
	public CartPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//loacting Checkout Button and creating checkoutEle object of Checkout icon  of type Weblement
	@FindBy(css = ".totalRow button")
	WebElement checkoutEle;

	//loacting the common locator for all product titles and creating list object of all product titles
	//present on the cart page
	@FindBy(css = ".cartSection h3")
	private List<WebElement> cartProducts;

	//Action method to match if the selcted Product is present in the Cart Page or not
	public Boolean VerifyProductDisplay(String productName) 
	{
		Boolean match = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	//Action method to click on Checkout icon
	public CheckoutPage goToCheckout() 
	{
		checkoutEle.click();
		return new CheckoutPage(driver);
	}

}
