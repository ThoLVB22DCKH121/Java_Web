<!--Hiển thị giao diện thêm tin tức-->
function toggleNewsForm() {
    let formContainer = document.getElementById("newsFormContainer");
    formContainer.style.display = formContainer.style.display === "none" ? "block" : "none";
}
<!--Sửa tin tức-->
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll(".btn-edit").forEach(button => {
        button.addEventListener("click", function() {
            let id = this.getAttribute("data-id");
            let title = this.getAttribute("data-title");
            let content = this.getAttribute("data-content");
            let image = this.getAttribute("data-image");

            document.getElementById("editId").value = id;
            document.getElementById("editTitle").value = title;
            document.getElementById("editContent").value = content;
            document.getElementById("editImage").value = image;

            document.getElementById("editNewsForm").setAttribute("action", "/admin/news/" + id);
        });
    });
});