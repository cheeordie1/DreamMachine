<%
  String dataName = request.getParameter("data-name").toString();
  String formID = request.getParameter("form-id").toString();
  String inputID = formID + "-input";
  String textareaID = request.getParameter("textarea-id").toString();
  String taClass = "'" + request.getParameter("textarea-class").toString();
  String hasErrors = request.getParameter("errors").toString();
  if (hasErrors.equals("true")) 
    taClass += " error'";
  else
	taClass += "'";
%>
<input type="hidden" id="form-id" value=<%= formID %> />
<input type="hidden" id="textarea-id" value=<%= textareaID %> />
<input type="hidden" id="input-id" value=<%= inputID %> />
<input type="hidden" id=<%= inputID %> name=<%= dataName %> />
<textarea id=<%= textareaID %> class=<%= taClass %>></textarea>
<script src="/DreamMachine/assets/javascripts/form-textarea.js"></script>