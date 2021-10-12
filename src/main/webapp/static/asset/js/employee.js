let baseUrl = 'http://localhost:8080/project1';

async function getEmp(){

    await fetch(`${baseUrl}/employees`, {
        method: 'GET'
    })
    .then(response =>{
      return response.json();
    })
    .then(data => {
        let tbody = document.querySelector("tbody");
        let i = 1;

        while(tbody.firstChild){
            tbody.removeChild(tbody.firstChild);
        }
        //populate table
       
        data.forEach( function(row){
            // console.log(row);
            let tag =  `
                <tr>
                    <th scope="row">${i}</th>
                    <td>${row.firstName}</td>
                    <td>${row.lastName}</td>
                    <td>${row.username}</td>
                    <td>${row.email}</td>
                    <td>${row.designation.title}</td>
                </tr>`;

                i++;
            tbody.innerHTML += tag;

        });

        // console.log(data);
    })
    .catch(err => {
        console.log(err);
    })

}
 
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
    if(userInfo != null){
        let profile = document.getElementById("profile");
        profile.innerHTML = `Welcome, ${userInfo.firstName} ${userInfo.lastName}`;

        getEmp();
    }else{
        window.location = "/project1/static/login.html" 
    }

};