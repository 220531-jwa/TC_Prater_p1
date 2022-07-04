baseURL = "http://localhost:8082"

//Home Functions
function logSession()
{
    console.log(sessionStorage.getItem("username"));
    console.log(sessionStorage.getItem("isManager"));
}

function userLogout()
{
    sessionStorage.setItem("uID",null);
    sessionStorage.setItem("username",null);
    sessionStorage.setItem("isManager",null);
    fetch(`${baseURL}/logout`, {method:`POST`, header: {"Content-Type": "application/json"}, body: null});
}

//Login Functions
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
            .then((resp) => 
            {
                console.log(resp); 
                sessionStorage.setItem("uID",resp.uID);
                sessionStorage.setItem("username",resp.username);
                sessionStorage.setItem("isManager",resp.financialManager);
                window.location.assign('homePage.html');
            })

            .catch((error) => {console.log("Halp")});

    }
}

//User Functions 
async function loadUser()
{
    let res = await fetch(`${baseURL}/users/${sessionStorage.getItem("uID")}`, {method: `GET`, 
            header: {"Content-Type": "application/json"}, body: null});
        let resJSON = await res.json()
            .then((resp) => 
            {
                console.log(resp); 
                document.getElementById('userGoesHere').innerHTML += `User ID: ${resp.uID} <br>`;
                document.getElementById('userGoesHere').innerHTML += `Username: ${resp.username} <br>`;
                document.getElementById('userGoesHere').innerHTML += `Password: ${resp.passkey} <br>`;
                document.getElementById('userGoesHere').innerHTML += `Personal Name: ${resp.pers_name} <br>`;
                document.getElementById('userGoesHere').innerHTML += `Family Name: ${resp.fam_name} <br>`;
                document.getElementById('userGoesHere').innerHTML += `Manager Permissions: ${resp.financialManager} <br>`;
            })

            .catch((error) => {console.log("Halp")});
}

//Request Functions
function hideMReqs()
{
    if (sessionStorage.getItem("isManager") == "false") 
    {
        document.getElementById('mReqH').setAttribute('style','display:none');
        document.getElementById('aReqH').setAttribute('style','display:none');
    }
    else if (sessionStorage.getItem("isManager") == "true") 
    {
        document.getElementById('mReqH').setAttribute('style','display:inline');
        document.getElementById('aReqH').setAttribute('style','display:inline');
    }
}

function requestIntoDiv(inputJson, outputDiv)
{
    outputDiv.innerHTML += `RequestID: ${inputJson.requestID}, UserID: ${inputJson.userID}, `; 
    outputDiv.innerHTML += `FinanceID: ${inputJson.financeID}, Status Code: ${inputJson.statusCode} <br>`;
    outputDiv.innerHTML += `Event Cost: ${inputJson.eventCost}, Requested Amount: ${inputJson.reqAmount}, `;
    outputDiv.innerHTML += `Approved Amount: ${inputJson.approvedAmount}, Amount Exceeded: ${inputJson.amountExceeded} <br>`;
    outputDiv.innerHTML += `Description: ${inputJson.description} <br> Location: ${inputJson.location}, `;
    outputDiv.innerHTML += `Proof Type: ${inputJson.proofType}, Proof Grade: ${inputJson.proofGrade}, `;
    outputDiv.innerHTML += `Event Type: ${inputJson.event_Type} <br> Justification: ${inputJson.justification} <br>`;
    outputDiv.innerHTML += `Finance Comment: ${inputJson.financeComment} <br> Event Date: ${inputJson.eventDate} <br>`;
    outputDiv.innerHTML += `Open Date: ${inputJson.openDate} <br> Close Date: ${inputJson.closeDate}<br>`;
    outputDiv.innerHTML += `<button type="button" onclick="getSingleRequest(${inputJson.requestID})" id="gSRb">Focus</button>`;
    
    return outputDiv;
}

function getBlankRequest()
{
    let blankRequest = new Object();
    blankRequest.requestID=0;
    blankRequest.userID=0;
    blankRequest.financeID=0;
    blankRequest.statusCode=0;
    blankRequest.eventCost=0;
    blankRequest.reqAmount=0;
    blankRequest.approvedAmount=0;
    blankRequest.amountExceeded=0;
    blankRequest.description="";
    blankRequest.location="";
    blankRequest.proofType="";
    blankRequest.proofGrade="";
    blankRequest.event_Type="";
    blankRequest.justification="";
    blankRequest.financeComment="";
    blankRequest.eventDate=null;
    blankRequest.openDate=null;
    blankRequest.closeDate=null;
    return blankRequest;
}

function requestIntoJson()
{
    let reqJSON = new Object();
    reqJSON.requestID = document.getElementById('rID').value;
    reqJSON.userID = document.getElementById('uID').value;
    reqJSON.financeID = document.getElementById('fID').value;
    reqJSON.statusCode = document.getElementById('sCode').value;
    reqJSON.eventCost = document.getElementById('eCost').value;
    reqJSON.reqAmount = document.getElementById('rAm').value;
    reqJSON.approvedAmount = document.getElementById('aAm').value;
    reqJSON.amountExceeded = document.getElementById('aEx').value;
    reqJSON.description = document.getElementById('description').value;
    reqJSON.location = document.getElementById('location').value;
    reqJSON.proofType = document.getElementById('pType').value;
    reqJSON.proofGrade = document.getElementById('pGr').value;
    reqJSON.event_Type = document.getElementById('eType').value;
    reqJSON.justification = document.getElementById('justification').value;
    reqJSON.financeComment = document.getElementById('fCom').value;
    // reqJSON.eventDate = document.getElementById('eDate').valueAsDate;
    // reqJSON.openDate = document.getElementById('oDate').valueAsDate;
    // reqJSON.closeDate = document.getElementById('cDate').valueAsDate;
    reqJSON.eventDate = document.getElementById('eDate').value;
    reqJSON.openDate = document.getElementById('oDate').value;
    console.log(reqJSON.openDate);
    if (!reqJSON.openDate[0]==1 && !reqJSON.openDate[0]==2) {reqJSON.openDate="";}
    reqJSON.closeDate = document.getElementById('cDate').value;
    if (reqJSON.closeDate.includes(":")==false || reqJSON.closeDate==undefined) {reqJSON.closeDate="";}
    return JSON.stringify(reqJSON);
}

function saveRequestForm() {sessionStorage.setItem('singleRequest', requestIntoJson());}

function clearSavedRequest() {sessionStorage.setItem('singleRequest', null);}

function padDateNumbers(obj)
{
    console.log(obj);
    if (obj!=null)
    {
        if (!obj.includes(":"))
        {
            for (let i = 1;i<6;i++)
            {
                if (obj[i]<10) {obj[i] = `0${obj[i]}`;}
            }
        }
    }
    return obj;
}

function chromeDateConversion(reqInput) 
{
    reqInput.eventDate = padDateNumbers(reqInput.eventDate);
    reqInput.openDate = padDateNumbers(reqInput.openDate);
    reqInput.closeDate = padDateNumbers(reqInput.closeDate);
    if (!reqInput.eventDate.includes(":"))
    {
        reqInput.eventDate = `${reqInput.eventDate[0]}-${reqInput.eventDate[1]}-${reqInput.eventDate[2]}T${reqInput.eventDate[3]}:${reqInput.eventDate[4]}`;
    }
    if (reqInput.openDate!=null && !reqInput.openDate.includes(":"))
    {
        reqInput.openDate = `${reqInput.openDate[0]}-${reqInput.openDate[1]}-${reqInput.openDate[2]}T${reqInput.openDate[3]}:${reqInput.openDate[4]}`;
    }
    if (reqInput.closeDate!=null && !reqInput.closeDate.includes(":"))
    {
        reqInput.closeDate = `${reqInput.closeDate[0]}-${reqInput.closeDate[1]}-${reqInput.closeDate[2]}T${reqInput.closeDate[3]}:${reqInput.closeDate[4]}`;
    }
    return reqInput;
}

function triggerPopulate() 
{
    let tempRequest = JSON.parse(sessionStorage.getItem('singleRequest'));
    if (tempRequest==null) {tempRequest = getBlankRequest();}

    { //preferably don't run this in firefox as it's not needed, trying to figure out how to detect specific browsers is taking too long
        tempRequest = chromeDateConversion(tempRequest);
        console.log("Attempted conversion");
    }

    let reqDiv = populateRequestForm(tempRequest,document.createElement('form'));
    document.getElementById('requestGoesHere').innerHTML = "";
    document.getElementById('requestGoesHere').appendChild(reqDiv);

    document.getElementById('description').value = tempRequest.description; //textareas don't have value="" tag functionality
    document.getElementById('justification').value = tempRequest.justification;
    document.getElementById('fCom').value = tempRequest.financeComment;

    document.getElementById('sCode').selectedIndex = (tempRequest).statusCode; //datetime inputs are picky eaters
    document.getElementById('pType').selectedIndex = (tempRequest).proofType;
    document.getElementById('eType').selectedIndex = (tempRequest).event_Type;

    

    if (tempRequest.eventDate==null) {;}
    else if (tempRequest.eventDate.length==7) //datetime objects are read as arrays
    {
        // let tempDate = new Date(); //worked for firefox, not for Chrome
        // tempDate.setFullYear(tempRequest.eventDate[0]); tempDate.setMonth(tempRequest.eventDate[1]-1); tempDate.setDate(tempRequest.eventDate[2]);
        // tempDate.setHours(tempRequest.eventDate[3], tempRequest.eventDate[4], 0, 0);
        // document.getElementById('eDate').valueAsDate = tempDate;
        // tempDate.setFullYear(tempRequest.openDate[0]); tempDate.setMonth(tempRequest.openDate[1]-1); tempDate.setDate(tempRequest.openDate[2]);
        // tempDate.setHours(tempRequest.openDate[3], tempRequest.openDate[4], 0, 0);
        // document.getElementById('oDate').valueAsDate = tempDate;
        // tempDate.setFullYear(tempRequest.closeDate[0]); tempDate.setMonth(tempRequest.closeDate[1]-1); tempDate.setDate(tempRequest.closeDate[2]);
        // tempDate.setHours(tempRequest.closeDate[3], tempRequest.closeDate[4], 0, 0);
        // document.getElementById('cDate').valueAsDate = tempDate;
        let clockSlow = document.createElement('div');
        // clockSlow.innerHTML = `Timezone Offset (Minutes): ${tempDate.getTimezoneOffset()}`;
        document.getElementById('requestGoesHere').appendChild(clockSlow);
    }
    else if (tempRequest.eventDate.length>7) //datetime strings are longer than 7
    {
        // let tempDate = new Date()
        let clockSlow = document.createElement('div');
        // clockSlow.innerHTML = `Timezone Offset (Minutes): ${tempDate.getTimezoneOffset()}`;
        // tempDate.setTime(Date.parse(tempRequest.eventDate));
        // document.getElementById('eDate').valueAsDate = tempDate;
        // tempDate.setTime(Date.parse(tempRequest.openDate));
        // document.getElementById('oDate').valueAsDate = tempDate;
        // tempDate.setTime(Date.parse(tempRequest.closeDate));
        // document.getElementById('cDate').valueAsDate = tempDate;
        // document.getElementById('eDate').valueAsDate=tempRequest.eventDate;
        // document.getElementById('oDate').valueAsDate=tempRequest.openDate;
        // document.getElementById('cDate').valueAsDate=tempRequest.closeDate;
        document.getElementById('requestGoesHere').appendChild(clockSlow);
    }
}

function populateRequestForm(inputJson, outputDiv)
{
    console.log(inputJson);
    outputDiv.innerHTML += `<label>RequestID: <input type="number" step="1" min="0" id="rID" value="${inputJson.requestID}"></label><br>`;
    outputDiv.innerHTML += `<label>UserID: <input type="number" step="1" min="0" id="uID" value="${inputJson.userID}"></label><br>`;
    outputDiv.innerHTML += `<label>FinanceID: <input type="number" step="1" min="0" id="fID" value="${inputJson.financeID}"></label><br>`;
    outputDiv.innerHTML += `<label for="sCode">Status Code: </label> 
                                <select name="sCode" id="sCode" value="${inputJson.statusCode}">
                                    <option value="0">Unsent</option>
                                    <option value="1">Pending</option>
                                    <option value="2">Please Resend/Update</option>
                                    <option value="3">Please Confirm</option>
                                    <option value="4">Accepted</option>
                                    <option value="5">Rejected</option>
                                    <option value="6">Withdrawn</option>
                                    <option value="7">Closed</option>
                                </select><br>`;
    outputDiv.innerHTML += `<label>Event Cost: <input type="number" step="0.01" min="0" id="eCost" value="${inputJson.eventCost}"></label><br>`;
    outputDiv.innerHTML += `<label>Request Amount: <input type="number" step="0.01" min="0" id="rAm" value="${inputJson.reqAmount}"></label><br>`;
    outputDiv.innerHTML += `<label>Approved Amount: <input type="number" step="0.01" min="0" id="aAm" value="${inputJson.approvedAmount}"></label><br>`;
    outputDiv.innerHTML += `<label>Amount Exceeded: <input type="number" step="0.01" min="0" id="aEx" value="${inputJson.amountExceeded}"></label><br>`;
    //strings start here
    outputDiv.innerHTML += `<label>Description: <textarea id="description" value="${inputJson.description}"></textarea></label><br>`;
    outputDiv.innerHTML += `<label>Location: <input type="text" id="location" value="${inputJson.location}"></label><br>`;
    outputDiv.innerHTML += `<label for="pType">Event Passing Requirement: </label> 
                                <select name="pType" id="pType" value="${inputJson.proofType}">
                                    <option value="0">Auto-Pass</option>
                                    <option value="1">Certification</option>
                                    <option value="2">Pass/Fail</option>
                                    <option value="3">Letter Grade At Least D-</option>
                                    <option value="4">Letter Grade At Least C-</option>
                                    <option value="5">Other: Disscuss With Boss</option>
                                </select><br>`;
    outputDiv.innerHTML += `<label>Event Grade/Pass Proof: <input type="text" id="pGr" value="${inputJson.proofGrade}"></label><br>`;
    outputDiv.innerHTML += `<label for="eType">Event Type: </label> 
                                <select name="eType" id="eType" value="${inputJson.event_Type}">
                                    <option value="0">University Course</option>
                                    <option value="1">Seminar</option>
                                    <option value="2">Certification Prep Class</option>
                                    <option value="3">Certification Actual</option>
                                    <option value="4">Technical Training</option>
                                    <option value="5">Other</option>
                                </select><br>`;
    outputDiv.innerHTML += `<label>Justification: <textarea id="justification" value="${inputJson.justification}"></textarea></label><br>`;
    outputDiv.innerHTML += `<label>Finance Comment: <textarea id="fCom" value="${inputJson.financeComment}"></textarea></label><br>`;
    //dates start here
    outputDiv.innerHTML += `<label>Event Date: <input type="datetime-local" id="eDate" value="${inputJson.eventDate}"></label><br>`;
    outputDiv.innerHTML += `<label>Open Date: <input type="datetime-local" id="oDate" value="${inputJson.openDate}"></label><br>`;
    outputDiv.innerHTML += `<label>Close Date: <input type="datetime-local" id="cDate" value="${inputJson.closeDate}"></label><br>`;
    outputDiv.innerHTML += `<input type="submit" onclick="submitRequestForm()"><input type="reset">`;
    outputDiv.innerHTML += `<button onclick="saveRequestForm()" id="svBttn" type="button">Save</button>`;

    return outputDiv;
}

async function getSingleRequest(rID)
{
    let res = await fetch(`${baseURL}/users/${sessionStorage.getItem("uID")}/requests/${rID}`, {method: `GET`, 
        header: {"Content-Type": "application/json"}, body: null});
        let resJSON = await res.json()
        .then((resp) =>
        {
            console.log(resp);
            window.location.assign('requestSinglePage.html');
            // populateRequestForm(resp,document.getElementById('requestGoesHere'));
            sessionStorage.setItem("rID", rID);
            sessionStorage.setItem("singleRequest",JSON.stringify(resp));
        })
        .catch((error) => {console.log(error)});
}

async function getRequestsUser()
{
    let res = await fetch(`${baseURL}/users/${sessionStorage.getItem("uID")}/requests`, {method: `GET`, 
        header: {"Content-Type": "application/json"}, body: null});
        let resJSON = await res.json()
        .then((resp) =>
        {
            console.log(resp);
            document.getElementById('uReqList').innerHTML='';
            for (let i = 0;i<resp.length;i++)
            {
                let newDiv = requestIntoDiv(resp[i], document.createElement('div'));
                document.getElementById('uReqList').appendChild(newDiv);
            }
        })
        .catch((error) => {console.log(error)});
}

async function getRequestsManager()
{
    if (sessionStorage.getItem("isManager"))
    {
        let res = await fetch(`${baseURL}/managers/${sessionStorage.getItem("uID")}/requests`, {method: `GET`, 
            header: {"Content-Type": "application/json"}, body: null});
            let resJSON = await res.json()
            .then((resp) =>
            {
                console.log(resp);
                document.getElementById('mReqList').innerHTML='';
                for (let i = 0;i<resp.length;i++)
                {
                    let newDiv = requestIntoDiv(resp[i], document.createElement('div'));
                    document.getElementById('mReqList').appendChild(newDiv);
                }
            })
            .catch((error) => {console.log(error);});
    }
}

async function getRequestsAll()
{
    let res = await fetch(`${baseURL}/users/0/requests`, {method: `GET`, 
        header: {"Content-Type": "application/json"}, body: null});
        let resJSON = await res.json()
        .then((resp) =>
        {
            console.log(resp);
            document.getElementById('aReqList').innerHTML='';
            for (let i = 0;i<resp.length;i++)
            {
                let newDiv = requestIntoDiv(resp[i], document.createElement('div'));
                document.getElementById('aReqList').appendChild(newDiv);
            }
        })
        .catch((error) => {console.log(error)});
}

async function submitRequestForm()
{
    saveRequestForm();
    if (sessionStorage.getItem('singleRequest') != null && sessionStorage.getItem("uID")>0) //need some way to differentiate user vs manager
    {
        let reqToSend = JSON.parse(sessionStorage.getItem('singleRequest'));
        console.log("a1");
        if (reqToSend.openDate==null || reqToSend.openDate=="") //new form, open date can be set to read-only with more dev time
        {
            console.log("a2");
            reqToSend.openDate = new Date();
            let localUID = sessionStorage.getItem("uID");
            let res = await fetch(`${baseURL}/users/${sessionStorage.getItem("uID")}/requests/1`, {method: `POST`,  
                header: {"Content-Type": "application/json"}, body: JSON.stringify(reqToSend)});
            let resJSON = await res.json()
            .then((resp) => 
            {
                console.log(resp); //re-load submitted request into form for user confirmation
                sessionStorage.setItem('singleRequest',JSON.stringify(resp));
                console.log(sessionStorage.getItem('singleRequest'));
                triggerPopulate();
            })
            .catch((error) => {console.log(error);});

        }
        //edit-as-user. Backend doesn't see any difference between this and edit-as-manager yet
        else if (reqToSend.userID == sessionStorage.getItem('uID'))
        {
            console.log("a3");
            let res = await fetch(`${baseURL}/users/${sessionStorage.getItem("uID")}/requests/${reqToSend.requestID}`, {method: `PUT`,  
                header: {"Content-Type": "application/json"}, body: JSON.stringify(reqToSend)});
            console.log("a3.0");
            let resJSON = await res.json()
            .then((resp) => 
            {
                console.log("a3.1");
                console.log(resp); //re-load submitted request into form for user confirmation
                console.log("a3.2");
                sessionStorage.setItem('singleRequest',JSON.stringify(resp));
                console.log("a3.3");
                console.log(sessionStorage.getItem('singleRequest'));
                triggerPopulate();
            })
            .catch((error) => {console.log(error);});
        }
        //edit-as-manager. A manager editing their own request should edit as user
        else if (reqToSend.financeID == sessionStorage.getItem('uID'))
        {
            console.log("a4");
            let res = await fetch(`${baseURL}/managers/${sessionStorage.getItem("uID")}/requests/${reqToSend.requestID}`, {method: `PUT`,  
                header: {"Content-Type": "application/json"}, body: JSON.stringify(reqToSend)});
            let resJSON = await res.json()
            .then((resp) => 
            {
                console.log(resp); //re-load submitted request into form for user confirmation
                sessionStorage.setItem('singleRequest',JSON.stringify(resp));
                console.log(sessionStorage.getItem('singleRequest'));
                triggerPopulate();
            })
            .catch((error) => {console.log(error);});
        }
    }
}

/* Status Code Reference: 
    <select name="sCode" id="sCode" value="${inputJson.statusCode}">
        <option value="0">Unsent</option>
        <option value="1">Pending</option>
        <option value="2">Please Resend/Update</option>
        <option value="3">Please Confirm</option>
        <option value="4">Accepted</option>
        <option value="5">Rejected</option>
        <option value="6">Withdrawn</option>
        <option value="7">Closed</option>
    </select>
*/