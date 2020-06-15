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
import com.google.sps.data.Constants;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles retrieving and storing comments made by users.
 */
@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {
  private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  /**
   * Retrieves all comments and returns it as an array of POJOs, sorted by latest comment first.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(Constants.KIND_COMMENT)
            .addSort(Constants.PROPERTY_TIMESTAMP, Query.SortDirection.DESCENDING);
    PreparedQuery preparedQuery = datastore.prepare(query);

    List<Comment> comments = StreamSupport
            .stream(preparedQuery.asIterable().spliterator(), false)
            .map(entity -> {
              String name = (String) entity.getProperty(Constants.PROPERTY_NAME);
              String content = (String) entity.getProperty(Constants.PROPERTY_CONTENT);
              long timestamp = (long) entity.getProperty(Constants.PROPERTY_TIMESTAMP);
              return new Comment(name, content, timestamp);
            })
            .collect(Collectors.toList());

    String json = new Gson().toJson(comments);
    response.setContentType(Constants.TYPE_JSON);
    response.getWriter().println(json);
  }

  /**
   * Adds a 'Comment' entity kind to the datastore together with timestamp, provided the comment is non-empty.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String content = request.getParameter(Constants.FORM_COMMENT);
    // don't add empty (useless) comments
    if (content.isEmpty()) {
      response.sendRedirect(Constants.LINK_FEEDBACK);
      return;
    }

    String name = System.getProperty("user.name");
    long timestamp = System.currentTimeMillis();

    Entity commentEntity = new Entity(Constants.KIND_COMMENT);
    commentEntity.setProperty(Constants.PROPERTY_NAME, name);
    commentEntity.setProperty(Constants.PROPERTY_CONTENT, content);
    commentEntity.setProperty(Constants.PROPERTY_TIMESTAMP, timestamp);

    datastore.put(commentEntity);
    response.sendRedirect(Constants.LINK_FEEDBACK);
  }
}
