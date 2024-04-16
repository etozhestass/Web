package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicServlet extends HttpServlet {

    private List<Map<String, String>> usersTexts = new ArrayList<>();
    private String currentUser = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/message/auth")) {
            String user = request.getParameter("user");
            writeResponse(response, user);
            currentUser = user;
        } else if (uri.endsWith("/message/findAll")) {
            List<Map<String, String>> post_format = new ArrayList<>();
            for (Map<String, String> pair : usersTexts) {
                for (Map.Entry<String, String> entry : pair.entrySet()) {
                    Map<String, String> message = new HashMap<>();
                    message.put("user", entry.getKey());
                    message.put("text", entry.getValue());
                    post_format.add(message);
                }
            }
            writeResponse(response, post_format);
        } else if (uri.endsWith("/message/add")) {
            String text = request.getParameter("text");
           usersTexts.add(new HashMap<String, String>(){{
               put(currentUser, text);
           }});
        }
    }

    private void writeResponse(HttpServletResponse response, Object objectToPost) throws IOException {
        response.setContentType("application/json");
        String json = new Gson().toJson(objectToPost);
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
