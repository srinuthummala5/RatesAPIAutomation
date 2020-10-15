package com.ratesapi.runner;

import io.cucumber.junit.CucumberOptions;

@CucumberOptions(features = "./features", glue = { "com.ratesapi.stepdefinitions" }, plugin = { "pretty",
		"html:reports/html", "json:reports/Cucumber.json" }, monochrome = true)
public class TestRunner {

}
