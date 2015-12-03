<%@ page import="assignment.Quiz, assignment.Question, java.util.List"%>

<%
int qid = Integer.valueOf(request.getParameter("qid"));
Quiz quiz = Quiz.searchByID(qid).get(0);
boolean immediate_correct = quiz.immediate_correct;
List<Question> questions = Question.searchByQuizID(qid, true);
%>

<h1><%=quiz.name%></h1>

<% for (int i = 0; i < questions.size(); i++) { %>
  <h4>Question <%=i%></h4>
  <% 
    Question currQuestion = questions.get(i);
    Question.Type type = currQuestion.question_type;
    int photo_id = currQuestion.photo_id;
  %>
  
  <% if (photo_id != 0) { %>
    <img src= <%=imgSrc%> alt="question pic">
  <% } %>
  
  <p><%=currQuestion.question%></p>
<%}%>