package com.lgl.qidian.algorithm.de_en_code;

/**
 * @auther 刘广林
 */
public class NullTokenDeEncode implements TokenFilterDeEncodeToken {
    @Override
    public String encode(String token) {
        return token;
    }

    @Override
    public String decode(String token) {
        return token;
    }
}
