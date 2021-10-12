
async function logout(){
    let cookie = document.cookie;
    let userId = getLoginUser("user").id;
    console.log(userId);
    // console.log(cookie);


    let url = 'http://localhost:8080/project1/logout';

  
    let res = fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(userId)
    });

     let result = await res
    .then((res => res.url))
    .then((data) => {
        console.log(data);
        console.log("successfully logout");
        
        if(cookie != null) {
            deleteAllCookies();

        }
        cookie = "user=; expires=Thu, 01 Jan 1970 00:00:00 UTC";

        window.location = data;

        
    })
    .catch(error => {

        console.log(error);
        console.log("failed to logout");
    });

}

function deleteAllCookies(){
    var Cookies = document.cookie.split(";");
    
    for(var i = 0; i < Cookies.length; i++){

    let Cookie = Cookies[i];

    var eqpos = Cookie.indexOf("=");

    let name = eqpos > -1? Cookie.substring(0,eqpos ): Cookie;

        document.Cookie = `user=;expires=${new Date(0).toUTCString()}`;
    
    }
}