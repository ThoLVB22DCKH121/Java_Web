document.addEventListener("DOMContentLoaded", function () {
    let toggleButtons = document.querySelectorAll(".toggle-project");

    toggleButtons.forEach(button => {
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
});
