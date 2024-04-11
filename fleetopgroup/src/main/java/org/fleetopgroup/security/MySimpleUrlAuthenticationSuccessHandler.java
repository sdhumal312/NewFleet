package org.fleetopgroup.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("myAuthenticationSuccessHandler")
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
/*	@Autowired
	ICacheService			cacheService;*/

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Autowired IUserProfileService		userProfileService;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		try {
			handle(request, response, authentication);
		} catch (Exception e) {
			e.printStackTrace();
		}
		final HttpSession session = request.getSession(false);
		if (session != null) {
			session.setMaxInactiveInterval(30 * 60);
		}
		clearAuthenticationAttributes(request);
	}

	protected void handle(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws Exception {
		final String targetUrl = determineTargetUrl(authentication);
		//CustomUserDetails		userDetails			= null;
		if (response.isCommitted()) {
			logger.error("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		
		userProfileService.updateLoginDetails();
		redirectStrategy.sendRedirect(request, response, targetUrl);
		//cacheService.cacheCompanyData();
		//cacheService.cacheUserProfile(userDetails.getId());
	}

	protected String determineTargetUrl(final Authentication authentication) {
		boolean isUser = false;
		boolean isAdmin = false;
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("READ_PRIVILEGE")) {
				isUser = true;
			} else if (grantedAuthority.getAuthority().equals("WRITE_PRIVILEGE")) {
				isAdmin = true;
				isUser = false;
				break;
			} else {
				isUser = true;
				break;
			}
		}
		if (isUser) {
			/*
			 * return "/homepage.html?user=" + authentication.getName(); return
			 * "/open.html?email=" + authentication.getName();
			 */
			return "/open.html";
		} else if (isAdmin) {
			/* return "/console.html"; */
			return "/console.html";
		} else {
			return "/open.html";
		}
	}

	protected void clearAuthenticationAttributes(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}