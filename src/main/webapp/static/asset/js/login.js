
    //  tag_alert = document.getElementsByClassName("alert")[0];
    //  msg = document.getElementById("alert-msg");
    //  tag_alert.classList.remove("hide");
    //  tag_alert.classList.add(" alert-success show ");
    //  msg.innerHTML = "Login Credentials invalid.";
    //  tag_alert.classList.remove("hide");
    //     tag_alert.classList.add("alert-success show ");
    //     msg.innerHTML= "Login Successfully";

async function login(){

    let url = 'http://localhost:8080/project1/login';

    let user = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    let res = fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(user)
    });

    let resJson = await res
    .then((res => res.url))
    .then((data) => {
        console.log(data);
        window.location = data;
        
    })
    .catch(error => {
      
        console.log(error);
    });

}

