package org.eclipse.jakarta.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.Test;

class EncodingFilterTest {

    @Test
    void setsRequestEncodingBeforeContinuingFilterChain() throws Exception {
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        new EncodingFilter().doFilter(request, response, chain);

        verify(request).setCharacterEncoding("UTF-8");
        verify(chain).doFilter(request, response);
    }
}
