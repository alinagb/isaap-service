package isapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .ignoringAntMatchers("/registration/users")
                .ignoringAntMatchers("/universities")
                .ignoringAntMatchers("/faculties")
                .ignoringAntMatchers("/posts")
                .and().cors().and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/registration/users").permitAll()
                .antMatchers(HttpMethod.GET, "/registration/users").permitAll()
                .antMatchers(HttpMethod.GET, "/universities").permitAll()
                .antMatchers(HttpMethod.GET, "/faculties").permitAll()
                .antMatchers(HttpMethod.GET, "/registration/profile/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/registration/post/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/image/{userId}/{fileId}").permitAll()
                .antMatchers(HttpMethod.GET, "/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/{postId}").permitAll()

                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
