window.onload = function () {
    loadAllList();
    loadUserName();
    loadUserEmail();
};

function loadAllPendingList() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let arr = JSON.parse(xhttp.responseText);

            for (let i = 0; i < arr.length; i++) {
                let reimbAmount = arr[i].reimbAmount;
                let reimbDescription = arr[i].reimbDescription;
                let reimbId = arr[i].reimbId;
                let reimbSubmitted = arr[i].reimbSubmitted;
                let typeId = arr[i].typeId;
                let typeStr = "";
                if (typeId == 1) {
                    typeStr = "Lodging";
                } else if (typeId == 2) {
                    typeStr = "Travel";
                } else if (typeId == 3) {
                    typeStr = "Food";
                } else {
                    typeStr = "Other";
                }

                let listItem = `
                    <li class="list-group-item cardList">
                        <a href="#" class="list-group-item list-group-item-action">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">$${reimbAmount}</h5>
                                <small class="text-muted">${reimbSubmitted}</small>
                            </div>
                            <p class="mb-1 description">${reimbDescription}</p>
                            <small class="text-muted">
                                Type: ${typeStr}, ReimbId: <span class="typeid">${reimbId}</span>
                                <button type="button" class="btn btn-warning btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details →</button>
                            </small>
                        </a>
                    </li>
                `;
                $("#managerList").append(listItem);
            }
        }
    }
    xhttp.open("GET", "http://localhost:8080/ERS_System/all-pendings.json");
    xhttp.send();
}

function loadAllApprovedList() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let arr = JSON.parse(xhttp.responseText);

            for (let i = 0; i < arr.length; i++) {
                let reimbAmount = arr[i].reimbAmount;
                let reimbDescription = arr[i].reimbDescription;
                let reimbId = arr[i].reimbId;
                let reimbSubmitted = arr[i].reimbSubmitted;
                let typeId = arr[i].typeId;
                let typeStr = "";
                if (typeId == 1) {
                    typeStr = "Lodging";
                } else if (typeId == 2) {
                    typeStr = "Travel";
                } else if (typeId == 3) {
                    typeStr = "Food";
                } else {
                    typeStr = "Other";
                }

                let listItem = `
                    <li class="list-group-item cardList">
                        <a href="#" class="list-group-item list-group-item-action">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">$${reimbAmount}</h5>
                                <small class="text-muted">${reimbSubmitted}</small>
                            </div>
                            <p class="mb-1 description">${reimbDescription}</p>
                            <small class="text-muted">
                                Type: ${typeStr}, ReimbId: <span class="typeid">${reimbId}</span>
                                <button type="button" class="btn btn-success btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details →</button>
                            </small>
                        </a>
                    </li>
                `;
                $("#managerList").append(listItem);
            }
        }
    }
    xhttp.open("GET", "http://localhost:8080/ERS_System/all-approved.json");
    xhttp.send();
}

function loadAllDeniedList() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let arr = JSON.parse(xhttp.responseText);

            for (let i = 0; i < arr.length; i++) {
                let reimbAmount = arr[i].reimbAmount;
                let reimbDescription = arr[i].reimbDescription;
                let reimbId = arr[i].reimbId;
                let reimbSubmitted = arr[i].reimbSubmitted;
                let typeId = arr[i].typeId;
                let typeStr = "";
                if (typeId == 1) {
                    typeStr = "Lodging";
                } else if (typeId == 2) {
                    typeStr = "Travel";
                } else if (typeId == 3) {
                    typeStr = "Food";
                } else {
                    typeStr = "Other";
                }

                let listItem = `
                    <li class="list-group-item cardList">
                        <a href="#" class="list-group-item list-group-item-action">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">$${reimbAmount}</h5>
                                <small class="text-muted">${reimbSubmitted}</small>
                            </div>
                            <p class="mb-1 description">${reimbDescription}</p>
                            <small class="text-muted">
                                Type: ${typeStr}, ReimbId: <span class="typeid">${reimbId}</span>
                                <button type="button" class="btn btn-danger btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details →</button>
                            </small>
                        </a>
                    </li>
                `;
                $("#managerList").append(listItem);
            }
        }
    }
    xhttp.open("GET", "http://localhost:8080/ERS_System/all-denied.json");
    xhttp.send();
}

function loadAllList() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let arr = JSON.parse(xhttp.responseText);

            for (let i = 0; i < arr.length; i++) {
                let reimbAmount = arr[i].reimbAmount;
                let reimbDescription = arr[i].reimbDescription;
                let reimbId = arr[i].reimbId;
                let reimbSubmitted = arr[i].reimbSubmitted;
                let typeId = arr[i].typeId;
                let typeStr = "";
                if (typeId == 1) {
                    typeStr = "Lodging";
                } else if (typeId == 2) {
                    typeStr = "Travel";
                } else if (typeId == 3) {
                    typeStr = "Food";
                } else {
                    typeStr = "Other";
                }

                let listItem = `
                    <li class="list-group-item cardList">
                        <a href="#" class="list-group-item list-group-item-action">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">$${reimbAmount}</h5>
                                <small class="text-muted">${reimbSubmitted}</small>
                            </div>
                            <p class="mb-1 description">${reimbDescription}</p>
                            <small class="text-muted">
                                Type: ${typeStr}, ReimbId: <span class="typeid">${reimbId}</span>
                                <button type="button" class="btn btn-info btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details →</button>
                            </small>
                        </a>
                    </li>
                `;
                $("#managerList").append(listItem);
            }
        }
    }
    xhttp.open("GET", "http://localhost:8080/ERS_System/all-list.json");
    xhttp.send();
}

function emptyCurrentListContainer() {
    let ulDom = $("#managerList");
    ulDom.empty();
}

function loadUserName() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let userName = JSON.parse(xhttp.responseText);
            $("#currentManager").text(userName);
        }
    };
    xhttp.open("GET", "http://localhost:8080/ERS_System/employee-name.json");
    xhttp.send();
}

function loadUserEmail(){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let userEmail = JSON.parse(xhttp.responseText);
            $("#currentEmail").text(userEmail);
        }
    };
    xhttp.open("GET", "http://localhost:8080/ERS_System/user-email.json");
    xhttp.send();
}

function reimbClickFunc(event) {
    // when click the details button, first clear all the text in the detail pane.
    // for better visual effect.
    $("#detail-amount").text("");
    $("#detail-submitted").text("");
    $("#detail-resolved").text("");
    $("#des_text").text("");
    $("#detail-receipt").text("");
    $("#detail-author").text("");
    $("#detail-manager").text("");
    $("#detail-status").text("");
    $("#detail-type").text("");

    // this not displayed container is to hold the reimbId for the displayed reimb.
    // so that later its innerText can be obtained and used by Deny and Approve functions.
    $("#reimbIdSecretBox").text("");

    let spanText = $(event.target).prev().text();
    console.log(spanText);

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log(JSON.parse(xhttp.responseText));
            let resObj = JSON.parse(xhttp.responseText);
            $("#detail-amount").text(`$${resObj.reimbAmount}`);
            $("#detail-submitted").text(resObj.reimbSubmitted);
            $("#detail-resolved").text(resObj.reimbResolved);
            $("#des_text").text(resObj.reimbDescription);
            $("#detail-receipt").text("Your receipt is lost on Mars (⊙v⊙)");
            $("#detail-author").text(resObj.authorName);
            $("#detail-manager").text(resObj.resolverName);
            $("#detail-status").text(resObj.statusName);
            $("#detail-type").text(resObj.typeName);

            $("#reimbIdSecretBox").text(resObj.reimbId);
        }
    }

    xhttp.open("POST", "http://localhost:8080/ERS_System/reimb-details.json", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(`reimbIdStr=${spanText}`);

}

function applyFilter() {
    let optionStr = $("#filter-options").val();
    emptyCurrentListContainer();
    switch (optionStr) {
        case "all":
            loadAllList();
            break;
        case "pending":
            loadAllPendingList();
            break;
        case "approved":
            loadAllApprovedList();
            break;
        case "denied":
            loadAllDeniedList();
            break;
    }
    console.log("filter button clicked");
}

function approveReimb() {
    let thisStatus = $("#detail-status").text();
    if (thisStatus == "denied" || thisStatus == "approved") {
        alert("You cannot approve/deny a processed reimbursement request.");
        return;
    }

    let id = $("#reimbIdSecretBox").text();
    if (id == "") {
        alert("You must load a reimbursement first so as to approve/deny.");
        return;
    }

    $(".my_btn").attr("disabled", true);

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log("server has processed the approve action");

            $("#detail-amount").text("");
            $("#detail-submitted").text("");
            $("#detail-resolved").text("");
            $("#des_text").text("");
            $("#detail-receipt").text("");
            $("#detail-author").text("");
            $("#detail-manager").text("");
            $("#detail-status").text("");
            $("#detail-type").text("");
            $("#reimbIdSecretBox").text("");

            applyFilter();
            $(".my_btn").attr("disabled", false);
        }
    }
    xhttp.open("POST", "http://localhost:8080/ERS_System/reimb-approve.json", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(`reimbIdStr=${id}`);
}

function denyReimb() {
    let thisStatus = $("#detail-status").text();
    if (thisStatus == "denied" || thisStatus == "approved") {
        alert("You cannot approve/deny a processed reimbursement request.");
        return;
    }

    let id = $("#reimbIdSecretBox").text();
    if (id == "") {
        alert("You must load a reimbursement first so as to approve/deny.");
        return;
    }

    $(".my_btn").attr("disabled", true);

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log("server has processed the approve action");

            $("#detail-amount").text("");
            $("#detail-submitted").text("");
            $("#detail-resolved").text("");
            $("#des_text").text("");
            $("#detail-receipt").text("");
            $("#detail-author").text("");
            $("#detail-manager").text("");
            $("#detail-status").text("");
            $("#detail-type").text("");
            $("#reimbIdSecretBox").text("");

            applyFilter();
            $(".my_btn").attr("disabled", false);
        }
    }
    xhttp.open("POST", "http://localhost:8080/ERS_System/reimb-deny.json", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(`reimbIdStr=${id}`);
}