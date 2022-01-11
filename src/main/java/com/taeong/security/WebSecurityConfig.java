package com.taeong.security;

import com.taeong.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity  //Spring Security를 활성화
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {   //Spring Security의 설정파일

    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {    // 인증을 무시할 경로들을 설정(무조건 접근이 가능)
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //http 관련 인증 설정
        http.authorizeRequests()
                .antMatchers("/login","/signup","/user").permitAll()    //누구나 접근이 가능
                .antMatchers("/").hasRole("USER")   //USER 권한이 있는 사람만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") //ADMIN 권한이 있는 사람만 접근 가능
                .anyRequest().authenticated()   //authenticated : 권한이 있으면 무조건 접근 가능 / anyRequest : 설정하지 않은 나머지 경로
            .and()
                .formLogin()
                    .loginPage("/login")        //로그인 페이지 링크 설정
                    .defaultSuccessUrl("/")     //로그인 성공 후 리다이렉트할 주소
            .and()
                .logout()
                    .logoutSuccessUrl("/login")     //로그아웃 성공 후 리다이렉트할 주소
                    .invalidateHttpSession(true);   //로그아웃 이후 세션 전체 삭제 여부
    }

    @Override   //BCrypt타입의 password를 다시 인코딩해주는 역할
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
                // loadUserByUsername() 구현해야함 (서비스 참고)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
