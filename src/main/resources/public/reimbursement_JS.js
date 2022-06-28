baseURL = "http://localhost:8082"
function hidePass()
{
    if (document.getElementById('pass').getAttribute("type")=="password") {document.getElementById('pass').setAttribute("type","text");}
    else {document.getElementById('pass').setAttribute("type","password");}
}

async function userLogin() 
{
    let uname = document.getElementById('uname').value; 
    let passkey = document.getElementById('pass').value;

    if (uname != "" && passkey != "")
    {
        let loginAttempt = 
        {
            username: uname, 
            passkey: passkey
        };
        let attemptJSON = JSON.stringify(loginAttempt);
        console.log(attemptJSON);

        let res = await fetch(`${baseURL}/login`, {method: `POST`, header: {"Content-Type": "application/json"}, body: attemptJSON});
        let resJSON = await res.json()
            .then((resp) => {console.log(resp)})

            .catch((error) => {console.log(error)});

    }
}