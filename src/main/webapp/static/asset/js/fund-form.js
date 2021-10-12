let baseUrl ='http://localhost:8080/project1';


function getLoginUser(name){
    let cookies = document.cookie;
    // console.log(cookies);
    // var name = "user";

    if(cookies.length != 0){
        let arr = cookies.split("=");
        // console.log(arr);

        for(var i = 0; i < arr.length -1; i++){
            // console.log(i);
            let cookie_name = arr[i];
            // console.log(cookie_name)

            if(cookie_name == name){
                let data = arr[i+1];
                // console.log(data);
                let cleanData = data.replace(/\\/g,'')
                // console.log(cleanData);
                let sliceData = cleanData.slice(1, cleanData.length - 1);
                // console.log(sliceData);
                let jsonData = JSON.parse(sliceData);
                // console.log(jsonData);
                return jsonData;
            }
        }
    }
}

//getting profile information
async function getInfo(){
    let userInfo = getLoginUser("user")
    // console.log(userInfo.designation.title);
    if(userInfo != null){
        let profile = document.getElementById("profile");
        profile.innerHTML = `Welcome, ${userInfo.firstName} ${userInfo.lastName}`;
        await getReimbursementById()
    }else{
        window.location = "/project1/static/login.html" 
    }

};

async function getReimbursementById(){

    let remId = sessionStorage.getItem("remId");
    let cardItem = document.getElementById("card-item");


    // console.log(id);
    await fetch(`${baseUrl}/reimbursements/${remId}`, {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {

        console.log(data);
        let formD = `
            <div class="input-group mb-3">
                <span class="input-group-text" >Request Name</span>
                <input type="text" class="form-control"  aria-label="user" 
                    readonly disabled
                    value="${data.employee.firstName} ${data.employee.lastName}"
                >
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text" id="eventName">Reimbursement Type</span>
                <input type="text" class="form-control" id="eventName" aria-label="eventName" aria-describedby="eventName" 
                    readonly disabled
                    value="${data.event.eventName}"
                >
            </div>

            <input type="number" id="percent" value="${data.event.percent}" hidden/>

            <div class="input-group mb-3">
                <span class="input-group-text">Event Cost</span>
                <input type="number" class="form-control" id="fund" aria-label="fund" 
                    aria-describedby="fund" readonly disabled
                    value="${data.eventCost}"
                >
            </div>

            <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="approved">
                <label class="form-check-label" for="approved">
                    Approved
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked>
                <label class="form-check-label" for="flexRadioDefault2">
                    Rejected
                </label>
            </div>

            <div class="gap-2 mt-2 mb-3 d-grid">
                <button type="button" class="btn btn-primary" onclick="postFund()">Release funds</button>
            </div>
        
        `;

        cardItem.innerHTML = formD;

    })
    .catch(err => {
        // console.log(err);
        alert("Fail get request");
        window.location =  "/project1/static/approval.html";

    })

}

//create
async function postFund(){
    let empId = sessionStorage.getItem("rEmpId")
    let remId = sessionStorage.getItem("remId");

    console.log(empId);

    let percent = document.getElementById("percent").value;
    console.log(percent);
    let cost = document.getElementById("fund").value;
    console.log(cost);
    let status = document.getElementById("approved").checked;
    console.log(status);
    let approvedFund = cost * (percent/100);
    console.log(approvedFund);
    let initialFund = 1000.0
    let remaining = initialFund - approvedFund;

    let data = {
        employee:{
            id : parseInt(empId)
        },
        reimbursement:{
            id: parseInt(remId)
        },
        initialFund: parseFloat(initialFund),
        allocatedFund: parseFloat(approvedFund),
        remainingFund:parseFloat(remaining),
        status: status

    };

    console.log(data);

    let jsonD = JSON.stringify(data);
    console.log(jsonD);
    let url =`${baseUrl}/funds`;

    console.log(url);

    await fetch(url,{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: jsonD
    })
    .then(res => res)
    .then(data => {
        console.log(data.status)
        if(data.status === 200){

            window.location = "/project1/static/dashboard.html"
            alert(" Funds Release successfully");
        }
        // window.location = data;
    })
    .catch(error => {
        console.log(error);
        window.location =  "/project1/static/fund-form.html"
        alert("Fail to Release funds.")

    });

}

//update
async function putfund(){
    let empId = sessionStorage.getItem("rEmpId")
    let remId = sessionStorage.getItem("remId");

    console.log(empId);

    let percent = document.getElementById("percent").value;
    console.log(percent);
    let cost = document.getElementById("fund").value;
    console.log(cost);
    let status = document.getElementById("approved").checked;
    console.log(status);
    let approvedFund = cost * (percent/100);
    console.log(approvedFund);
    let initialFund = 1000.0
    let remaining = initialFund - approvedFund;

    let data = {
        employee:{
            id : parseInt(empId)
        },
        reimbursement:{
            id: parseInt(remId)
        },
        initialFund: parseFloat(initialFund),
        allocatedFund: parseFloat(approvedFund),
        remainingFund:parseFloat(remaining),
        status: status

    };

    console.log(data);

    let jsonD = JSON.stringify(data);
    console.log(jsonD);
    let url =`${baseUrl}/funds/id`;

    console.log(url);

    await fetch(url,{
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: jsonD
    })
    .then(res => res)
    .then(data => {
        console.log(data.status)
        if(data.status === 200){

            window.location = "/project1/static/dashboard.html"
            alert(" Funds Release successfully");
        }
        // window.location = data;
    })
    .catch(error => {
        console.log(error);
        window.location =  "/project1/static/fund-form.html"
        alert("Fail to Release funds.")

    });

}
