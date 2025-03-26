package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//By default the output that comes in Cucumber is not in readable format so we have used monochrome 
//which will give us the result in readable format

//we are using html reporting so we are using one plugin for that any plugin that we use should be in key-
//value pair html is key type and its value where report will be stored 
@CucumberOptions(features="src/test/java/cucumber",glue="rahulshettyacademy.stepDefinitions",
monochrome=true, tags = "@Regression", plugin= {"html:target/cucumber.html"})

//TestNg does not come inbuilt with Cucumber so it requires to extend AbstractTestNGCucumberTests parent class
// which helps to understand TestNG assertions
public class TestNGTestRunner extends AbstractTestNGCucumberTests {}
