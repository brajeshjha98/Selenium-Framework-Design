package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.AbstractComponents;

public class CheckoutPage extends AbstractComponents {

		WebDriver driver;

		public CheckoutPage(WebDriver driver) 
		{
			// TODO Auto-generated constructor stub
			super(driver);
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		//loacting Submit Button and creating submit object of Submit button of type Weblement
		@FindBy(css = ".action__submit")
		private WebElement submit;

		//loacting Country field and creating country object of Country box of type Weblement
		@FindBy(css = "[placeholder='Select Country']")
		private WebElement country;

		//loacting India which is 2nd option from the given option box and creating selectCountry object
		//of 2nd option India of type Webelement 
		@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
		private WebElement selectCountry;

		//waitForElementToAppear method in AbstractComponents Parent class accepts By variable as argument
		//so stored the locator of country options box  in the variable of type By
		private By results = By.cssSelector(".ta-results");

		//Action method to click on Country field and sending required Country Name
		public void selectCountry(String countryName) {
			Actions a = new Actions(driver);
			a.sendKeys(country, countryName).build().perform();
			
			//adding explicit wait to wait till all country option appears
			waitForElementToAppear(results);
			selectCountry.click();
		}

		//Action method to click on Submit button
		public ConfirmationPage submitOrder() {
			submit.click();
			return new ConfirmationPage(driver);

		}


}
