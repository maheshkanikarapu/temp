package testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="features",glue="stepdefinitions",monochrome=true,plugin={"html:target/cucumber-html-report","json:target/cucumber-json-report"},tags={"@P1","~@F2"})
public class Runner {

}
