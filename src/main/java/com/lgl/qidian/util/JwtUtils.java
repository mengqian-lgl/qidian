package com.lgl.qidian.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @auther 刘广林
 */
public class JwtUtils {


    //滑块验证使用的token生成方法
    public static String generyToken(String uuid, String secret, String keyName){

        return JWT.create()
                .withClaim(keyName,uuid)
                .sign(Algorithm.HMAC256(secret));
    }

    public static DecodedJWT decodeJWT(String token, String secret){
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }


}
