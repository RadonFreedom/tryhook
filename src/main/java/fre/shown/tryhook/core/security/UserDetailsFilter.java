package fre.shown.tryhook.core.security;

import fre.shown.tryhook.common.util.UserUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Shaman
 * @date 2019/12/27 13:35
 */

public class UserDetailsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UserDetailsImpl userDetails = UserUtils.getUserDetails();
        if (userDetails != null) {
            request.setAttribute("userDetails", UserUtils.getUserDetails());
        }
        chain.doFilter(request, response);
    }
}
