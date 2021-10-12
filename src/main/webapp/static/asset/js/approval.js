
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
        await readApproval();
        // await updateGrade();
        // await getStatusForm();
    }else{
        window.location = "/project1/static/login.html" 
    }

};
// async function getSideBar(){
//     let menu = document.querySelector('#side-menu');
//     let navtag= document.getElementsByTagName('ul');
//      navtag.innerHTML= `
//             // <ul class="nav flex-column ps-3">

//                 <li class="mt-3 nav-item">
//                     <a class="nav-link active"  href="dashboard.html">Dashboard</a>
//                 </li>
//                 <li class="nav-item">
//                     <a class="nav-link" href="file.html">Attachment</a>
//                 </li>
//                 <li class="nav-item">
//                     <a class="nav-link" href="request.html">Request Form</a>
//                 </li>
//                 <li class="nav-item">
//                     <a class="nav-link" href="fund-form.html">Fund Release</a>
//                 </li>
//                 <li class="nav-item">
//                     <a class="nav-link" href="employee.html">Employees</a>
//                 </li>
//                 <li class="nav-item">
//                     <a class="nav-link" href="reimbursement.html">Reimbursement</a>
//                 </li>
//                 <li class="nav-item">
//                 <a class="nav-link" href="approval.html">Approved</a>
//                 </li>
//             // </ul>
//     </div>
//     `;

//     menu.innerHTML = navtag;
// }


async function readApproval(){

    await fetch(`${baseUrl}/approvals`, {
        method: 'GET'
    })
    .then(res =>{
      return res.json();
    })
    .then(data => {
        let tagDiv = document.getElementById("approvals");

        while(tagDiv.firstChild){
            tagDiv.removeChild(tagDiv.firstChild);
        }
        //populate table
       
        data.forEach( function(app){
            // console.log(app);
            // if(getLoginUser("user").designation.title === app.employee.designation.title)
          let card =  
            `<div class="col-md-3 mb-3 mx-4 " >
                <div class="accordion" id="accordionExample">
                    <div class="accordion-item">
                        <h3 class="accordion-header" id="headingOne">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne${app.reimbursement.id}" aria-expanded="true" aria-controls="collapseOne">
                                ${app.reimbursement.event.eventName} 
                            </button>
                        </h3>
                        <div id="collapseOne${app.reimbursement.id}" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                            <div class="accordion-body">

                                <p class=""><strong> Description :</strong> <br>${app.reimbursement.eventDescription}</p>
                                <p class=""> <strong> Start At :</strong> ${formatDate(app.reimbursement.eventStart)}</p>
                                <p class=""> <strong> Time :</strong> ${app.reimbursement.eventTime}</p>
                                <p class=""> <strong> Cost :</strong> ${app.reimbursement.eventCost} ${checkLogin( "fund,"+app.id+","+app.reimbursement.employee.id) == undefined ? "" :checkLogin( "fund,"+app.reimbursement.id+","+app.reimbursement.employee.id)}</p>
                                <p class=""> <strong> Grade :</strong> ${app.reimbursement.grade.gradeLabel} ${ checkLogin("grade,"+app.id+","+app.reimbursement.id) == undefined ? "": checkLogin("grade,"+app.id+","+app.reimbursement.id)}</p>
                                <p class=""> <strong> Vanue :</strong> ${app.reimbursement.eventLocation}</p>
                                <p class=""> <strong> RequestBy :</strong> <span>${app.reimbursement.employee.firstName}</span> <span>${app.reimbursement.employee.lastName}</span> </p>
                                <p class=""> <strong> Departmant :</strong> ${app.reimbursement.employee.designation.title}</p>  
            
                            </div>
                            <div class="collapse" id="collapseGrade">
                                <div class="card card-body">
                                    <input type="text" hidden value="${app.reimbursement.id}" id="hidden${app.reimbursement.designation}"/>
                                    <select class="form-select form-select-sm mb-2" name="grade" id="grade">
                                        <option value="1">Pass</option>
                                    </select>
                                    <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseGrade" aria-expanded="false" aria-controls="collapseGrade" onclick="updateGrade(${app.reimbursement.id})">
                                        save
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h3 class="accordion-header" id="headingTwo">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo${app.reimbursement.id}" aria-expanded="false" aria-controls="collapseTwo">
                                Status of Reimbursement
                            </button>
                        </h3>
                        <div id="collapseTwo${app.reimbursement.id}" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <p class=""> <strong> Status :</strong> ${app.status==true? "approved" : "Pending"} ${checkLogin( "status,"+app.id+","+app.reimbursement.id)}</p>
                                <p class=""> <strong> Reason :</strong> <br>${app.reason == null ? "Pending for review": app.reason}</p>
                                <p class=""> <strong> approvedBy :</strong> <span>${app.employee.firstName  == null ? " Not Review": app.employee.firstName}</span> <span>${app.employee.lastName == null ? " ": app.employee.lastName}</span></p>
                                <p class=""> <strong>  </strong> ${app.employee.designation.title == null ? " " : app.employee.designation.title}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;

            tagDiv.innerHTML += card;
        });
    })
    .catch(err => {
        // console.log(err);
        alert('Error: ' + err.message);
    });

}


       
function checkLogin(checking){
    let arr = checking.split(',');

    // console.log(arr[0]);
    // console.log(arr[1]);
    // console.log(arr[2]);
    let user = getLoginUser("user").designation.title;

    if(user != "Associate/Employee"){

        if(user == "Benefits Coordinator"){
            if(arr[0] == "fund"){
                return `<button class="btn btn-sm btn-outline-primary ms-2 ${ user != "Benefits Coordinator" ? "d-none" :""}" onclick="getFundForm(${arr[1]} , ${arr[2]})"> Release </button> `;
            }
        
        }
        if(arr[0] == "grade"){
            return `<a class="btn btn-sm btn-outline-primary ms-2${ user == "Associate/Employee" ? "d-none" :""}" data-bs-toggle="collapse" href="#collapseGrade" role="button" aria-expanded="false" aria-controls="collapseGrade"> Edit </a>`;
        }
        if(arr[0] == "status"){
            // let sKey = `${arr[1]},${arr[2]}`;
            // console.log(sKey);
            return `<update class="btn btn-sm btn-outline-primary ms-2" onclick="getStatusForm(${arr[1]} , ${arr[2]})"> ${arr[1] != 0 ? "update": "edit"} </button> `;
        }
        // else if(arr[0] == "status"){
        //     return `<button class="btn btn-sm btn-outline-primary ms-2" onclick="getStatusForm(${arr[1]} , ${arr[2]})"> Edit </button> `;
        // }
    }
}

async function updateGrade(id){
    // console.log("checking parameter:" +id);
    // let id = document.getElementById("designationId").value;
    let gradeId = document.getElementById("grade").value;
    let url = `${baseUrl}/reimbursements/${id}`;
    // console.log(url);
    // console.log(gradeId);
    // console.log(id);
    let data = {
        grade: {
            id: parseInt(gradeId, 10)
        },
        updatedAt: "2021-10-10"
    };

    let jsonD = JSON.stringify(data);
    // console.log(jsonD);

    let res = fetch(url, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: jsonD
    });

    let resJson = await res
    .then((res => res))
    .then((data) => {
        if(data.status === 200){
            // getLoginUser("user");
            window.location =  "/project1/static/approval.html";
            alert("successful Edited Grade");
        }
        // console.log(data.status);
        // window.location = data;
        
    })
    .catch(error => {
        // console.log(error);
        window.location =  "/project1/static/approval.html";
        alert("Fail to edit grade.");

    });

}

async function getStatusForm(){
console.log(arguments[0]);
console.log(arguments[1]);
    let appId = sessionStorage.setItem("appId", arguments[0])
    let s = sessionStorage.setItem("reId", arguments[1]);

    // console.log(id);
    await fetch(`${baseUrl}/reimbursements/${arguments[1]}`, {
        method: 'GET'
    })
    .then((res => res))
    .then((data) => {
        if(data.status === 200){
            console.log(data);
            // alert("Successful get request");
            window.location =  "/project1/static/status-form.html";

        }
    })

    .catch(err => {
        // console.log(err);
        alert("Fail get request");
        window.location =  "/project1/static/approval.html";

    })

}

async function getFundForm(){
    // console.log(arguments[0]);
    // console.log(arguments[1]);
        let remId = sessionStorage.setItem("remId", arguments[0])
        let rempId = sessionStorage.setItem("rEmpId", arguments[1]);
    
        // console.log(id);
        await fetch(`${baseUrl}/approvals/${arguments[1]}`, {
            method: 'GET'
        })
        .then((res => res))
        .then((data) => {
            if(data.status === 200){
                // console.log(data);
                // alert("Successful get request");
                window.location =  "/project1/static/fund-form.html";
    
            }
        })
        .catch(err => {
            // console.log(err);
            alert("Releasing funds fail");
            window.location =  "/project1/static/approval.html";
    
        })
    
    }

// function convertToTime(d) {
//     d = Number(d);
//     var h = Math.floor(d / 3600);
//     var m = Math.floor(d % 3600 / 60);
//     var s = Math.floor(d % 3600 % 60);

//     var hDisplay = h > 0 ? h + (h == 1 ? " hour, " : " hours, ") : "";
//     var mDisplay = m > 0 ? m + (m == 1 ? " minute, " : " minutes, ") : "";
//     var sDisplay = s > 0 ? s + (s == 1 ? " second" : " seconds") : "";
//     return hDisplay + mDisplay + sDisplay; 
// }

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('/');
}