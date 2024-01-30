package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.*;
import io.restassured.response.Response;

public class UserTests2 {
      
	Faker faker ;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup(){
		
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPasswsord(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		
		//for LOGS 
		
		logger=LogManager.getLogger(this.getClass());
		logger.debug("debugging............");

	}

	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*********Creating User************************");
		Response response =UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*********User Created************************");
		
	}
	
	@Test(priority=2)
	public void testGetUserByName() 
	{
		logger.info("*********Read User Info************************");
		Response response =UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*********User Info is Read************************");
	}
	
	
	@Test(priority=3)
	public void testUpdateUser()
	{
		//update user payload 
		logger.info("*********Updating User Details************************");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		//response.then().log().body().statusCode(200); ---chai assertion 
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
		//Validation after updating
		Response responseAfterUpdate=UserEndPoints2.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		logger.info("*********User Details Updated***********************");
		
	}
	
	@Test(priority=4)
	public void testDeleteUser()
	{
		Response response=UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*********User Deleted************************");
	}
}

