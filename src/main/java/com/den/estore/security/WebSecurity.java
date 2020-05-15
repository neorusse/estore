package com.den.estore.security;

import com.den.estore.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final UserService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      // Remove csrf and disable session creation
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      // Add JWT Filters (1. Authentication, 2. Authorization)
      .addFilter(getAuthenticationFilter())
      .addFilter(new JWTAuthorizationFilter(authenticationManager()))
      .authorizeRequests()
      // Configure access rules
      .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
      .anyRequest().authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  /* By default, spring framework uses /login url as route for user authentication
   * here, we are customizing it to /api/users/login url
   */
  public JWTAuthenticationFilter getAuthenticationFilter() throws Exception {

    final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/api/user/login");

    return filter;
  }

}
