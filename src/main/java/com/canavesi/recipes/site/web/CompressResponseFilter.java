package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

@WebFilter(
        filterName = "CompressResponseFilter",
        urlPatterns = {"/*"}
)
public class CompressResponseFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CompressResponseFilter.class.getName());

    private HtmlCompressor compressor;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        LOG.log(Level.INFO, "\n**** compress {0}", request.getRequestURI());

        CharResponseWrapper responseWrapper = new CharResponseWrapper(
                (HttpServletResponse) resp);

        chain.doFilter(req, responseWrapper);

        String servletResponse = responseWrapper.toString();
        if (DaoConfigs.getEnableCompression()) {
            resp.getWriter().write(compressor.compress(servletResponse));
        } else {
            resp.getWriter().write(servletResponse);
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        LOG.info("Init");
        if (DaoConfigs.getEnableCompression()) {
            compressor = new HtmlCompressor();
            compressor.setCompressCss(true);
            compressor.setCompressJavaScript(false);//true throws parsing errors in runtime
        }

    }

    @Override
    public void destroy() {
    }

}
