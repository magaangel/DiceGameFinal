package com.dicegamefinal.configuration;

import com.dicegamefinal.security.CustomUserDetailService;
import com.dicegamefinal.security.JwtAuthenticationEntryPoint;
import com.dicegamefinal.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration//control y registros de beans
@EnableWebSecurity//crear una clase de seguridad personalizada
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {//permite usar y personalizar los metodos de security de spring

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;//se integra el JWT a la clase de configuracion

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();//permite codificar la contrasena
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{//metodo a sobreescrobir
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//generamos la exception desde la clase que se encarga de manejar los errores
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()//.antMatchers(HttpMethod.GET, "/players/**").permitAll()
                .antMatchers("/players/auth/**").permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //se le pasa el filtro del token y la clase que procesa un formulario de autenticacion
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*SE DEBE AGREGAR ESTE METODO CUANDO SE USA SPRING SECURITY PARA PERMITIR A SWAGGER EL ACCESO
    PARA DOCUMENTAR LA API, con el se ignoran las partes de la url de swagger por lo cual se puede acceder a los
    recursos de la api sin necesidad de autenticar ni usar token
    */
    @Override
    public void configure(WebSecurity web) throws Exception {//para permitir Swagger
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
