package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Base64;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Boolean captchaPassed = (Boolean) session.getAttribute("captcha");
        if ("GET".equals(request.getMethod()) && captchaPassed == null && !uri.endsWith(".ico")) {
            String user_number = request.getParameter("answer");
            String previousNumber = (String) session.getAttribute("previous");
            if (user_number != null && user_number.equals(previousNumber)) {
                session.setAttribute("captcha", true);
                chain.doFilter(request, response);
                return;
            }
            Random random = new Random();
            String randNumber = Integer.toString(random.nextInt( 900) + 100);
            session.setAttribute("previous", randNumber);
            try (PrintWriter writer = response.getWriter()) {
                response.setContentType("text/html");
                byte[] imageBytes = ImageUtils.toPng(randNumber);
                writer.println("<html> <body>    <form>\n" +
                        "        <label for=\"answer\">Введите число:</label>\n" +
                        "        <input type=\"text\" id=\"answer\" name=\"answer\">\n" +
                        "        <input type=\"submit\" value=\"Отправить\">\n" +
                        "    </form>");
                writer.println("<img src='data:image/png;base64," +
                        Base64.getEncoder().encodeToString(imageBytes) +
                        "'>");
                writer.println("</body></html>");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
