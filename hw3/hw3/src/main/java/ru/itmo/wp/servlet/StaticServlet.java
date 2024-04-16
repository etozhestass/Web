package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;


public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] split_uri = request.getRequestURI().split("\\+");
        boolean set_content_type = false;
        for (String uri : split_uri) {
            File file = new File("C:/Web/hw3/hw3/src/main/webapp/static", uri);
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("/static" + uri));
                if (!file.isFile()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    continue;
                }
            }
            if (!set_content_type) {
                response.setContentType(getServletContext().getMimeType(file.getName()));
                set_content_type = true;
            }
            try (OutputStream outputStream = response.getOutputStream()) {
                Files.copy(file.toPath(), outputStream);
            }
        }
    }
}
