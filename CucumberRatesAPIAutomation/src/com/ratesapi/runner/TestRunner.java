package com.ratesapi.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = "./features", glue = { "com.ratesapi.stepdefinitions" }, plugin = { "pretty",
		"html:reports/html", "json:reports/Cucumber.json"}, monochrome = true)
public class TestRunner {

}
