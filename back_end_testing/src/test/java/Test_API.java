import org.json.simple.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Test_API {

    @Test
    void test_application_authorisation_post() {
        RestAssured.baseURI="https://supervillain.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("key", "wds23817");
        requestParams.put("email", "wds23817@gmail.com");
        request.body(requestParams.toJSONString());
        Response response = request.post("/auth/gentoken/");
        int statusCode = response.getStatusCode();
        String statusString = response.getContentType();
        // duplicated key for user
        // expect 400
        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(statusString, "application/json; charset=utf-8");
    }

    @Test
    void test_application_authorisation_get() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMyMzgxNyIsImVtYWlsIjoid2RzMjM4MTdAZ21haWwuY29tIiwiaWF0IjoxNjM3ODM0OTQ3LCJleHAiOjE2MzgwOTQxNDd9.ElOgyxtmElBjMLvqzzegSked2hrn3i3-bX69KCxhf-FX-071ju6g51FCzGrtpC5PK-cp0XEo9lTDbns-uZdXIg").
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(200);//validate JWT
    }

    @Test
    void test_application_authorisation_get_with_malformed() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", "......////").
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(403);//invalidate JWT
    }

    @Test
    void test_application_authorisation_get_with_invalid() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMzODE3IiwiZW1haWwiOiJ3ZHMzODE3QGdtYWlsLmNvbSIsImlhdCI6MTYzNzU0NzE2OSwiZXhwIjoxNjM3ODA2MzY5fQ.8F0VBQDRV2lAAy5oS925Ezmur3bxO8omxeF16BXznOYLNa-PmGSToWGwXnIhQOumHgsAt-7PNzhfeK-_OZOSZw").
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(403);//invalidate JWT
    }

    @Test
    void test_application_authorisation_get_with_missing_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", "").
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(403);//invalidate JWT
    }

    @Test
    void test_user_authentication_register_post() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Test111111");
        requestParams.put("password", "Test111111");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMyMzgxNyIsImVtYWlsIjoid2RzMjM4MTdAZ21haWwuY29tIiwiaWF0IjoxNjM3ODM0OTQ3LCJleHAiOjE2MzgwOTQxNDd9.ElOgyxtmElBjMLvqzzegSked2hrn3i3-bX69KCxhf-FX-071ju6g51FCzGrtpC5PK-cp0XEo9lTDbns-uZdXIg").
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/register").
        then().
        statusCode(400);//already registered user
    }



    @Test
    void test_user_authentication_login_post() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Test111111");
        requestParams.put("password", "Test111111");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMyMzgxNyIsImVtYWlsIjoid2RzMjM4MTdAZ21haWwuY29tIiwiaWF0IjoxNjM3ODM0OTQ3LCJleHAiOjE2MzgwOTQxNDd9.ElOgyxtmElBjMLvqzzegSked2hrn3i3-bX69KCxhf-FX-071ju6g51FCzGrtpC5PK-cp0XEo9lTDbns-uZdXIg").
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/login").
        then().
        statusCode(200);//post login user 200 login successfully
    }

    @Test
    void test_user_leaderboard_return_list_users_get() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMyMzgxNyIsImVtYWlsIjoid2RzMjM4MTdAZ21haWwuY29tIiwiaWF0IjoxNjM3ODM0OTQ3LCJleHAiOjE2MzgwOTQxNDd9.ElOgyxtmElBjMLvqzzegSked2hrn3i3-bX69KCxhf-FX-071ju6g51FCzGrtpC5PK-cp0XEo9lTDbns-uZdXIg").
        when().
        get("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(200);//successfully return a list of users
    }

    @Test
    void test_user_leaderboard_add_new_user_post() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Test111111");
        requestParams.put("score", 22);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMyMzgxNyIsImVtYWlsIjoid2RzMjM4MTdAZ21haWwuY29tIiwiaWF0IjoxNjM3ODM0OTQ3LCJleHAiOjE2MzgwOTQxNDd9.ElOgyxtmElBjMLvqzzegSked2hrn3i3-bX69KCxhf-FX-071ju6g51FCzGrtpC5PK-cp0XEo9lTDbns-uZdXIg").
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(400);//the username is already existed so return 400
    }

    @Test
    void test_user_leaderboard_update_user_post() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Test111111");
        requestParams.put("score", 222);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMyMzgxNyIsImVtYWlsIjoid2RzMjM4MTdAZ21haWwuY29tIiwiaWF0IjoxNjM3ODM0OTQ3LCJleHAiOjE2MzgwOTQxNDd9.ElOgyxtmElBjMLvqzzegSked2hrn3i3-bX69KCxhf-FX-071ju6g51FCzGrtpC5PK-cp0XEo9lTDbns-uZdXIg").
        body(json).
        when().
        put("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(204);//put 204 means updated successfully
    }

    @Test
    void test_user_leaderboard_delete_users() {

        given().
        header("delete-key", "Test111111").
        header("Authorization", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMyMzgxNyIsImVtYWlsIjoid2RzMjM4MTdAZ21haWwuY29tIiwiaWF0IjoxNjM3ODM0OTQ3LCJleHAiOjE2MzgwOTQxNDd9.ElOgyxtmElBjMLvqzzegSked2hrn3i3-bX69KCxhf-FX-071ju6g51FCzGrtpC5PK-cp0XEo9lTDbns-uZdXIg").
        when().
        delete("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(401);//?unable to authenticate key, not sure, already raised a bug
    }
}