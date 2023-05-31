package pasarela.zuul.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecuritySuccessHandler successHandler;

    @Autowired
    private JwtRequestFilter authenticationRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and() // Añadir esta línea para habilitar CORS
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/opiniones/**").permitAll()
            .antMatchers("/restaurantes/**").authenticated()
            .and()
            .oauth2Login().successHandler(successHandler)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

