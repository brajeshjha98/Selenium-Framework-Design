package testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.ExtentReportNG;

public class Listeners extends BaseTest implements ITestListener {
	
	ExtentTest test;
	ExtentReports extent = ExtentReportNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe

	
	@Override
	public void onTestStart(ITestResult result) {
		
		//this is to initiate Extent Report and create test entry in the Extent Report and result argument
		//holds the name of Test Method which is running so we can access the name of Test Method which is
		//currently using this listener using .getMethod().getMethodName() and pass it to extent.createTest
		//so that same Test case name created in Extent reports as well
		test = extent.createTest(result.getMethod().getMethodName());
		
		//unique thread id(ErrorValidationTest)->test
		extentTest.set(test);
	}

	
	@Override
	public void onTestSuccess(ITestResult result) {
		
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	
	@Override
	public void onTestFailure(ITestResult result) {
		
		//we don't want just Test Fail message we also want what the error message it is throwing, so this
		//error message will get stored at result variable and we can access error message using 
		//.getThrowable() 
		extentTest.get().fail(result.getThrowable());
		try {
			
			//since reult variable stores all information about the testcase that currently using it so we 
			//can fetch driver details using result variable. Using result variable we are accessing which
			//test class current test belongs to in TestNG xml file and then it will fetch the real class of
			// that test and fetch driver details from that class
			driver = (WebDriver)result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
			} 
		
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		String filePath = null;
		try {
			
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//This will help to add screenshot to our Extent Report with same test method name 
		//above try catch block is due to below line of code only beacuse if filepath remains empty or null
		//how that sitiuation will be handled so used try catch
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
		//extent.flush() helps to finally wrap the report and generate it
		extent.flush();
	}
	
	

}
