function openUpdateRoleModal(button) {
    let userId = button.getAttribute("data-id");
    let currentRole = button.getAttribute("data-role");

    document.getElementById("userId").value = userId;
    document.getElementById("editRole").value = currentRole;

    let form = document.getElementById("updateRoleForm");
    form.action = "/admin/user/" + userId;

    let modal = new bootstrap.Modal(document.getElementById("updateRoleModal"));
    modal.show();
}