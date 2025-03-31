// Hàm toggle form thêm cẩm nang
function toggleHandBookForm() {
    let form = document.getElementById("handBookFormContainer");
    form.style.display = (form.style.display === "none") ? "block" : "none";
}


document.addEventListener("DOMContentLoaded", function () {
    // Xử lý modal mục cẩm nang
    const handBookDetailModal = document.getElementById('handBookDetailModal');
    if (handBookDetailModal) {
        handBookDetailModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const idHandBook = button.getAttribute('data-id-handbook');
            const form = document.getElementById('handBookDetailForm');
            const inputId = document.getElementById('idHandBook');

            if (form && inputId) {
                inputId.value = idHandBook;
                form.action = '/admin/handbook/' + idHandBook;
            }
        });
    }

    // Xử lý sửa cẩm nang
    document.querySelectorAll(".btn-editHandBook").forEach(button => {
        button.addEventListener("click", function () {
            let id = this.getAttribute("data-id");
            let title = this.getAttribute("data-title");
            let category = this.getAttribute("data-category");

            document.getElementById("editId").value = id;
            document.getElementById("editTitle").value = title;
            document.getElementById("editCategory").value = category;

            document.getElementById("editHandBookForm").setAttribute("action", "/admin/handbook/" + id);
        });
    });

    // Xử lý hiển thị từng mục
    document.querySelectorAll(".toggle-handBook").forEach(button => {
        button.addEventListener("click", function () {
            let handBookId = this.getAttribute("data-handBook-id");
            let handBookRow = document.querySelector(`.handBookDetail-list[data-handBook-id="${handBookId}"]`);

            document.querySelectorAll(".handBookDetail-list").forEach(item => {
                if (item !== handBookRow) {
                    item.style.display = "none";
                }
            });

            if (handBookRow.style.display === "none") {
                handBookRow.style.display = "table-row";
            } else {
                handBookRow.style.display = "none";
            }
        });
    });

    // Xử lý sửa mục
    document.querySelectorAll(".btn-editHandBookDetail").forEach(button => {
        button.addEventListener("click", function () {
            let id = this.getAttribute("data-id-handBookDetail");
            let title = this.getAttribute("data-title-handBookDetail");
            let content = this.getAttribute("data-content-handBookDetail");
            let image = this.getAttribute("data-image-handBookDetail");

            document.getElementById("editIdHandBookDetail").value = id;
            document.getElementById("editTitleHandBookDetail").value = title;
            document.getElementById("editContentHandBookDetail").value = content;
            document.getElementById("editImageHandBookDetail").value = image;

            document.getElementById("editHandBookDetailForm").setAttribute("action", "/admin/handbook/handbookdetail/" + id);
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