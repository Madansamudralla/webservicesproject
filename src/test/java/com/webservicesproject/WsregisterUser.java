package com.webservicesproject;


import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.qmetry.qaf.automation.util.Validator;
import com.qmetry.qaf.automation.ws.Response;
import com.qmetry.qaf.automation.ws.rest.RestWSTestCase;
import com.sun.jersey.api.client.ClientResponse;

public class WsregisterUser extends RestWSTestCase{
	
	@Test
	public void registeruser() {
		Userbean bean = new Userbean();
		bean.fillRandomData();
		getWebResource("/register").header("Content-Type", "application/json").post(new Gson().toJson(bean));
	Response response = getResponse();
	System.out.println(response);
	JSONObject jsonObject = new JSONObject(response.getMessageBody());
	Validator.verifyThat(jsonObject.getString( "id").length(), Matchers.greaterThan(6));			

	jsonObject = new JSONObject();
	jsonObject.put("id", "5ad9e42dee11cb00014b25d2");
	getWebResource("/cart").header("Content-Type", "application/json").post(jsonObject.toString());
	ClientResponse clientResponse = getWebResource("/cart").header("Content-Type", "application/json").get(ClientResponse.class);
	System.out.println(clientResponse);
	}

}
