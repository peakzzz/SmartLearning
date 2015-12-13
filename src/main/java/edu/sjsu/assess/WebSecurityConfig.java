package edu.sjsu.assess;



        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.sjsu.assess.exception.UserException;
import edu.sjsu.assess.model.UserSearchParams;
import edu.sjsu.assess.service.UserServiceImpl;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.formLogin()
                .loginPage("/user/loginPage")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .loginProcessingUrl("/user/loginSubmit")
                .failureUrl("/user/loginPage?error=true")
                .defaultSuccessUrl("/user/success")
                .permitAll()
                    .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                        .and()
                        .authorizeRequests()
                        .antMatchers("/", "/index", "/resources/**", "/questionnaire/**", "/user/register" ).permitAll(); // all these urls don't need authentication
                    //.antMatchers("/questionnaire/**").authenticated(); //add new urls here that need authentication
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity.ignoring().antMatchers( "/resources/**");
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserSearchParams un = new UserSearchParams(username);
                edu.sjsu.assess.model.User user = null;
                try {
                    user = userService.getUser(un);
                } catch (UserException e) {
                    e.printStackTrace();
                }
                if(user != null) {

                    String role = "admin".equals(user.getRole())? "ROLE_ADMIN" : "ROLE_USER";
                    return new User(user.getLogin(), user.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList(role));
                } else {
                    throw new UsernameNotFoundException("could not find the user '"
                            + username + "'");
                }
            }

        };
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
