package testscripts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pages.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//creating object of WebDriver class
		WebDriver driver = new ChromeDriver();
		
		//maximizing window to run the test case smoothly
		driver.manage().window().maximize();
		
		//defning implicit wait on global level to manage any load sync issues
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//defining explicit wait of 5 seconds at global level so that we can use it wherever necessary
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		//going to required website
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage LP = new LandingPage(driver);
		
		//locating User Email field and passing User Email
		driver.findElement(By.id("userEmail")).sendKeys("sunny938650@gmail.com");
		
		//locating User Password field and passing User Password
		driver.findElement(By.id("userPassword")).sendKeys("Kratos@1234");
		
		//Loacting Login Button and clicking on it
		driver.findElement(By.id("login")).click();
		
        //adding explicit wait of 5 seconds here so that all products get loaded on the screen
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		//There are lot of products being displayed on the screen and we want to purchase ZARA COAT 3 product
		//So we will create a list of webelements which will store all products in it so we need to some
		//common locator which is common to all products and we can use it to select all of them at once so
		//we have used findElements instead of findElement
		List <WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//we will use Java Stream to iterate through the product list, we have 5 product in the list
		//stream method is used to iterate through the list one by one and then we have added filter
		//and using product.findElement to restrict our search to that product only so that we can find
		//product title easily and we are matching that product title with ZARA COAT 3 and we are saying if
		//it has 3 product with same name return first one or if it does not have any product with this name
		//return null and we are storing the product in prod variable of WebElement type
		WebElement prod = products.stream().filter((product)->
		product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
		
		//loacting add to cart button by restrcting search scope to our selected product and clicking on it
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//defining explixit wait of 5 seconds till the time Product Added to Cart message gets visible on the 
		//screen
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		
		//defining explixit wait of 5 seconds till the time white loading screen goes off
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		//loacting cart buttuon and clicking on it
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//storing all the products in a list of webelements which is carProducts
		List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
		//writing Java streams to iterate through the product list present in the cart and it will 
		//return True or False based on ZARA COAT 3 is present in the product list of cart or not
		//anMatch is the filter in Java streams
		Boolean match =cartProducts.stream().anyMatch(cp -> cp.getText().equalsIgnoreCase("ZARA COAT 3"));
		
		//Putting TestNG assertion to check of True has been returned in match variable or not and using 
		//this we will be able to identify ZARA COAT 3 is present in the product list of the cart or not
		Assert.assertTrue(match);
		
		//locating Checkout button and clicking on it
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//creating object of Action class
		Actions a = new Actions(driver);
		
		//locating email field and pasing India to it. Since we are doing it with the help of action class
		//we need to add build and perform
		a.sendKeys(driver.findElement(By.cssSelector(".form-group input")), "India").build().perform();
		
		//adding explicit wait so that box showing all countries name should get visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-item")));
		
		//locating India at 2nd option and clicking on it 
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
		
		//locating Submit button and clicking on it
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		//extracting text "Thankyou for the order." from the confirmation page and stroing it in a String
		//variable ThanksPage
		String ThanksPage = driver.findElement(By.cssSelector(".hero-primary")).getText().trim();
		
		//writing TestNG assertion to match our extracted text with expected text and validating if they are
		//same or not
		Assert.assertTrue(ThanksPage.equalsIgnoreCase("Thankyou for the order."));
		
		//closing the browser
		driver.close();
	}

}
