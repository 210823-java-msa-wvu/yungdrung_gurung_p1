
let baseUrl ="http://localhost:8080/project1"

// let ressid = sessionStorage.getItem("reId");
// console.log("reId : " +ressid);
// let appId = sessionStorage.getItem("appId");
// console.log("appId: "  +appId);

// sessionStorage.removeItem("appId");
// sessionStorage.removeItem("reId");


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

function getInfo(){
    let userInfo = getLoginUser("user")
    if(userInfo != null && userInfo.designation != "Associate/Employee"){
        let profile = document.getElementById("profile");
        profile.innerHTML = `Welcome, ${userInfo.firstName} ${userInfo.lastName}`;
        // await createApproval();
        
    }
    else if(userInfo != null){
        window.location =  "/project1/static/dashboard.html";
    }
    else{
        window.location = "/project1/static/login.html";
    }

};

// async function getStatusForm(){
//     // console.log(arguments[0]);
//     // console.log(arguments[1]);
//         // let appId = sessionStorage.setItem("appId", arguments[0])
//         let s = sessionStorage.getItem("reId");
    
//         // console.log(id);
//         await fetch(`${baseUrl}/reimbursements/${s}`, {
//             method: 'GET'
//         })
//         .then((res => res))
//         .then((data) => {
//             if(data.status === 200){
//                 console.log(data);
//                 // alert("Successful get request");
//                 window.location =  "/project1/static/status-form.html";
    
//             }
//         })
    
//         .catch(err => {
//             // console.log(err);
//             alert("Fail get request");
//             window.location =  "/project1/static/approval.html";
    
//         })
    
//     }


//create


let appId = sessionStorage.getItem("appId");
// if(appId == 0)
// document.getElementById("myBtn").addEventListener("click", createApproval() );
// else
// document.getElementById("myBtn").addEventListener("click", updateApproval() );

// if(appId != 0){

    // async function createApproval(){
    //     let button = document.getElementById("myBtn");
    //     let reId = sessionStorage.getItem("reId");
    //     let userId = getLoginUser("user").id;
    //     let approved = document.getElementById("approved").checked;
    //     console.log(approved);
    //     let reason = document.getElementById("reason").value;
    //     console.log(reason);
    //     let data = {
    //     reason: reason,
    //     employee:{
    //         id: userId,
    //     },
    //     reimbursement:{
    //         id: parseInt(reId, 10),
    //     },
    //     status: approved
    //     };

    //     let jsonD = JSON.stringify(data);
    //     console.log(jsonD);
    //     let url =`${baseUrl}/approvals/${appId}`;
    //     console.log(url);

    //     await fetch(url,{
    //         method: "PUT",
    //         headers: {"Content-Type": "application/json"},
    //         body: jsonD
    //     })
    //     .then(res => res)
    //     .then(data => {
    //         console.log(data.status)
    //         if(data.status === 200){

    //             window.location =  "/project1/static/approval.html"
    //             alert("successful approved");
    //         }
    //         // window.location = data;
    //     })
    //     .catch(error => {
    //         console.log(error);
    //         window.location =  "/project1/static/approval.html"
    //         alert("Fail to approved.")

    //     });

    // }
// }else{
    async function createApproval(){
        let button = document.getElementById("myBtn");
        let reId = sessionStorage.getItem("reId");
        let userId = getLoginUser("user").id;
        console.log(userId);
        let approved = document.getElementById("approved").checked;
        console.log(approved);
        let reason = document.getElementById("reason").value;
        console.log(reason);
        let data = {
        reason: reason,
        employee:{
            id: parseInt(userId),
        },
        reimbursement:{
            id: parseInt(reId, 10),
        },
        status: approved
        };

        let jsonD = JSON.stringify(data);
        console.log(jsonD);
        let url =`${baseUrl}/approvals`;
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

                window.location =  "/project1/static/approval.html"
                alert("successful approved");
            }
            // window.location = data;
        })
        .catch(error => {
            console.log(error);
            window.location =  "/project1/static/approval.html"
            alert("Fail to approved.")

        });

    }
// }
