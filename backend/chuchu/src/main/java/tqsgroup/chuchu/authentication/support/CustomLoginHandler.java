package tqsgroup.chuchu.authentication.support;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomLoginHandler implements AuthenticationSuccessHandler {
	

    @Value("${ionic.port}")
    private String ionicPort;

	private  Map<String, String> roleTargetUrlMap = new HashMap<>();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException {
				handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {
		String ionicURL = new StringBuilder().append("http://localhost:").append(ionicPort).toString();
		String userUrl = ionicURL + "/";
		String adminUrl = ionicURL + "/admin";

		roleTargetUrlMap.put("USER", userUrl);
		roleTargetUrlMap.put("ADMIN", adminUrl);
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			String authorityName = grantedAuthority.getAuthority();
			if (roleTargetUrlMap.containsKey(authorityName)) {
				return roleTargetUrlMap.get(authorityName);
			}
			else
				return userUrl;
		}

		throw new IllegalStateException();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}