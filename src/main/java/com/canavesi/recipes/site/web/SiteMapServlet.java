package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import com.canavesi.recipes.site.dao.DaoRecipes3;
import com.canavesi.recipes.site.entities.RecipeEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");

        DaoRecipes3 daoRecipes = new DaoRecipes3();
        List<RecipeEntity> recipes = daoRecipes.findAll(0, 1000);
        String baseUrl = DaoConfigs.getBaseUrl();

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
            out.println("<image:image>");
            out.println("<image:loc>https://res.cloudinary.com/dniiru5xy/image/upload/v1528565414/recipe-default.png</image:loc>");
            out.println("<image:caption>Recetas City. Las mejores recetas de cocina</image:caption>");
            out.println("</image:image>");
            out.println("</url> ");

            for (RecipeEntity recipe : recipes) {
                out.println("<url> ");
                out.println("<loc>" + baseUrl + "/receta/" + recipe.getId() + "/" + recipe.getTitleForUrl() + "</loc> ");
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
        processRequest(request, response);
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
        processRequest(request, response);
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