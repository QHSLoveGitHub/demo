package org.qhs.example.config;

import org.qhs.example.dao.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
//@Profile("production")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //注入dao层接口
    @Autowired
    private ReaderRepository readerRepository;

    //设置角色权限和登录界面
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //设置登录权限
        http.authorizeRequests()
                .antMatchers("/")
                .access("hasRole('READER')")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true");
    }

    //设置角色权限和登录界面
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(new UserDetailsService(){
                    @Override
                    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                        return readerRepository.getOne(username);
                    }
                });
    }
}
