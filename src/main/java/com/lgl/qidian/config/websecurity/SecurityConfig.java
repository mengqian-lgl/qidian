package com.lgl.qidian.config.websecurity;


import com.lgl.qidian.algorithm.de_en_code.TokenDeEncodeFactory;
import com.lgl.qidian.entity.web_security.MyUserDetail;
import com.lgl.qidian.filter.security.TokenFilter;
import com.lgl.qidian.service.security_user_service.MyUserDetailService;
import com.lgl.qidian.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import java.util.Calendar;
import java.util.Date;

/**
 * @auther 刘广林
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    TokenFilter tokenFilter;

    @Autowired
    MyUserDetailService myUserDetailService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //关闭csrf攻击防御
        http.csrf().disable();
        //允许跨域
        http.cors().disable();

        http.authorizeRequests()
                .antMatchers("/register", "/login","/sliderverify","/123/{name}").permitAll()
                .anyRequest().authenticated();

        //不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //使用自定义的token过滤器代替session
        http.addFilterAfter(tokenFilter, SecurityContextPersistenceFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(bCryptPasswordEncoder());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Bean("MyProviderManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return new AuthenticationManager() {
          @Override
          public Authentication authenticate(Authentication authentication) throws AuthenticationException {
              String userId = String.valueOf(authentication.getPrincipal());
              String rowUserPassword = (String)authentication.getCredentials();
              MyUserDetail userDetails = (MyUserDetail)myUserDetailService.loadUserByUsername(userId);
              if (userDetails == null){
                  System.out.println("用户登入失败，用户名不存在：时间" + DateUtils.getTime(new Date()));
                  return null;
              }

              if (!bCryptPasswordEncoder().matches(rowUserPassword,userDetails.getPassword())){
                  System.out.println("用户"+ userId + "  密码输入错误: 时间 " + DateUtils.getTime(new Date()) );
              }

              return new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
          }
      };
      }
}

