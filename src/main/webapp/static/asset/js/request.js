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
    if(userInfo != null){
        let profile = document.getElementById("profile");
        // let inputUser = document.getElementById("employee_id");
        profile.innerHTML = `Welcome, ${userInfo.firstName} ${userInfo.lastName}`;
        // inputUser.value = userInfo.id;
        await getEvents();
    }else{
        window.location = "/project1/static/login.html" 
    }

};

async function getEvents(){
    let url=`${baseUrl}/events`;
    // console.log(url);
 
    await fetch(url, {
        method: 'GET'
    })
    .then(response =>{
        return response.json();
    })
    .then(data => {
        let selectTag = document.querySelector("select");
        let optionTag = document.createElement("option");
        data.forEach( function(eTag){
            // console.log(eTag);
             optionTag += ` <option class="" value= "${eTag.id}">${eTag.eventName} </option>`;
            
        });
        selectTag.innerHTML = optionTag;
        // console.log(data);
    })
    .catch(err => {
        console.log(err);
    })
    
};



async function postRequest(){

    let url ='http://localhost:8080/project1/reimbursements';

   let user =  getLoginUser("user");
   let event_id = document.getElementById("event").value;

    // console.log(`${user.id}`);
    // console.log(event_id);
    let request = {
        eventStart: document.getElementById("event-date").value,
        eventTime: document.getElementById("event-time").value,
        eventLocation: document.getElementById("location").value,
        event: {
            id: parseInt(event_id, 10)
        },
        eventDescription:  document.getElementById("description").value,
        eventCost: parseFloat(document.getElementById("event-cost").value),
        grade: {
           id : null
        },
        employee : {
            id: user.id
        }
    };
   
    // console.log(request);

    let stringify = JSON.stringify(request);
    console.log(stringify);


    await fetch(url, {
        method:"POST",
        headers: {"Content-Type": "application/json"},
        body: stringify
    })
    .then((res => res))
    .then((data) => {
        if(data.status === 200){
            // getLoginUser("user");
            window.location =  "/project1/static/reimbursement.html";
            alert("successful Request");
        }
        // console.log(data.status);
        // window.location = data;
        
    })
    
    .catch(error => {
        console.log(error);
        window.location =  "/project1/static/request.html";
        alert("Fail Request");


    });

}



// let request = {
//     event_id:document.getElementById("event").value,
//     startEvent: document.getElementById("event-date").value,
//     eventTime: document.getElementById("event-time").value,
//     eventLocation: document.getElementById("location").value,
//     eventCost: document.getElementById("event-cost").value,
//     description: document.getElementById("description").value
// };

