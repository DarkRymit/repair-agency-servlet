package com.epam.finalproject.framework.security.web.servlet.filter;

import com.epam.finalproject.framework.context.ApplicationContext;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.framework.security.web.SecurityContextRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class SecurityHolderFilter extends HttpFilter {

    private transient SecurityContextRepository securityContextRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        ApplicationContext applicationContext = (ApplicationContext) filterConfig.getServletContext()
                .getAttribute("applicationContext");
        securityContextRepository = applicationContext.getBean(SecurityContextRepository.class);
    }


    @Override
    public void destroy() {
        SecurityContextHolder.resetContext();
        super.destroy();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain) throws ServletException, IOException {
        SecurityContextHolder.setContext(securityContextRepository.loadContext(req));
        chain.doFilter(req, res);
    }
}
