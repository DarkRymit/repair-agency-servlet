package com.epam.finalproject.framework.web;


import com.epam.finalproject.framework.context.ApplicationContext;
import com.epam.finalproject.framework.context.support.ApplicationContextHolder;
import com.epam.finalproject.framework.web.servlet.View;
import com.epam.finalproject.framework.web.servlet.ViewHandlerManager;
import com.epam.finalproject.framework.web.support.RequestArgumentAdapter;
import com.epam.finalproject.framework.web.support.RequestHandlerManager;
import com.epam.finalproject.framework.web.support.StringToViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class FrontController extends HttpServlet {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FrontController.class);
    private transient RequestHandlerManager handlerManager;
    private transient RequestArgumentAdapter argumentAdapter;

    private transient StringToViewResolver stringToViewResolver;

    private transient ViewHandlerManager viewHandlerManager;

    private transient FlashAttributesManager flashAttributesManager;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.trace("Started handling request");
        WebHttpPair webHttpPair = new WebHttpPair(request, response);
        Throwable cause = null;
        RequestHandlerContainer container = null;
        RequestHandler handler = null;
        try {
            Exception subCause = null;
            try {
                container = handlerManager.resolve(request);
                if (container == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                handler = container.getHandler();

                Map<String, Object> flashAttributes = flashAttributesManager.retrieveAndCleanFlashMap(request);
                if (flashAttributes != null) {
                    flashAttributes.forEach(request::setAttribute);
                }

                if (!handlerManager.preHandle(handler, webHttpPair)) {
                    return;
                }

                Object result = processHandler(webHttpPair, container);

                View view = getView(result);

                handlerManager.postHandle(handler, webHttpPair, view);

                viewHandlerManager.handle(view, webHttpPair);

            } catch (Exception e) {
                subCause = e;
                throw e;
            } finally {
                handlerManager.afterCompletion(handler, webHttpPair, subCause);
            }
        } catch (RuntimeException | IOException | ServletException e) {
            cause = e;
            throw e;
        } catch (Exception e) {
            cause = e;
            throw new ServletException(e);
        } finally {
            if (cause != null) {
                log.trace("Exception during execution", cause);
            } else {
                log.trace("Success handle of request");
            }
        }
    }

    private View getView(Object result) {
        View view;
        if (result instanceof View) {
            view = (View) result;
        } else if (result instanceof String) {
            view = stringToViewResolver.resolve((String) result);
        } else {
            throw new IllegalStateException("Cant resolve result " + result);
        }
        return view;
    }

    private Object processHandler(WebHttpPair webHttpPair, RequestHandlerContainer container) throws Exception {
        log.trace("Resolved web handler {}", container);
        List<Object> args = argumentAdapter.adapt(webHttpPair, container);
        log.trace("Adapt web handler args {}", args);
        Object result = handlerManager.invoke(container.getHandler(), args);
        log.debug("Executed web handler");
        return result;
    }

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = ApplicationContextHolder.getContext();
        handlerManager = applicationContext.getBean(RequestHandlerManager.class);
        argumentAdapter = applicationContext.getBean(RequestArgumentAdapter.class);
        flashAttributesManager = applicationContext.getBean(FlashAttributesManager.class);
        stringToViewResolver = applicationContext.getBean(StringToViewResolver.class);
        viewHandlerManager = applicationContext.getBean(ViewHandlerManager.class);
    }
}