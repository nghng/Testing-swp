/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.commonController;

import dal.AccountDBContext;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import model.Account;
import model.User;
import util.MiscUtil;

/**
 *
 * @author Zuys
 */
public class LoginController extends HttpServlet {

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
        MiscUtil check = new MiscUtil();
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        String login_status = "Wrong email or password, please try again!";
        AccountDBContext db = new AccountDBContext();
        UserDBContext dbUser = new UserDBContext();

        Account account = db.getAccount(username);
        if (account == null || !check.checkEncryptString(password, account.getPassword())) {
            request.getSession().setAttribute("login_status", login_status);
            response.sendRedirect("home");
        } else {
            User user = dbUser.getUser(account);
            request.getSession().setAttribute("account", account);
            request.getSession().setAttribute("user", user);
            if (user.getAccount().getRole().getRoleID() == 5) {
                response.sendRedirect("home");
            } else {
                response.sendRedirect("dashboard");
            }
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
