function toggleProjectForm() {
    let form = document.getElementById("projectFormContainer");
    form.style.display = (form.style.display === "none") ? "block" : "none";
}

document.addEventListener("DOMContentLoaded", function () {
    // Xử lý modal thêm BDS
    const propertyModal = document.getElementById('propertyModal');
    if (propertyModal) {
        propertyModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const idProject = button.getAttribute('data-id-project');
            const form = document.getElementById('propertyForm');
            const inputId = document.getElementById('idProject');

            if (form && inputId) {
                inputId.value = idProject;
                form.action = '/admin/project/' + idProject;
            }
        });
    }

    // Xử lý sửa dự án
    document.querySelectorAll(".btn-editProject").forEach(button => {
        button.addEventListener("click", function () {
            let id = this.getAttribute("data-id");
            let name = this.getAttribute("data-name");
            let description = this.getAttribute("data-description");
            let address = this.getAttribute("data-address");
            let startDate = this.getAttribute("data-startDate");
            let status = this.getAttribute("data-status");
            let investorName = this.getAttribute("data-investorName");
            let investorAddress = this.getAttribute("data-investorAddress");
            let investorPhone = this.getAttribute("data-investorPhone");
            let investorEmail = this.getAttribute("data-investorEmail");
            let wardCode = this.getAttribute("data-ward_code");

            document.getElementById("editId").value = id;
            document.getElementById("editName").value = name;
            document.getElementById("editDescription").value = description;
            document.getElementById("editAddress").value = address;
            document.getElementById("editStartDate").value = startDate;
            document.getElementById("editStatus").value = status;
            document.getElementById("editInvestorName").value = investorName;
            document.getElementById("editInvestorAddress").value = investorAddress;
            document.getElementById("editInvestorPhone").value = investorPhone;
            document.getElementById("editInvestorEmail").value = investorEmail;
            document.getElementById("editWard_id").value = wardCode;

            document.getElementById("editProjectForm").setAttribute("action", "/admin/project/" + id);
        });
    });

    // Xử lý hiển thị danh sách BDS
    document.querySelectorAll(".toggle-project").forEach(button => {
        button.addEventListener("click", function () {
            let projectId = this.getAttribute("data-project-id");
            let projectRow = document.querySelector(`.property-list[data-project-id="${projectId}"]`);

            document.querySelectorAll(".property-list").forEach(item => {
                if (item !== projectRow) {
                    item.style.display = "none";
                }
            });

            if (projectRow.style.display === "none") {
                projectRow.style.display = "table-row";
            } else {
                projectRow.style.display = "none";
            }
        });
    });

    // Xử lý sửa BDS
    document.querySelectorAll(".btn-editProperty").forEach(button => {
        button.addEventListener("click", function () {
            let id = this.getAttribute("data-id-property");
            let name = this.getAttribute("data-name-property");
            let description = this.getAttribute("data-description-property");
            let price = this.getAttribute("data-price-property");
            let area = this.getAttribute("data-area-property");
            let type = this.getAttribute("data-type-property");
            let status = this.getAttribute("data-status-property");

            document.getElementById("editIdProperty").value = id;
            document.getElementById("editNameProperty").value = name;
            document.getElementById("editDescriptionProperty").value = description;
            document.getElementById("editPriceProperty").value = price;
            document.getElementById("editAreaProperty").value = area;
            document.getElementById("editTypeProperty").value = type;
            document.getElementById("editStatusProperty").value = status;

            document.getElementById("editPropertyForm").setAttribute("action", "/admin/project/property/" + id);

        });
    });

    // Reset form khi modal đóng
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('hidden.bs.modal', function () {
            const form = this.querySelector('form');
            if (form) {
                form.reset();
                const districtSelect = form.querySelector('select[name="district"]');
                const wardSelect = form.querySelector('select[name="ward_id"]');
                if (districtSelect) {
                    districtSelect.innerHTML = '<option value="">Chọn huyện</option>';
                }
                if (wardSelect) {
                    wardSelect.innerHTML = '<option value="">Chọn xã</option>';
                }
            }
        });
    });

    // Gán sự kiện onchange cho dropdown trong modal "Thêm Bất Động Sản"
    const province = document.getElementById('province');
    const district = document.getElementById('district');
    if (province && district) {
        province.addEventListener('change', loadDistricts);
        district.addEventListener('change', loadWards);
    }

    // Gán sự kiện onchange cho dropdown trong modal "Sửa Bất Động Sản"
    const editProvince = document.getElementById('editProvince');
    const editDistrict = document.getElementById('editDistrict');
    if (editProvince && editDistrict) {
        editProvince.addEventListener('change', function () {
            loadDistrictsForEdit(this.value);
        });
        editDistrict.addEventListener('change', function () {
            loadWardsForEdit(this.value);
        });
    }
});

function loadDistricts() {
    const provinceCode = document.getElementById('province').value;
    const districtSelect = document.getElementById('district');
    const wardSelect = document.getElementById('ward_id');

    districtSelect.innerHTML = '<option value="">Chọn huyện</option>';
    wardSelect.innerHTML = '<option value="">Chọn xã</option>';

    if (provinceCode) {
        fetch(`/api/districts?province_code=${provinceCode}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                data.forEach(district => {
                    const option = document.createElement('option');
                    option.value = district.code;
                    option.text = district.name;
                    districtSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading districts:', error));
    }
}

function loadWards() {
    const districtCode = document.getElementById('district').value;
    const wardSelect = document.getElementById('ward_id');

    wardSelect.innerHTML = '<option value="">Chọn xã</option>';

    if (districtCode) {
        fetch(`/api/wards?district_code=${districtCode}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                data.forEach(ward => {
                    const option = document.createElement('option');
                    option.value = ward.code;
                    option.text = ward.name;
                    wardSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading wards:', error));
    }
}

function loadDistrictsForEdit() {
    const province = document.getElementById('editProvince').value;
    const districtSelect = document.getElementById('editDistrict');
    const wardSelect = document.getElementById('editWard_id');

    districtSelect.innerHTML = '<option>Chọn huyện</option>';
    wardSelect.innerHTML = '<option>Chọn xã</option>';

    if (province) {
        fetch(`/api/districts?province_code=${province}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                data.forEach(district => {
                    const option = document.createElement('option');
                    option.value = district.code;
                    option.text = district.name;
                    districtSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading districts:', error));
    }
}

function loadWardsForEdit() {
    const district = document.getElementById('editDistrict').value;
    const wardSelect = document.getElementById('editWard_id');

    wardSelect.innerHTML = '<option>Chọn xã</option>';

    if (district) {
        fetch(`/api/wards?district_code=${district}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                data.forEach(ward => {
                    const option = document.createElement('option');
                    option.value = ward.code;
                    option.text = ward.name;
                    wardSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading wards:', error));
    }
}