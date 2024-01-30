package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests 
{
    Faker faker;
	
	
	@Test(priority=1,dataProvider = "Data",dataProviderClass = DataProviders.class)
	public void testPostUser(String userID,String userName,String fname,String lname,String userrmail,String pwd,String ph)
	{
		User userPayload=new User();
		faker=new Faker();
		userPayload.setId(faker.hashCode());
		//if u want to read from excel 
		//userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstname(fname);
		userPayload.setLastname(lname);
		userPayload.setEmail(userrmail);
		userPayload.setPasswsord(pwd);
        userPayload.setPhone(ph);		
        
        Response response =UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}

	
	@Test(priority=2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
    public void testGetUserByName(String userName)
    {
   	Response response= UserEndPoints.readUser(userName);
   		Assert.assertEquals(response.getStatusCode(), 200);
    }
	
	
	@Test(priority=3,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
     public void testDeleteUserByName(String userName)
     {
    	Response response= UserEndPoints.deleteUser(userName);
    		Assert.assertEquals(response.getStatusCode(), 200);
     }
}
