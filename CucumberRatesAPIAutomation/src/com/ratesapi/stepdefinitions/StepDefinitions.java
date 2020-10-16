package com.ratesapi.stepdefinitions;

import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hamcrest.Matchers;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinitions {

	RequestSpecification request;
	Response response;

	@Given("User request Rates API URL")
	public void user_request_rates_api_for_latest_foreign_exchange_rates() {
		RestAssured.baseURI = "https://api.ratesapi.io/api/";
		request = RestAssured.given();
	}

	@When("User request for an API {string}")
	public void user_request_for_an_api(String apiURL) {
		response = request.contentType("application/json").when().get(apiURL);
	}

	@Then("User validates status and body of the response")
	public void user_validates_status_and_body_of_the_response() {
		response.then().statusCode(200);
		response.then().statusLine(Matchers.containsString("OK"));
		response.then().body("rates.size()", Matchers.greaterThan(0));
	}

	@And("Validate Rates are quoted against the Euro by default")
	public void validate_base_default_to_euro() {
		response.then().body("base", Matchers.equalTo("EUR"));
	}

	@When("User request for an incomplete api")
	public void user_request_for_an_incomplete_api() {
		response = request.contentType("application/json").when().get("");
	}

	@Then("User validates error message of the response")
	public void user_validates_error_message_of_the_response() {
		response.then().statusCode(400);
		String errorMessage = response.jsonPath().get("error").toString();
		assertThat(errorMessage, Matchers.equalTo("time data 'api' does not match format '%Y-%m-%d'"));
	}

	@Then("User validates that the response matches for the current date")
	public void user_validates_that_the_response_matches_for_the_current_date() {
		response.then().statusCode(200);
		String date = response.jsonPath().get("date").toString();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String currentDate = dtf.format(LocalDate.now().minusDays(1));
		String expectedDate = (date.equals(currentDate)) ? currentDate : dtf.format(LocalDate.now());
		assertThat(date, Matchers.equalTo(expectedDate));
	}

	@When("User request for historical record")
	public void user_request_for_historical_record(DataTable dataTable) {
		String endPoint = dataTable.cell(1, 0);
		response = request.contentType("application/json").when().get("/" + endPoint);
	}

	@When("User request for future rates {string}")
	public void user_request_for(String endPoint) {
		response = request.contentType("application/json").when().get("/" + endPoint);
	}
}
