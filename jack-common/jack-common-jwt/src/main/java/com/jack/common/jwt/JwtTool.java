package com.jack.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTool {

    /**
     * 客户端登录Token的生成和解析
     */

    /* token秘钥

     */
    public static final String SECRET = "ofjyhkWlc192wbtF7YRMQGgjjEkB3m3EE4K5nXOQLbZBNDdGgiKo25OnQV36aY3YENIkWAy9k6DE8D8Dakt3hbCOcbQpNs8ZCtLyuq7LJJVCD2IpUH3cjvKOP1aFrUK2";
    /*token 过期时间: 7200秒 */
    public static final int calendarField = Calendar.SECOND;
    public static final int calendarInterval = 172800;

    /**
     * JWT生成Token.<br/>
     * <p>
     * JWT构成: header, payload, signature
     *
     * @param userId userId, 参数userId不可传空
     */
    public static String createToken(Long userId) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("userId", null == userId ? null : userId.toString())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {

            return null;

        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取userId
     *
     * @param token
     * @return user_id
     */
    public static Long getUserId(String token) {
        Map<String, Claim> claims = verifyToken(token);
        if (claims == null) {
            return null;
        }
        Claim user_id_claim = claims.get("userId");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {

            return null;

        }
        return Long.valueOf(user_id_claim.asString());
    }
    /**
     * 根据Authorization获取userId
     *
     * @param request
     * @return user_id
     */
    public static Long getUserIdByAuthorization(HttpServletRequest request) {
        if (request==null)
        {
            return  null;
        }
        String Authorization=request.getHeader("Authorization");
        if (Authorization == null || StringUtils.isEmpty(Authorization)) {
            return null;
        }
        String[] sp = Authorization.split(" ");
        if (sp.length == 2) {
            String token = sp[1];
            if (token != null && StringUtils.isNoneEmpty(token)) {
                Map<String, Claim> claims = verifyToken(token);
                if (claims == null) {
                    return null;
                }
                Claim user_id_claim = claims.get("userId");
                if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {

                    return null;

                }
                return Long.valueOf(user_id_claim.asString());
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

}
