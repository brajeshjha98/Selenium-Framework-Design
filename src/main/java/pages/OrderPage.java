package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.AbstractComponents;

public class OrderPage extends AbstractComponents {
	
	WebDriver driver;
	
	public OrderPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

//	@FindBy(css = ".totalRow button")
//	WebElement checkoutEle;

	//locating product list on orders page and creating productNames object of product list with thet list of
	//weblements type
	@FindBy(css = "tr td:nth-child(3)")
	private List<WebElement> productNames;

	//Action method to check if the name of the order placed is displaying on the Orders Page or not
	public Boolean VerifyOrderDisplay(String productName) {
		Boolean match = productNames.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
		return match;

	}


}
