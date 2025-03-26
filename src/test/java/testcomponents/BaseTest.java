package testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LandingPage;

public class BaseTest {
	
	//declaring variables here so that all methods of this class can access them
	public WebDriver driver;

	public LandingPage LP;

	//This aim if this method is to read Global.properties file and to check which browser name is there
	//in that file and based on the browser it has to create the driver of respective browser
	public WebDriver InitializeDriver() throws IOException {

		//properties class in Java Utils class is used to read .poperties file so creating object of 
		//properties class
		Properties prop = new Properties();
		
		//load method of Properties class accepts object of FileInputStram so creating object of 
		//FileInputStream class
		//System.getProperty is used to get the path of folder in which we are currently in so it will pull
		// the path of our project
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//resources//GlobalData.properties");
		prop.load(fis);

		//using ternary operator to catch the Mavan parameter coming through Command Prompt
		//here System.getProperty("browser") is catching Maven parameter browser
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				             :prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			
			//Chromeoptions class helps to make configuraion changes of Chrome Browser and helps to run 
			//the tests in headless mode
			
			//headless mode means browser will not open up and we will not be able to see the test cases
			//visually however test cases will run in the backend in the chrome browser by Chrome Engine
			
			//creating object of ChromeOptions class
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless"))
			{
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
		    //driver.manage().window().setSize(new Dimension(1440,900));// full screen
		} 
		
		else if (browserName.equalsIgnoreCase("firefox")) 
		{

			driver = new FirefoxDriver();
		} 
		else if (browserName.equalsIgnoreCase("edge")) 
		{

			driver = new EdgeDriver();
		}
		
		//defining implicit wait for 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//maximizing window
		driver.manage().window().maximize();
		return driver;

	}
	
	//method to convert json data to string and then convert String to HashMap and then create the list of
	//Hasmaps to send multiple set of data as one Hashmap respresent one set of data
	public List<HashMap<String, String>> getJsonDataToMap(String FilePath) throws IOException
	{
		// Java has FileUtils package which has readFileToString class which helps to read json file
		// and convert all Json content to String
		
		//we also need to provide the standard encoding format which is UTF_8 as String wants in which 
		//encoding format String should be written
		String jsonContent = FileUtils.readFileToString(new File(FilePath), StandardCharsets.UTF_8);
		
	    //Jackson Databind helps to convert String to HashMap
		
		//creating object of ObjectMapper class and using that object to access the method readValue which
		//will read string content and will convert the string into the hashmap and we also want to have the 
		//list of Hashmaps as one Hashmap repersents on set of data (User Id, Password, Product Name), 
		// and another Hashmap reprsents another set of data (User Id, Password, Product Name) so we created
		//a list which will store all Hashmaps as a single entity
		
		//TypeReference helps to convert the String to the fomrat we want like in this case we want to 
		//convert String to List of Hashmaps
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, 
				new TypeReference<List<HashMap<String, String>>>(){});
	 
		return data; //{{HashMap1}, {HashMap2}}
	
	}
	
	//Method to take screenshot
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		//In every method we are initializing local driver variable with the driver coming from the testcase
		//so here initialization of local variable driver will come from Listener 
		
		//we can not give any driver that we want so initialising driver of this method from Listener 
		
		//creating object of TakesScreenshot class
		TakesScreenshot ts = (TakesScreenshot)driver;
		
		//taking screenshot and defining type of screenshot whether it should be File type, byte type etc.
		File sstype = ts.getScreenshotAs(OutputType.FILE);
		
		//giving the file path where screenshot should be stored and with which name
		File filepathname = new File(System.getProperty("user.dir") + "/reports/" + testCaseName + ".png");
		
		//storing the taken screenshot with required name and at required path
		FileUtils.copyFile(sstype, filepathname);
		
		//returning File Path where screenshot is stored
		return System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
			
	}

	//This method is used to intialize driver and go the website and we need to run it before every test case
	//so we used BeforeMethod and AfterMethod
	
	//we put "alwaysRun = True" beacuse if we want to run few test cases as a group then TestNG will not run
	//methods under @BeforeMethod and @AfterMethod but these methods are necessary to run before
	//of any test case so we use alwaysRun = true
		
	@BeforeMethod(alwaysRun =true)
	public LandingPage launchApplication() throws IOException 
	{

		// InitializeDriver() is the method in this class defined above whose task is to initialize driver
		// of the respective browser mentioned in GlobalData.properties file
		driver = InitializeDriver();
		
		//creating object of LandingPage class to access the method goToWebsite
		LP = new LandingPage(driver);
		LP.goToWebsite();
		
		//returning object of LandingPage class
		return LP;
	}

	//we want to close the browser after the execution of each test case to we have used AfterMethod
	@AfterMethod(alwaysRun =true)
	public void tearDown() 
	{
		driver.close();
	}

}
