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
            $("#detail-receipt").text("Your receipt is lost on Mars (⊙v⊙)");
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
                                <button type="button" class="btn btn-warning btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details →</button>
                            </small>
                        </a>
                    </li>
                `;

                // 因为这些li是从数据库获取信息，然后异步加载到页面的。所以它们是后加载。
                // 而Chrome浏览器debug的“天坑”在于，当尝试用document.getElementsByClassName获取这些li或者a元素时，
                // 会返回一个HTMLCollection，而这个集合最开始是空的，因为异步还没有把li加载到页面。
                // 而异步数据到达之后，这个HTMLCollection自动填充，变得有内容。
                // 但Chrome的“基因缺陷”就是，填充之后的HTMLCollection会自动覆盖console中的原来空的HTMLCollection，
                // 导致你看到的时候，永远是填充后的状态，你永远看不到未填充时的状态。
                // 这就导致，即使用for遍历这个HTMLCollection，想通过这种方式给元素绑定回调函数，
                // 是根本做不到的，因为for一开始就执行，因为HTMLCollection是空，所以for永远不会执行。
                // 所以回调函数永远不可能绑定成功。解决办法就必须是，获取到这个元素的时候，再绑定。
                // 所以最好就是用行内事件绑定方式，onclick="handler()"。啥时候元素有了，啥时候绑定。

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
                                <button type="button" class="btn btn-success btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details →</button>
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
                                <button type="button" class="btn btn-danger btn-sm view-detail-btn" onclick="reimbClickFunc(event)">Details →</button>
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
