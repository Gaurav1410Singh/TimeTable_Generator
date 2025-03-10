const apiUrl = "http://localhost:8080/timetable/teachers";

const teacherTableBody = document.getElementById("teacherTableBody");
const saveButton = document.getElementById("saveButton");

const updateDialog = document.getElementById("updateDialog");
const dialogOverlay = document.getElementById("dialogOverlay");
const updateButton = document.getElementById("updateButton");
const cancelButton = document.getElementById("cancelButton");

let currentTeacherId = null;

// Save Teacher
saveButton.addEventListener("click", async () => {
    const teacher = {
        name: document.getElementById("teacherName").value,
        nickname: document.getElementById("teacherNickname").value,
        department: document.getElementById("teacherDepartment").value,
        needsFreeFourthLecture: document.getElementById("teacherFreeFourth").checked,
        secondary: document.getElementById("teacherSecondary").checked,
    };

    const response = await fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(teacher),
    });

    if (response.ok) {
        alert("Teacher added successfully!");
        loadTeachers();
    }
});

// Load Teachers
async function loadTeachers() {
    const response = await fetch(apiUrl);
    const teachers = await response.json();

    teacherTableBody.innerHTML = "";
    teachers.forEach((teacher) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${teacher.name}</td>
            <td>${teacher.nickname}</td>
            <td>${teacher.department}</td>
            <td>${teacher.needsFreeFourthLecture ? "Yes" : "No"}</td>
            <td>${teacher.secondary ? "Yes" : "No"}</td>
            <td>
                <button onclick="deleteTeacher(${teacher.id})">Delete</button>
                <button onclick="openUpdateDialog(${teacher.id}, '${teacher.name}', '${teacher.nickname}', '${teacher.department}', ${teacher.needsFreeFourthLecture}, ${teacher.secondary})">Update</button>
            </td>
        `;
        teacherTableBody.appendChild(row);
    });
}

// Delete Teacher
async function deleteTeacher(id) {
    const response = await fetch(`${apiUrl}/${id}`, { method: "DELETE" });

    if (response.ok) {
        alert("Teacher deleted successfully!");
        loadTeachers();
    }
}

// Open Update Dialog
function openUpdateDialog(id, name, nickname, department, needsFreeFourthLecture, secondary) {
    currentTeacherId = id;

    document.getElementById("updateTeacherName").value = name;
    document.getElementById("updateTeacherNickname").value = nickname;
    document.getElementById("updateTeacherDepartment").value = department;
    document.getElementById("updateTeacherFreeFourth").checked = needsFreeFourthLecture;
    document.getElementById("updateTeacherSecondary").checked = secondary;

    updateDialog.style.display = "block";
    dialogOverlay.style.display = "block";
}

// Close Update Dialog
cancelButton.addEventListener("click", () => {
    updateDialog.style.display = "none";
    dialogOverlay.style.display = "none";
});

// Update Teacher
updateButton.addEventListener("click", async () => {
    const updatedTeacher = {
        id: currentTeacherId,
        name: document.getElementById("updateTeacherName").value,
        nickname: document.getElementById("updateTeacherNickname").value,
        department: document.getElementById("updateTeacherDepartment").value,
        needsFreeFourthLecture: document.getElementById("updateTeacherFreeFourth").checked,
        secondary: document.getElementById("updateTeacherSecondary").checked,
    };

    const response = await fetch(`${apiUrl}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedTeacher),
    });

    if (response.ok) {
        alert("Teacher updated successfully!");
        loadTeachers();
        updateDialog.style.display = "none";
        dialogOverlay.style.display = "none";
    }
});

// Load Teachers on Page Load
loadTeachers();
