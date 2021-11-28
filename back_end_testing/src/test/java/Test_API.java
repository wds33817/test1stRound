import org.json.simple.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Test_API {

    String username = "wds33817";
    String invalid_username = " .././";
    String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJ3ZHMzMzgxNyIsImVtYWlsIjoibXVoYW1kMTQxMDFAZ21haWwuY29tIiwiaWF0IjoxNjM4MTA2NTMzLCJleHAiOjE2MzgzNjU3MzN9.AWs8PbQRy1dPEQ66llGJiGbWE_7qjmSDwHwou1zm4-bCYB7CkaHUqDhpy51uicgkK-aqFP2f1tu1lKjOllENbw";
    String expired_token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiIxNjA0NzQ0NzU5IiwiZW1haWwiOiIxNjA0NzQ0NzU5QHFxLmNvbSIsImlhdCI6MTYzNzYzMzM1OSwiZXhwIjoxNjM3ODkyNTU5fQ.yrER9BFeQLKnx7mPZCNQBYyvMve1P5xbZvd2RTKwellhuvDZn3enWKZwRLNMj-pK24I8GSsjt44c840DGHF2ig";
    String invalid_token = "......////.....////";
    String null_token = "";
    String empty_token = "  ";

    @Test
    void test_application_authorisation_post() {
        RestAssured.baseURI="https://supervillain.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("key", username);
        requestParams.put("email", "muhamd14101@gmail.com");
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
        header("Authorization", token).
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(200);//validate JWT
    }

    @Test
    void test_application_authorisation_get_with_invalid_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", invalid_token).
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(403);//invalidate JWT
    }

    @Test
    void test_application_authorisation_get_with_expired_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", expired_token).
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(403);//invalidate JWT
    }

    @Test
    void test_application_authorisation_get_with_empty_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", empty_token).
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(403);//invalidate JWT
    }

    @Test
    void test_application_authorisation_get_with_null_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", null_token).
        when().
        get("https://supervillain.herokuapp.com/auth/verifytoken").
        then().
        statusCode(403);//invalidate JWT
    }

    @Test
    void test_user_authentication_register_post_expect_200() {
        JSONObject requestParams = new JSONObject();
        Double num = Math.random() * 10000000;
        String un = num.toString();
        requestParams.put("username", un);
        requestParams.put("password", "Test111111");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/register").
        then().
        statusCode(200);//already registered user
    }

    @Test
    void test_user_authentication_register_invalid_username_post_expect_400() {
        JSONObject requestParams = new JSONObject();
        Double num = Math.random() * 10000000;
        //String username = num.toString();
        requestParams.put("username", num);
        requestParams.put("password", "Test111111");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/register").
        then().
        statusCode(400);//invalid username type double
    }

    @Test
    void test_user_authentication_register_post() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Test111111");
        requestParams.put("password", "Test111111");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/register").
        then().
        statusCode(400);//already registered user
    }

    @Test
    void test_user_authentication_user_login_with_invalid_username_post_expect_400() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", invalid_username);
        requestParams.put("password", "Test111111");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/login").
        then().
        statusCode(400);
    }

    @Test
    void test_user_authentication_user_login_with_invalid_username_and_password_post_expect_400() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", " .././");
        requestParams.put("password", ">?>?>?<<<");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/login").
        then().
        statusCode(400);
    }

    @Test
    void test_user_authentication_login_post() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Test111111");
        requestParams.put("password", "Test111111");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/login").
        then().
        statusCode(200);//post login user 200 login successfully
    }

    @Test
    void test_user_authentication_login_post_with_valid_username_and_invalid_password() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Test111111");
        requestParams.put("password", "fewfewfw");
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/auth/user/login").
        then().
        statusCode(400);
    }

    @Test
    void test_user_leaderboard_return_list_users_get() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        when().
        get("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(200);//successfully return a list of users
    }

    @Test
    void test_user_leaderboard_return_list_users_get_with_expired_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", expired_token).
        when().
        get("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_return_list_users_get_with_invalid_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", invalid_token).
        when().
        get("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_return_list_users_get_with_empty_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", empty_token).
        when().
        get("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_return_list_users_get_with_null_token() {
        given().
        header("Content-Type", "application/json").
        header("Authorization", null_token).
        when().
        get("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_valid_token() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("score", 22);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(201);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_valid_token_duplicate_data() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("score", 22);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(400);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_invalid_token() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*10000000;
        String str = dbl.toString();
        requestParams.put("username", str);
        requestParams.put("score", 22);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", invalid_token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_empty_token() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*10000000;
        String str = dbl.toString();
        requestParams.put("username", str);
        requestParams.put("score", 22);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", empty_token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_null_token() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*10000000;
        String str = dbl.toString();
        requestParams.put("username", str);
        requestParams.put("score", 22);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", null_token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_expired_token() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*10000000;
        String str = dbl.toString();
        requestParams.put("username", str);
        requestParams.put("score", 22);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", expired_token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_valid_token_but_with_null_username_and_null_score() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*10000000;
        String str = dbl.toString();
        requestParams.put("username", null);
        requestParams.put("score", null);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(400);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_valid_token_but_with_null_username() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*10000000;
        String str = dbl.toString();
        requestParams.put("username", null);
        requestParams.put("score", 123);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(400);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_valid_token_but_with_null_score() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*10000000;
        String str = dbl.toString();
        requestParams.put("username", "username");
        requestParams.put("score", null);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(400);
    }

    @Test
    void test_user_leaderboard_add_new_user_post_with_highest_score() {
        JSONObject requestParams = new JSONObject();
        Double dbl = Math.random()*100000;
        String str = dbl.toString();
        requestParams.put("username", str);
        requestParams.put("score", 2147483647);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        post("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(201);
    }

    @Test
    void test_user_leaderboard_update_user_post_with_valid_token() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("score", 222);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", token).
        body(json).
        when().
        put("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(204);//put 204 means updated successfully
    }

    @Test
    void test_user_leaderboard_update_user_post_with_invalid_token() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("score", 222);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", invalid_token).
        body(json).
        when().
        put("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_update_user_post_with_empty_token() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("score", 222);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", empty_token).
        body(json).
        when().
        put("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_update_user_post_with_null_token() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("score", 222);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", null_token).
        body(json).
        when().
        put("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_update_user_post_with_expired_token() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("score", 222);
        String json = requestParams.toJSONString();
        given().
        header("Content-Type", "application/json").
        header("Authorization", expired_token).
        body(json).
        when().
        put("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(403);
    }

    @Test
    void test_user_leaderboard_delete_users_with_valid_token() {
        given().
        header("delete-key", "Test111111").
        header("Authorization", token).
        when().
        delete("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(401);//?unable to authenticate key, not sure, already raised a bug
    }

    @Test
    void test_user_leaderboard_delete_users_with_invalid_token() {
        given().
        header("delete-key", "Test111111").
        header("Authorization", invalid_token).
        when().
        delete("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(401);//?unable to authenticate key, not sure, already raised a bug
    }

    @Test
    void test_user_leaderboard_delete_users_with_empty_token() {
        given().
        header("delete-key", "Test111111").
        header("Authorization", empty_token).
        when().
        delete("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(401);//?unable to authenticate key, not sure, already raised a bug
    }

    @Test
    void test_user_leaderboard_delete_users_with_null_token() {
        given().
        header("delete-key", "Test111111").
        header("Authorization",null_token).
        when().
        delete("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(401);//?unable to authenticate key, not sure, already raised a bug
    }

    @Test
    void test_user_leaderboard_delete_users_with_expired_token() {
        given().
        header("delete-key", "Test111111").
        header("Authorization",expired_token).
        when().
        delete("https://supervillain.herokuapp.com/v1/user").
        then().
        statusCode(401);//?unable to authenticate key, not sure, already raised a bug
    }
}