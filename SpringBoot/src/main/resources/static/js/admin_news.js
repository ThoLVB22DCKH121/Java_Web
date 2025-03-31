function toggleNewsForm() {
    let form = document.getElementById("newsFormContainer");
    form.style.display = (form.style.display === "none") ? "block" : "none";
}


document.addEventListener("DOMContentLoaded", function () {
    // Xử lý modal mục
    const newsDetailModal = document.getElementById('newsDetailModal');
    if (newsDetailModal) {
        newsDetailModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const idNews = button.getAttribute('data-id-news');
            const form = document.getElementById('newsDetailForm');
            const inputId = document.getElementById('idNews');

            if (form && inputId) {
                inputId.value = idNews;
                form.action = '/admin/news/' + idNews;
            }
        });
    }

    // Xử lý sửa tin tức
    document.querySelectorAll(".btn-editNews").forEach(button => {
        button.addEventListener("click", function () {
            let id = this.getAttribute("data-id");
            let title = this.getAttribute("data-title");

            document.getElementById("editId").value = id;
            document.getElementById("editTitle").value = title;

            document.getElementById("editNewsForm").setAttribute("action", "/admin/news/" + id);
        });
    });

    // Xử lý hiển thị từng mục
    document.querySelectorAll(".toggle-news").forEach(button => {
        button.addEventListener("click", function () {
            let newsId = this.getAttribute("data-news-id");
            let newsRow = document.querySelector(`.newsDetail-list[data-news-id="${newsId}"]`);

            document.querySelectorAll(".newsDetail-list").forEach(item => {
                if (item !== newsRow) {
                    item.style.display = "none";
                }
            });

            if (newsRow.style.display === "none") {
                newsRow.style.display = "table-row";
            } else {
                newsRow.style.display = "none";
            }
        });
    });

    // Xử lý sửa mục
    document.querySelectorAll(".btn-editNewsDetail").forEach(button => {
        button.addEventListener("click", function () {
            let id = this.getAttribute("data-id-newsDetail");
            let title = this.getAttribute("data-title-newsDetail");
            let content = this.getAttribute("data-content-newsDetail");
            let image = this.getAttribute("data-image-newsDetail");

            document.getElementById("editIdNewsDetail").value = id;
            document.getElementById("editTitleNewsDetail").value = title;
            document.getElementById("editContentNewsDetail").value = content;
            document.getElementById("editImageNewsDetail").value = image;

            document.getElementById("editNewsDetailForm").setAttribute("action", "/admin/news/newsdetail/" + id);
        });
    });

    // Reset form khi modal đóng
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('hidden.bs.modal', function () {
            const form = this.querySelector('form');
            if (form) {
                form.reset();
            }
        });
    });
});