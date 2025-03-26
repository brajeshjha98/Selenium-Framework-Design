package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {
	
	public static ExtentReports getReportObject()
	{
		
		String path =System.getProperty("user.dir")+"//reports//index.html";
		
		//ExtentSparkReporter is the class which helps to set the configuration of extent reports, creating
		//object of ExtentSparkReporter class and passing the path (where we want to store the report) as
		//argument
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		
		//using reporter object we are using setReportName method under ExtentSparkReporter class to set the 
		//name of the report
		reporter.config().setReportName("Web Automation Results");
		
		//using reporter object we are using setReportName method under ExtentSparkReporter class to set the 
		//name of the report
		reporter.config().setDocumentTitle("Test Results");
		
		//ExtentReports is the main class which helps to create whole report
		//creating of ExtentReports class
		ExtentReports extent =new ExtentReports();
		
		//using extent object of ExtentReports class we are accessing attachReporter method to attach 
		//all our configuration made using reporter object of ExtentSparkReporter class hence passing
		//reporter object to set all pur desired configuration to our extent reports
		extent.attachReporter(reporter);
		
		//adding tester name to extent report, this is optional
		extent.setSystemInfo("Tester", "Brajesh");
		
		//returning object of ExtentReports class
		return extent;		
	}

}
