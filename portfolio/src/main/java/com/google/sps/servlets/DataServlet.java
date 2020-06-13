// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import com.google.sps.data.Comments;
import com.google.sps.data.Constants;
import com.sun.org.apache.bcel.internal.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {
  private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  // Retrieves all comments and returns it as a JSON string, sorted in descending timestamp { comments: [...] }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(Constants.KIND_COMMENT)
            .addSort(Constants.PROPERTY_TIMESTAMP, Query.SortDirection.DESCENDING);
    PreparedQuery preparedQuery = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();
    for (Entity entity: preparedQuery.asIterable()) {
      String content = (String) entity.getProperty(Constants.PROPERTY_CONTENT);
      long timestamp = (long) entity.getProperty(Constants.PROPERTY_TIMESTAMP);
      comments.add(new Comment(content, timestamp));
    }

    String json = new Gson().toJson(comments);

    response.setContentType(Constants.TYPE_JSON);
    response.getWriter().println(json);
  }

  // adds a comment entity to the datastore together with timestamp, provided the comment is non-empty.
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String content = request.getParameter(Constants.FORM_COMMENT);
    long timestamp = System.currentTimeMillis();

    Entity commentEntity = new Entity(Constants.KIND_COMMENT);
    commentEntity.setProperty(Constants.PROPERTY_CONTENT, content);
    commentEntity.setProperty(Constants.PROPERTY_TIMESTAMP, timestamp);

    // don't add empty (useless) comments
    if (content.isEmpty()) {
      response.sendRedirect(Constants.LINK_HOME);
      return;
    }

    datastore.put(commentEntity);
    response.sendRedirect(Constants.LINK_HOME);
  }
}
