
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
        profile.innerHTML = `Welcome, ${userInfo.firstName} ${userInfo.lastName}`;
        await getReim();
    }else{
        window.location = "/project1/static/login.html" 
    }

};



async function getReim(){

    await fetch(`${baseUrl}/reimbursements`, {
        method: 'GET'
    })
    .then(res =>{
      return res.json();
    })
    .then(data => {
        let tbody = document.querySelector("tbody");
        let i = 1;

        while(tbody.firstChild){
            tbody.removeChild(tbody.firstChild);
        }
        //populate table
       
        data.forEach( function(item){
            console.log(item);
        let tag =  `
            <tr>
                <th scope="row">${i}</th>
                <td>${item.employee.firstName} ${item.employee.lastName}</td>
                <td>${item.event.eventName}</td>
                <td>${item.eventDescription}</td>
                <td>${formatDate(item.eventStart)}</td>
                <td>${item.eventTime}</td>
                <td>${item.eventLocation}</td>
                <td>${item.eventCost}</td>
                <td>${item.grade.gradeLabel === null ? "Pending" : item.grade.gradeLabel}</td> 
                <td> <span style="${item.urgent ? "color: red;" : ""}">${item.urgent == true ? "isUrgent" : "Pending"}</span></td> 

            </tr>`;

                i++;
            tbody.innerHTML += tag;

        });

        console.log(data);
    })
    .catch(err => {
        console.log(err);
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