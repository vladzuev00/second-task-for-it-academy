package by.itacademy.zuevvlad.cardpaymentproject.servlet.filter;

import javax.servlet.*;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.ServletProperty.CONTENT_TYPE_OF_RESPONSE;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.ServletProperty.CHARACTER_ENCODING_OF_RESPONSE;

public final class ResponseConfigurationFilter implements Filter
{
    public ResponseConfigurationFilter()
    {
        super();
    }

    @Override
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                               final FilterChain filterChain)
            throws ServletException, IOException
    {
        servletResponse.setContentType(CONTENT_TYPE_OF_RESPONSE.getValue());
        servletResponse.setCharacterEncoding(CHARACTER_ENCODING_OF_RESPONSE.getValue());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
