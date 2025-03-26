package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.AbstractComponents;

//we are using inheritence here as we want to use all action methods and components of AbstractCompenets 
//Parent class in this class. Child class LadningPage can access Parent class variables and Objects
public class LandingPage extends AbstractComponents {

//declaring local driver variable of WebDriver type	
WebDriver driver;
	
	public LandingPage (WebDriver driver)
	{
		//super keyword is used to send driver information to Parent class AbstractComponents as parent class
		//does not have idea about driver
		
		//super keyword is used to pass information from Child class to Parent class
		
		//In Parent class AbstractComponents there will be a constrcutor to catch this driver infomration
		super(driver);
		
		//intializing empty current class driver variable with actual driver created in test class 
		this.driver = driver;
		
		//Since FindBy in PageFeactory did not know about driver so initialize driver using below line of code
		//this in below code refers to current class page objects like Webelement userEmail, Password, 
		//loginButton, error message which will be intialized by driver.
		
		//So once this constrcutor is called automatically all page objects will get created or instantiated.
		//The second argument, this, refers to the current instance of the LandingPage class, allowing 
		//PageFactory to initialize the WebElements within this class. 
		PageFactory.initElements(driver, this);
	}
	
	//Page Factory design pattern to reducing the syntax for creating an web element
	
	
	//loacting userEmail field and creating userEmail object of UserEmail box of type Weblement
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	@FindBy(id="userEmail")	
	WebElement userEmail;
	
	
	//loacting userPassword and creating password object of password box of type Weblement
	//WebElement password = driver.findElement(By.id("userPassword"));
	@FindBy(id="userPassword")
	WebElement password;
	
	//locating Login button and creating loginButton object of Login Button of type Weblement
    //WebElement loginButton = driver.findElement(By.id("login"));
	@FindBy(id="login")
	WebElement loginButton;
	
	//loacting Login Error message and creating errorMessage object of Login error message of type Weblement
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	
	//Action method for Login and since we will land on ProductCatalogue Page so this method should
	//return us the object of ProductCatalogue class
	public ProductCatalogue loginApplication(String email, String pass) {
		userEmail.sendKeys(email);
		password.sendKeys(pass);
		
		//clicking on login button
		loginButton.click();
		ProductCatalogue ProductCatalogue = new ProductCatalogue(driver);
		return ProductCatalogue;
	}
	
	//Action method to get the text of Error Mesage
	public String getErrorMessage()
	{
		//adding explicit wait to wait till Login error message appears
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	//Acion method to go to the website
	public void goToWebsite() {
		driver.get("https://rahulshettyacademy.com/client");
	}

	
}
