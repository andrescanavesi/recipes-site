package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import com.canavesi.recipes.site.dao.DaoRecipes;
import com.canavesi.recipes.site.entities.RecipeEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andres Canavesi
 */
@WebServlet(name = "SiteMapServlet", urlPatterns = {"/sitemap.xml"})
public class SiteMapServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/xml;charset=UTF-8");

        List<RecipeEntity> recipes = DaoRecipes.getInstance().find(0, 1000);
        String baseUrl = DaoConfigs.getBaseUrl();
        final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        /**
         * Keywords to generate url to search
         */
        String[] keywords = new String[]{"faina", "pizza", "tarta", "torta", "medialunas", "pasta%20frola", "vainillas", "sin%20gluten", "sin%20tacc",
            "pan", "semillas", "arrolladitor", "manzana", "maiz", "harina", "mermelada", "calabaza", "naranja", "avena", "galletitas", "celiacos",
            "panqueques", "queso", "torta%20de%20fiambre", "ajo", "oregano", "chocolate", "cookies", "churros", "budin", "marmolado",
            "pascualina", "masa", "chips", "pancitos", "hojaldre", "vainilla", "cebolla", "parmesano"};
        /**
         * Prints xml according to:
         *
         * https://support.google.com/webmasters/answer/183668?hl=es
         *
         */
        try (PrintWriter out = response.getWriter()) {
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" \n"
                    + "  xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\">");

            out.println("<url> ");
            out.println("<loc>" + baseUrl + "</loc> ");
            out.println("<lastmod>" + DATE_FORMAT.format(new Date()) + "</lastmod> ");
            out.println("<changefreq>daily</changefreq> ");
            out.println("<priority>1.0</priority> ");
            out.println("<image:image>");
            out.println("<image:loc>https://res.cloudinary.com/dniiru5xy/image/upload/c_fill,g_auto/w_600,q_auto,f_auto/recipe-default.png</image:loc>");
            out.println("<image:caption>Recetas City. Las mejores recetas de cocina</image:caption>");
            out.println("</image:image>");
            out.println("</url> ");

            List<String> added = new ArrayList<>();
            for (String keyword : keywords) {
                if (!added.contains(keyword)) {
                    out.println("<url> ");
                    out.println("<loc>" + baseUrl + "buscar/" + keyword + "</loc> ");
                    out.println("<lastmod>" + DATE_FORMAT.format(new Date()) + "</lastmod> ");
                    out.println("<changefreq>weekly</changefreq> ");
                    out.println("<priority>0.8</priority> ");
                    out.println("<image:image>");
                    out.println("<image:loc>https://res.cloudinary.com/dniiru5xy/image/upload/c_fill,g_auto/w_600,q_auto,f_auto/background-table-food.jpg</image:loc>");
                    out.println("<image:caption>Recetas sobre " + keyword + "</image:caption>");
                    out.println("</image:image>");
                    out.println("</url> ");
                }

            }

            for (RecipeEntity recipe : recipes) {
                out.println("<url> ");
                out.println("<loc>" + baseUrl + "receta/" + recipe.getId() + "/" + recipe.getTitleForUrl() + "</loc> ");
                out.println("<lastmod>" + DATE_FORMAT.format(recipe.getUpdatedAt()) + "</lastmod> ");
                out.println("<changefreq>monthly</changefreq> ");
                out.println("<priority>0.5</priority> ");
                out.println("<image:image>");
                out.println("<image:loc>" + recipe.getFeaturedFullImageUrl() + "</image:loc>");
                out.println("<image:caption>" + recipe.getTitle() + "</image:caption>");
                out.println("</image:image>");
                out.println("</url> ");
            }

            out.println("</urlset>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
