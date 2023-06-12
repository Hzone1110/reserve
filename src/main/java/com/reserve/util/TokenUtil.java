package com.reserve.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenUtil {
    private static final String privateKey = "test";

    /**
     * 加密token.
     */
    public static String generateToken(String account) {
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.
        long EXPIRE = 60 * 60 * 1000;
        return JWT
                .create()
                .withClaim("account", account)
                .withClaim("timeStamp", System.currentTimeMillis() + EXPIRE)
                .sign(Algorithm.HMAC256(privateKey));
    }

    /**
     * 解析token.
     */
    public static Map<String, String> parseToken(String token) {
        HashMap<String, String> map = new HashMap<>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(privateKey))
                .build().verify(token);
        Claim account = decodedjwt.getClaim("account");
        Claim timeStamp = decodedjwt.getClaim("timeStamp");
        map.put("account", account.asString());
        map.put("timeStamp", timeStamp.asLong().toString());
        return map;
    }

    /**
     * @return 获取token
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
