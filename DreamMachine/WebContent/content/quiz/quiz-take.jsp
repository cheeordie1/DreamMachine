<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

  <%@ page import="assignment.Quiz"%>

  <% 
  
  int qid = Integer.parseInt(request.getParameter("qid"));
  List<Quiz> quiz = Quiz.searchByID(qid);
  boolean singlePage = quiz.single_page;
  %>

  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/take-quiz.css">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <title><%=quiz.name%></title>
</head>
<body>
  <% if (singlePage) { %>
    <jsp:include page="single-page-quiz.jsp" />
  <% } else { %>
    <jsp:include page="multi-page-quiz.jsp" />
  <% } %>
</body>
</html>