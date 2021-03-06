window.onload = function () {
    loadOnesPendingList();
    loadOnesApprovedList();
    loadOnesDeniedList();
    loadUserName();
    loadUserEmail();
};

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
            $("#detail-receipt").text("Your receipt is lost on Mars (āvā)");
            $("#detail-author").text(resObj.authorName);
            $("#detail-manager").text(resObj.resolverName);
            $("#detail-status").text(resObj.statusName);
            $("#detail-type").text(resObj.typeName);
        }
    }

    xhttp.open("POST", "http://localhost:8080/ERS_System/reimb-details.json", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(`reimbIdStr=${spanText}`);

}

function loadOnesPendingList() {
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
                                <button type="button" class="btn btn-warning btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details ā</button>
                            </small>
                        </a>
                    </li>
                `;

                // å äøŗčæäŗlięÆä»ę°ę®åŗč·åäæ”ęÆļ¼ē¶åå¼ę­„å č½½å°é”µé¢ēćęä»„å®ä»¬ęÆåå č½½ć
                // čChromeęµč§åØdebugēāå¤©åāåØäŗļ¼å½å°čÆēØdocument.getElementsByClassNameč·åčæäŗlięčaåē“ ę¶ļ¼
                // ä¼čæåäøäøŖHTMLCollectionļ¼ččæäøŖéåęå¼å§ęÆē©ŗēļ¼å äøŗå¼ę­„čæę²”ęęliå č½½å°é”µé¢ć
                // čå¼ę­„ę°ę®å°č¾¾ä¹åļ¼čæäøŖHTMLCollectiončŖåØå”«åļ¼åå¾ęåå®¹ć
                // ä½Chromeēāåŗå ē¼ŗé·āå°±ęÆļ¼å”«åä¹åēHTMLCollectionä¼čŖåØč¦ēconsoleäø­ēåę„ē©ŗēHTMLCollectionļ¼
                // åÆ¼č“ä½ ēå°ēę¶åļ¼ę°øčæęÆå”«ååēē¶ęļ¼ä½ ę°øčæēäøå°ęŖå”«åę¶ēē¶ęć
                // čæå°±åÆ¼č“ļ¼å³ä½æēØforéåčæäøŖHTMLCollectionļ¼ę³éčæčæē§ę¹å¼ē»åē“ ē»å®åč°å½ę°ļ¼
                // ęÆę ¹ę¬åäøå°ēļ¼å äøŗforäøå¼å§å°±ę§č”ļ¼å äøŗHTMLCollectionęÆē©ŗļ¼ęä»„forę°øčæäøä¼ę§č”ć
                // ęä»„åč°å½ę°ę°øčæäøåÆč½ē»å®ęåćč§£å³åę³å°±åæé”»ęÆļ¼č·åå°čæäøŖåē“ ēę¶åļ¼åē»å®ć
                // ęä»„ęå„½å°±ęÆēØč”åäŗä»¶ē»å®ę¹å¼ļ¼onclick="handler()"ćå„ę¶ååē“ ęäŗļ¼å„ę¶åē»å®ć

                $("#employeePendings").append(listItem);
            }

        }
    }
    xhttp.open("GET", "http://localhost:8080/ERS_System/employee-pendings.json");
    xhttp.send();
};

function loadOnesApprovedList() {
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
                                <button type="button" class="btn btn-success btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details ā</button>
                            </small>
                        </a>
                    </li>
                `;

                $("#employeeApproved").append(listItem);
            }

        }
    }
    xhttp.open("GET", "http://localhost:8080/ERS_System/employee-approved.json");
    xhttp.send();
};

function loadOnesDeniedList() {
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
                                <button type="button" class="btn btn-danger btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details ā</button>
                            </small>
                        </a>
                    </li>
                `;

                $("#employeeDenied").append(listItem);
            }

        }
    }
    xhttp.open("GET", "http://localhost:8080/ERS_System/employee-denied.json");
    xhttp.send();
};

function loadUserName() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let userName = JSON.parse(xhttp.responseText);
            $("#currentEmployee").text(userName);
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

function clearForm() {
    $("#description").val("");
    $("#amount").val("");
    $("#receipt").val(null);
    $("#type").val(1);
}
