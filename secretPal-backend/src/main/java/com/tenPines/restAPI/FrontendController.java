package com.tenPines.restAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResourceLoader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


/**
 * Este controller devuelve el contenido del index de la aplicacion ember en cualquier ruta que le pidan
 * de manera que el usuario autenticado reciba la single-page app de Ember, y sea esa app la que se encargue
 * de manejar la vista de acuerdo a la ruta.
 * <p>
 * <p>
 * Created by tenpines on 31/03/16.
 */
@Controller
public class FrontendController implements ServletContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(FrontendController.class);

    /**
     * Path dentro del host donde servimos contenido web. Dentro de esta url
     * vamos a servir la aplicacion ember
     */
    private static final String WEB_CONTENT_ROOT = "/web";
    /**
     * Ubicacion dentro de src/webapp donde se hostea la aplicacion ember
     * que hace de frontend
     */
    private static final String INTERNAL_FRONTEND_APP_DIR = "WEB-INF/frontend/";
    /**
     * Ubicacion del index, dentro de la app ember
     */
    private static final String EMBER_APP_INDEX_HTML = INTERNAL_FRONTEND_APP_DIR + "index.html";

    private ServletContext servletContext;

    @RequestMapping({WEB_CONTENT_ROOT+"/**/*", WEB_CONTENT_ROOT + "/*"})
    @ResponseBody
    public FileSystemResource devolverArchivoIndexDelFrontendEmber(HttpServletRequest request) throws IOException {
        LOG.info("Llego el request [" + request.getMethod() + "]:" + request.getRequestURI());

        Resource recursoOriginal = getResourceFromURI(request);
        Resource recursoADevolver;
        if( this.siNoEsUnArchivo(recursoOriginal) ) {
            recursoADevolver = this.devolverElIndex(recursoOriginal);
        } else {
            recursoADevolver = recursoOriginal;
        }

        File myFile = recursoADevolver.getFile();
        return new FileSystemResource(myFile);
    }

    private ResourceLoader loader;
    private ResourceLoader getLoader(){
        loader = Optional.ofNullable(loader).orElseGet(() ->
                new ServletContextResourceLoader(servletContext)
        );
        return loader;
    }

    private Resource devolverElIndex(Resource resource) {
        return getLoader().getResource(defaultResource());
    }

    private boolean siNoEsUnArchivo(Resource resource){
        try {
            return !resource.exists() || resource.getFile().isDirectory();
        } catch (IOException e) {
            throw new RuntimeException("El recurso " + resource + " existe, pero no está en el file system");
        }
    }

    private String defaultResource() {
        return EMBER_APP_INDEX_HTML;
    }

    private Resource getResourceFromURI(HttpServletRequest request) {
        String nombreDeArchivo = getWebappFileName(request);
        return getLoader().getResource(INTERNAL_FRONTEND_APP_DIR + nombreDeArchivo);
    }

    private String getContextRoot() {
        return WEB_CONTENT_ROOT + "/";
    }

    private String getWebappFileName(HttpServletRequest request) {
        return request.getRequestURI().substring(getContextRoot().length());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}