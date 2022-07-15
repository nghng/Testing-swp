/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.publicController;

import controller.AuthorizationController;
import dal.BlogDBContext;
import dal.CourseDBContext;
import dal.SliderDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Course;
import model.Post;
import model.Slider;

/**
 *
 * @author Hai Tran
 */
public class HomePageController extends HttpServlet {

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
        SliderDBContext dbSlider = new SliderDBContext();
        BlogDBContext dbBlog = new BlogDBContext();
        CourseDBContext dbCourse = new CourseDBContext();
        Account account = (Account) request.getSession().getAttribute("account");
        ArrayList<Course> courses = dbCourse.getCoursesForHomePage(account);
        ArrayList<Post> posts = dbBlog.getPostForHomePage();
        ArrayList<Slider> sliders = dbSlider.getSliders();
        request.setAttribute("sliders", sliders);
        request.setAttribute("posts", posts);
        request.setAttribute("courses", courses);
        if (account == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (account.getRole().getRoleID() == 5) {
            request.getRequestDispatcher("view/customer/index.jsp").forward(request, response);

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
