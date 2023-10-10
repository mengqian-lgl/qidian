package com.lgl.qidian.filter.security;

import com.alibaba.fastjson.JSON;
import com.lgl.qidian.algorithm.de_en_code.TokenDeEncodeFactory;
import com.lgl.qidian.algorithm.de_en_code.TokenFilterDeEncodeToken;
import com.lgl.qidian.entity.web_security.MyUserDetail;
import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.entity.web_state.PleaseReloadFactoryFast;
import com.lgl.qidian.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @auther 刘广林
 */

//自定义的token过滤器
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private final static String TOKEN_NAME = "JSESSION";

    //默认为不做处理的加密解密算法实现
    protected static TokenFilterDeEncodeToken de_en_code
            = TokenDeEncodeFactory.createTokenDeEncode(TokenDeEncodeFactory.DeEncodeStrategy.NULL);

    //启用自己的实现
    public static void setDe_en_code(TokenFilterDeEncodeToken de_en_code) {
        TokenFilter.de_en_code = de_en_code;
    }

    public static void setDe_en_code(TokenDeEncodeFactory.DeEncodeStrategy strategy){
        TokenDeEncodeFactory.createTokenDeEncode(strategy);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //尝试获取token
        Cookie[] cookies = request.getCookies();
        String token = "";

        try{
            //判断cookie否为空
            //不为空，获取用户登录凭证
            Assert.isNull(cookies,"没有携带cookie");
        }catch (Exception e){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN_NAME)){
                    token = cookie.getValue();
                    break;
                }
            }
        }


        //为空则跳过进入下一个过滤链
        //token不为空，尝试解密，并使用redis查询
        try {
            //false 不为空，进入处理
            //true  为空   进入下一个过滤器
            Assert.isTrue(token.isEmpty());
        }catch (Exception e){
            //拿到解密后的JSESSIOIN
            token = decodeToken(token);
            String userJson = stringRedisTemplate.opsForValue().get(token);
            //为空时表示token过期，结束请求,通知前端跳转到登录界面
            try{
                Assert.notNull(userJson,"token过期");
            }catch (Exception token_expire){
                ResponseUtils.message(
                        new PleaseReloadFactoryFast().createWebstate(),response);
                return;
            }

            //获取的userjson不为空
            User user = JSON.parseObject(userJson, User.class);
            MyUserDetail myUserDetail = new MyUserDetail();
            myUserDetail.setUser(user);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    myUserDetail, user.getUserPassword(), user.grantedAuthorities
            ));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
        }
        filterChain.doFilter(request,response);


    }

    /**
     todo 简单的解密算法暂时还没有想到之后来做
     */
    private String decodeToken(String token){
        String decodeToken = de_en_code.decode(token);
        return decodeToken;
    }

    /**
     todo 简单的解密算法暂时还没有想到之后来做
     */
    private String encodeToken(String token){
        String encodeToken = de_en_code.encode(token);
        return encodeToken;
    }

}
