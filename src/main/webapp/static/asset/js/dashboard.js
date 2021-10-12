
let baseUrl ="http://localhost:8080/project1"


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
        await getAllFunds();
    }else{
        window.location = "/project1/static/login.html" 
    }

};



async function getAllFunds(){
    await fetch(`${baseUrl}/funds`, {
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
                <td>${item.initialFund}</td>
                <td>${item.allocatedFund}</td>
                <td>${item.remainingFund}</td>
                <td>${formatDate(item.updatedAT)}</td>
                <td>${item.status == true? "Approved": "pending"}</td>
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