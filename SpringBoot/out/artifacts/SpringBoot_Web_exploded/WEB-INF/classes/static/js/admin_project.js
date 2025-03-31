function toggleProjectForm() {
    let form = document.getElementById("projectFormContainer");
    form.style.display = (form.style.display === "none") ? "block" : "none";
}

function togglePropertyForm() {
    let form = document.getElementById("propertyFormContainer");
    form.style.display = (form.style.display === "none") ? "block" : "none";
}

document.addEventListener("DOMContentLoaded", function () {
    // Xử lý sửa dự án
    document.querySelectorAll(".btn-editProject").forEach(button => {
        button.addEventListener("click", function () {
            let id = this.getAttribute("data-id");
            let name = this.getAttribute("data-name");
            let location = this.getAttribute("data-location");
            let status = this.getAttribute("data-status");

            document.getElementById("editId").value = id;
            document.getElementById("editName").value = name;
            document.getElementById("editLocation").value = location;
            document.getElementById("editStatus").value = status;

            document.getElementById("editProjectForm").setAttribute("action", "/admin/project/" + id);
        });
    });

    // Xử lý hiển thị danh sách BDS của từng dự án
    document.querySelectorAll(".toggle-project").forEach(button => {
        button.addEventListener("click", function () {
            let projectId = this.getAttribute("data-project-id");
            let propertyRow = document.querySelector(`.property-list[data-project-id="${projectId}"]`);

            document.querySelectorAll(".property-list").forEach(item => {
                if (item !== propertyRow) {
                    item.style.display = "none";
                }
            });

            if (propertyRow.style.display === "none") {
                propertyRow.style.display = "table-row";
            } else {
                propertyRow.style.display = "none";
            }
        });
    });

    // Xử lý sửa BDS
    document.querySelectorAll(".btn-editProperty").forEach(button => {
        button.addEventListener("click", function () {
            let idProperty = this.getAttribute("data-id-property");
            let price = this.getAttribute("data-price-property");
            let type = this.getAttribute("data-type-property");
            let status = this.getAttribute("data-status-property");

            document.getElementById("editIdProperty").value = idProperty;
            document.getElementById("editPriceProperty").value = price;
            document.getElementById("editTypeProperty").value = type;
            document.getElementById("editStatusProperty").value = status;

            document.getElementById("editPropertyForm").setAttribute("action", "/admin/project/property/" + idProperty);
        });
    });
});
