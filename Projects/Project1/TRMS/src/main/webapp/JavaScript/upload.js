$(function() {
        $('#fileUpload').ajaxForm({
            success: function(msg) {
                alert("File has been uploaded successfully");
            },
            error: function(msg) {
                alert("Couldn't upload file");
            }
        });
    });

