package com.lgl.qidian.algorithm.de_en_code;

/**
 * @auther 刘广林
 */
public class TokenDeEncodeFactory{

    public static enum DeEncodeStrategy{
        NULL;
    }

    // 简单工厂默认提供不对token进行处理的加密解密算法实现
    public static TokenFilterDeEncodeToken createTokenDeEncode(DeEncodeStrategy deEncodeStrategy){
        switch (deEncodeStrategy){
            case NULL:
                return new NullTokenDeEncode();

        }
        return new NullTokenDeEncode();
    }
}
