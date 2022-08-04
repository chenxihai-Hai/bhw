package com.bhw.common.util.jwt;

import com.alibaba.fastjson.JSON;
import com.bhw.common.constant.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;


public class JWTHelper {
    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJWTInfo jwtInfo, String priKey, int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.HS512, priKey)
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static IJWTInfo getInfoFromToken(String token, String pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        Object o = body.get(CommonConstants.JWT_KEY_USER_ID);
        Object o1 = body.get(CommonConstants.JWT_KEY_NAME);
        return new JWTInfo(body.getSubject(), null ==o?"":o.toString(),null ==o1?"":o1.toString() );
    }

}
