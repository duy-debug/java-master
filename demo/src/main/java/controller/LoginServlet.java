package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
          throws ServletException, IOException {

    request.getRequestDispatcher("login.jsp")
            .forward(request, response);
  }
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if("admin".equals(username) && "admin".equals(password)){
      // cookie
      Cookie c = new Cookie("username", username);
      c.setMaxAge(60); // thiết lập thời của cookie là 60s
      response.addCookie(c);

      //session
      HttpSession session = request.getSession();
      session.setAttribute("username", username);

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Login Servlet</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Welcome " + username + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
    else{
      PrintWriter out = response.getWriter();
      out.println("<h3>Login failed</h3>");
    }
  }
}
