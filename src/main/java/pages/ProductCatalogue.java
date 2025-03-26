package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.AbstractComponents;

public class ProductCatalogue extends AbstractComponents {

		WebDriver driver;

		public ProductCatalogue(WebDriver driver) {
			//super keyword is used to pass information from Child class to Parent class
			//super keyword is used to send driver information to Parent class AbstractComponents as parent class
			//does not have idea about driver
			super(driver);
			
			//initialzing current class driver with driver declared on the test page and passed to initialize
			//current class driver so that it can 
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		//since we have whole list of products so we need to declare this webelement as List of weblements
		//List <WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		@FindBy(css = ".mb-3")
		List<WebElement> products;

		//waitForElementToAppear method accepts By locator as argument so we need to store the common locator
		//of all products in a variable of By type so we stored common locators of all product in 
		//ProductBy variable of By type declared above
		By ProductsBy = By.cssSelector(".mb-3");
		
		//findElement in addProductToCart method accepts By loactor as argument so storing Add to cart button 
		//in By type variable
		By addToCart = By.cssSelector(".card-body button:last-of-type");
		
		//waitForElementToAppear method accepts By loactor as argument so using By loacator of Product Added
		//to Cart message in By type variable
		By toastMessage = By.id("toast-container");
		
		
		//waitForElementToDisAppear method accepts By loactor as argument so using By loacator of 
		//of White loading screen in By type variable
		@FindBy(css = ".ng-animating")
		WebElement animating;
		
		//Action method to get Product List
		public List<WebElement> getProductList() 
		{
			//waiting for Products to appear on the page annd passing ProductBy variable of By type 
			waitForElementToAppear(ProductsBy);
			return products;
		}
		
		
		//Action method to select our desired product 
		public WebElement getProductByName(String ProductName) 
		{
			//we will use Java Stream to iterate through the product list returned by getProductList() method,
			//we have 5 product in the list stream method is used to iterate through the list one by one 
			//and then we have added filter and using product.findElement to restrict our search to that 
			//product only so that we can find product title easily and we are matching that product title 
			//with ZARA COAT 3 and we are saying if it has 3 product with same name return first one or 
			//if it does not have any product with this name return null and we are storing the 
			//product in prod variable of WebElement type and returning it
			WebElement prod = getProductList().stream()
					.filter((product) -> product.findElement(By.cssSelector("b")).getText().equals(ProductName))
					.findFirst().orElse(null);
			return prod;
		}
		
		
		//Action method to click on Add to cart icon of desired product and wait till Product
		//added to cart message displays successfully.
		public void addProductToCart(String ProductName) 
		{
			//stroing product name return by getProductByName method into a prod variable of Webelement type
			WebElement prod = getProductByName(ProductName);
			
			//now using prod variable to limit the scope of our search to find out the add to cart button
			//and passing addToCart variable of By type to findElement and after finding Add to Cart button
			//we are clicking on it
			prod.findElement(addToCart).click();
			
			//adding explicit wait of 5 seconds fetched from AbstractComponents Parent class to wait till 
			//Product added to cart message displays successfully.
			waitForElementToAppear(toastMessage);
			
			//adding explicit wait of 5 seconds fetched from AbstractComponents Parent class to wait till 
			//white screen goes off from the screen
			waitForElementToDisappear(animating);
			
		}

}
