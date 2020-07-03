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

public class JwtManageTool {

    /**
     * 客户端登录Token的生成和解析
     */

    /* token秘钥

     */
    public static final String SECRET = "MIICXQIBAAKBgQCLqlhJNzntmrKrRXsiCf9bD4IBQDUyOUISZ5DtSQLBU247XeU8yXYEiUJ0ly2HFuLgur8I5t2i3jFwTqNRYJs6dd1v86iaQShqbU3wiGxVS42Wn0pVdgKzgYhFjl" +
            "RoRYsai1kDCVuRHfB4tiYLVMu40tl4oXMwgeU191ZnUvaJBI1YJHVunkbwIDAQABqLl6ECQQDXAOTSaBM4ROOV";
    /*token 过期时间: 7200秒 */
    public static final int calendarField = Calendar.SECOND;
    public static final int calendarInterval = 43200;

    /**
     *
     * @param sysUserId
     * @param sysUserName
     * @param syeUserPicture
     * @return
     * @throws Exception
     */
    public static String createToken(Long sysUserId,String sysUserName,String syeUserPicture) throws Exception {
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
                .withClaim("sysUserId", null == sysUserId ? null : sysUserId.toString())
                .withClaim("sysUserName",StringUtils.isNotBlank(sysUserName)?sysUserName:null)
                .withClaim("syeUserPicture",StringUtils.isNotBlank(syeUserPicture)?syeUserPicture:null)
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
    public static Map<String,Object> getSysUserInfo(String token) {
        if (StringUtils.isBlank(token))
        {
            return null;
        }
        Map<String, Claim> claims = verifyToken(token);
        if (claims == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Claim sysUserId_claim = claims.get("sysUserId");
        if (null != sysUserId_claim && StringUtils.isNotBlank(sysUserId_claim.asString())) {

            map.put("sysUserId", sysUserId_claim.asString());

        }
        Claim sysUserName_claim = claims.get("sysUserName");
        if (null != sysUserName_claim && StringUtils.isNotBlank(sysUserName_claim.asString())) {

            map.put("sysUserName", sysUserName_claim.asString());

        }
        Claim syeUserPicture_claim = claims.get("syeUserPicture");
        if (null != syeUserPicture_claim && StringUtils.isNotBlank(syeUserPicture_claim.asString())) {

            map.put("syeUserPicture", syeUserPicture_claim.asString());

        }
        if (map.size() < 3) {
            return null;
        }
        return map;
    }

    /**
     *
     * @param request
     * @return
     */
    public static Map<String,Object> getSysUserInfoByAuthorization(HttpServletRequest request) {
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
                Map<String, Object> map = new HashMap<>();
                Claim sysUserId_claim = claims.get("sysUserId");
                if (null != sysUserId_claim && StringUtils.isNotBlank(sysUserId_claim.asString())) {

                    map.put("sysUserId", sysUserId_claim.asString());

                }
                Claim sysUserName_claim = claims.get("sysUserName");
                if (null != sysUserName_claim && StringUtils.isNotBlank(sysUserName_claim.asString())) {

                    map.put("sysUserName", sysUserName_claim.asString());

                }
                Claim syeUserPicture_claim = claims.get("syeUserPicture");
                if (null != syeUserPicture_claim && StringUtils.isNotBlank(syeUserPicture_claim.asString())) {

                    map.put("syeUserPicture", syeUserPicture_claim.asString());

                }
                if (map.size() < 3) {
                    return null;
                }
                return map;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
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
                Claim user_id_claim = claims.get("sysUserId");
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
