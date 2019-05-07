package org.capvalue.recrute.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfigue {
@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Autowired
		public void globalConfig(AuthenticationManagerBuilder  auth,DataSource dataSource) throws Exception{
			auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username as principal, password as credentials, true from users where	username = ? and actived=1")
				.authoritiesByUsernameQuery("select users as principal, roles as role from users_roles "
						+ "where users = ?")
				.rolePrefix("ROLE_")
				.passwordEncoder(passwordencoder());
			}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic()
					.and()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).accessDeniedPage("/accessDenied")
					.and()
				.authorizeRequests()
				   
					.antMatchers(HttpMethod.POST,"/candidats/candidats").permitAll()
					.antMatchers(HttpMethod.PUT,"/candidats/candidats").permitAll()
					
					.antMatchers(HttpMethod.POST,"/postulers/postulers").permitAll()
				
					//.antMatchers(HttpMethod.GET,"/candidat/consulter/{id}/").permitAll()
					.antMatchers(HttpMethod.GET,"/offre/offres").permitAll()
					.antMatchers(HttpMethod.PUT,"/offre/offres").hasRole("ADMIN")
					
					//.antMatchers(HttpMethod.GET,"/user").hasRole("ADMIN")
					//.antMatchers(HttpMethod.GET,"/user").hasRole("CANDIDAT")
					
				
					.antMatchers(HttpMethod.GET,"/candidats/activateAccount/{username}/{activationKey}/").permitAll()
					.antMatchers(HttpMethod.GET,"/offre/offres/{id}").permitAll()
					.antMatchers(HttpMethod.GET,"/nosInfos").permitAll()
					.antMatchers(HttpMethod.PUT,"/nosInfos").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT,"/nosInfos/{codeNosInfos}").hasRole("ADMIN")
					///nosInfos/{codeNosInfos}
					.antMatchers(HttpMethod.DELETE,"/nosInfos/{codeNosInfos}").hasRole("ADMIN")
					///nosInfos/{codeNosInfos}
					.antMatchers(HttpMethod.GET,"/nosMetier").permitAll()
					.antMatchers(HttpMethod.PUT,"/nosMetier").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT,"/nosMetier/{id}").hasRole("ADMIN")
					.antMatchers(HttpMethod.DELETE,"/nosMetier/{codeNosMetier}").hasRole("ADMIN")
					
					.antMatchers(HttpMethod.DELETE,"/references/{codeRef}").hasRole("ADMIN")
					
					
					.antMatchers(HttpMethod.GET,"/ordered").permitAll()
					.antMatchers(HttpMethod.GET,"/orderedMetier").permitAll()
					
					.antMatchers(HttpMethod.GET,"/offresSearch/**").permitAll()
					.antMatchers(HttpMethod.GET,"/offre/offresByCompetence/{id}").permitAll()
					.antMatchers(HttpMethod.GET,"/offre/offresByVille/{id}").permitAll()
					.antMatchers(HttpMethod.GET,"/offre/offresByContract/{id}").permitAll()
					.antMatchers(HttpMethod.GET,"/contacter/{username}/{subject}/{message}").permitAll()
					
				
					.antMatchers(HttpMethod.GET,"/users/forgotPassword").permitAll()
					.antMatchers(HttpMethod.GET,"/users/recoverAccount").permitAll()
					.antMatchers(HttpMethod.PUT,"/users/recoverPassword/{email}/{verificationKey}").permitAll()
					
					.antMatchers(HttpMethod.GET,"/users/allUsers").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT,"/users/allUsers/{username}/{etat}").hasRole("ADMIN")
					.antMatchers(HttpMethod.DELETE,"/users/allUsers/{username}/target").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT,"/users/updateAdmin").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST,"/users/addAdmin").hasRole("ADMIN")
					
					.antMatchers(HttpMethod.GET,"/offre/offres/ville").permitAll()
					.antMatchers(HttpMethod.GET,"/offre/offres/contrat").permitAll()
					.antMatchers(HttpMethod.GET,"/offre-competence-not").permitAll()
					.antMatchers(HttpMethod.GET,"/users/currentUser").permitAll()
					.antMatchers(HttpMethod.PUT,"/currentUser/{username}/changed").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT,"/currentUser/{username}/changed").hasRole("CANDIDAT")
					.antMatchers(HttpMethod.GET,"/offre/offres/incrementNbVueOffre/{codeOffre}").permitAll()
					.antMatchers(HttpMethod.GET,"/subscribes/desinscrire/{username}/").permitAll()
					.antMatchers(HttpMethod.POST,"/subscribes/subscribes").permitAll()
					.antMatchers(HttpMethod.GET,"/candidats/users/desinscrire/{username}/").permitAll()
					
					
					.antMatchers(HttpMethod.GET,"/villes/**").permitAll()
					.antMatchers(HttpMethod.GET,"/contrats").permitAll()
					.antMatchers(HttpMethod.GET,"/references").permitAll()
					.antMatchers(HttpMethod.GET,"/competences").permitAll()
					.antMatchers(HttpMethod.GET,"/competencelst").permitAll()
					.antMatchers(HttpMethod.POST,"/subscribes/subscribes").permitAll()
					.antMatchers(HttpMethod.GET,"/subscribes/activedSubscribe").permitAll()
					.antMatchers(HttpMethod.GET,"/subscribes/desactivedSubscribe").permitAll()
					.antMatchers("/app/index.html#/app/admin/jobs/list").hasRole("ADMIN")
					.antMatchers("/app/index.html#/app/admin/reference").hasRole("ADMIN")
					.antMatchers("/app/index.html#/app/admin/users").hasRole("ADMIN")
				    //	/app/index.html#/app/admin/users
					.antMatchers("/",
							"/app/**",
							"/bower_components/**",
							"/node_modules/**"
							).permitAll()
					.anyRequest().authenticated()
					.and()
				    .formLogin()
					.loginPage("/login")
					.permitAll();					
/*
 *              .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter("email")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();*/					
					
				//	.csrfTokenRepository(csrfTokenRepository())
				//.and()
				//.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
					//;
		}

		private Filter csrfHeaderFilter() {
			return new OncePerRequestFilter() {
				@Override
				protected void doFilterInternal(HttpServletRequest request,
						HttpServletResponse response, FilterChain filterChain)
						throws ServletException, IOException {
					CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
							.getName());
					if (csrf != null) {
						Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
						String token = csrf.getToken();
						if (cookie == null || token != null
								&& !token.equals(cookie.getValue())) {
							cookie = new Cookie("XSRF-TOKEN", token);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
					}
					filterChain.doFilter(request, response);
				}
			};
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
		@Bean(name="passwordEncoder")
	    public PasswordEncoder passwordencoder(){
	     return new BCryptPasswordEncoder(12);
	    }
	}

  }

