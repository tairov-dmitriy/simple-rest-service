package com.dmitriy.testtasks.restservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestserviceApplicationTests {

	private ObjectMapper mapper = new ObjectMapper();

	private PurchaseResponse getResponse(String requestJSON) throws JsonProcessingException {
		PurchaseRequest request = mapper.readValue(requestJSON, PurchaseRequest.class);
		return request.check();
	}

	private void checkOK(PurchaseResponse response) {
		assertThat(response.getStatus()).isEqualTo(PurchaseResponse.SUCCESS_STATUS);
	}

	private void checkError(PurchaseResponse response, String expectedError) {
		assertThat(response.getStatus()).isEqualTo(PurchaseResponse.ERROR_STATUS);
		assertThat(response.getErrors().contains(expectedError)).isTrue();
	}

	@Test
	void requestCheckTest() throws JsonProcessingException {

		// successful tests
		checkOK(getResponse("{ \"seller\":\"123534251\", \"customer\":\"648563524\", \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"},{\"name\":\"water\",\"code\":\"3656352437590\"}] }"));
		checkOK(getResponse("{ \"seller\":\"023920202\", \"customer\":\"100093200\", \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"},{\"name\":\"water\",\"code\":\"3656352437590\"}, {\"name\":\"bread\",\"code\":\"7777777777777\"}] }"));

		// missing tests
		checkError(getResponse("{}"), "Missed field 'seller'");
		checkError(getResponse("{}"), "Missed field 'customer'");
		checkError(getResponse("{}"), "Missed field 'products'");
		checkError(getResponse("{\"customer\":\"648563524\",\"products\":[{\"name\":\"water\",\"code\":\"3656352437590\"}]}"),
				"Missed field 'seller'");
		checkError(getResponse("{\"seller\":\"123534251\",\"products\":[{\"name\":\"water\",\"code\":\"3656352437590\"}]}"),
				"Missed field 'customer'");
		checkError(getResponse("{\"seller\":\"123534251\",\"customer\":\"648563524\"}"),
				"Missed field 'products'");
		checkError(getResponse("{\"seller\":\"123534251\",\"customer\":\"648563524\",\"products\":[]}"),
				"Empty products list");
		checkError(getResponse("{ \"seller\":\"123534251\", \"customer\":\"648563524\", \"products\":[{\"code\":\"2364758363546\"},{\"name\":\"water\"}] }"),
				"Missed product`s field 'name'");
		checkError(getResponse("{ \"seller\":\"123534251\", \"customer\":\"648563524\", \"products\":[{\"code\":\"2364758363546\"},{\"name\":\"water\"}] }"),
				"Error in product 'water': missed field 'code'");
		checkError(getResponse("{ \"seller\":\"123534251\", \"customer\":\"648563524\", \"products\":[{}] }"),
				"Error in product 'null': missed field 'code'");

		// request fields tests
		checkError(getResponse("{ \"seller\":\"0239202024\", \"customer\":\"100093200\", \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"}] }"),
				"Field 'seller' must contains 9 digits");
		checkError(getResponse("{ \"seller\":\"023-00202\"}"),
				"Field 'seller' must contains 9 digits");
		checkError(getResponse("{ \"seller\":\"123534251\", \"customer\":\"0QWErty24\", \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"}] }"),
				"Field 'customer' must contains 9 digits");
		checkError(getResponse("{\"customer\":\"90239202\", \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"}] }"),
				"Field 'customer' must contains 9 digits");

		// product fields tests
		checkError(getResponse("{ \"seller\":\"123534251\", \"customer\":\"648563524\", \"products\":[{\"name\":\"milk\",\"code\":\"236475836546\"},{\"name\":\"water\",\"code\":\"3656352437590\"}] }"),
				"Error in product 'milk': field 'code' must contains 13 digits");
		checkError(getResponse("{\"products\":[{\"name\":\"milk\",\"code\":\"23_4758363546\"},{\"name\":\"water\",\"code\":\"03656352437590\"}] }"),
				"Error in product 'milk': field 'code' must contains 13 digits");
		checkError(getResponse("{ \"seller\":\"123534251\", \"customer\":\"648563524\", \"products\":[{\"name\":\"milk\",\"code\":\"2364758363546\"},{\"name\":\"water\",\"code\":\"03656352437590\"}] }"),
				"Error in product 'water': field 'code' must contains 13 digits");
		checkError(getResponse("{\"products\":[{\"name\":\"milk\",\"code\":\"23647F8363546\"},{\"name\":\"water\",\"code\":\"365635O437590\"}] }"),
				"Error in product 'water': field 'code' must contains 13 digits");
	}
}
