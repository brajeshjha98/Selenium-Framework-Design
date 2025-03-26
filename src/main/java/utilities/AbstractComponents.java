//This page contains resuable components used by methods in different page classes e.g. wait

package utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.CartPage;
import pages.OrderPage;

public class AbstractComponents {
	
	//creating this driver so that all methods of this class can use driver
	//driver coming from child class as argument to below constrcutor is limited to this constructor only
	//so we are intializing current class driver (declared below) with the driver came from child class
	//in constructor below
	WebDriver driver;
	
	//defining constructor to initialize the current class driver and to intialize the 
	public AbstractComponents(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//loacting Cart icon and creating cartholder object of Cart Icon of type Weblement
	@FindBy(css="[routerlink*='cart']")
	WebElement cartholder;
	
	//locating Order icon and creating orderHeader object of Order Icon of type Weblement
	@FindBy(css = "[routerlink*='myorders']")
	WebElement orderHeader;
	
	
	//method for explicit wait for element to appear
	public void waitForElementToAppear(By findBy) 
	{
		//declaring object of WebDriver wait class to use Explicit wait of 5 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		//visibilityOfElementLocated accepts By loactor in argument so In argument of this method
		//we have mentioned return type as By
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	//method for explict wait for element to disappear
	public void waitForElementToDisappear(WebElement FindBy) 
	{
		//declaring object of WebDriver wait class to use Explicit wait of 5 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		//invisibilityOf accepts webelement so In argument of this method we have mentioned return type
		//as webelement
		wait.until(ExpectedConditions.invisibilityOf(FindBy));
	}
	

	//defning waitForWebElementToAppear method which accepts weblement type of variable as argument
	//not By type of variable
	public void waitForWebElementToAppear(WebElement findBy) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	//Action method to click on Cart icon to go to the Cart Page
	public CartPage goToCartPage() {
		cartholder.click();
		//creating object of CartPage here only as clicking on cart icon will land us on to card page 
		//so instead declaring object of CartPage on test page we can write it here to optimize the test code
		//and return the object of CartPage so that we can use to access action methods and webelements of 
		//CartPage
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	//Action Method to click on Orders icon and go to the Order Page
	public OrderPage goToOrdersPage()
	{
		orderHeader.click();
		//same reason of creation of object for OrderPage as mentioend above for CartPage
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}

}
