var formID = $("#form-id").val ();
var textareaID = $("#textarea-id").val ();
var inputID = $("#input-id").val ();
function createTextAreaForm (form, textarea, input) {
  $("#" + form).on ("submit", function() {
    $("#" + input).val ($("#" + textarea).val ());
  });
};

createTextAreaForm (formID, textareaID, inputID);
$("#form-id").remove ();
$("#textarea-id").remove ();