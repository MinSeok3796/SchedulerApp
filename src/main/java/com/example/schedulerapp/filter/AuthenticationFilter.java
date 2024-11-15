package com.example.schedulerapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.schedulerapp.entity.User;
import com.example.schedulerapp.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();
        String requestURI = httpRequest.getRequestURI();
        String email = httpRequest.getParameter("email");
        String password = httpRequest.getParameter("password");

        if (!requestURI.startsWith("/login")
                && !(requestURI.startsWith("/users") && "POST".equals(method))
                && !isAuthenticated(email, password)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("email") == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 먼저 하세요");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isAuthenticated(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent() && user.get().getPassword().equals(password);
    }
}