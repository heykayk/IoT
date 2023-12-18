function setStatusPump(statusPump){
    console.log("den day roii nhes !!!");
    var checkbox = document.getElementById("flexSwitchCheckDefault").checked;
    if(checkbox !== statusPump){
        checkbox.checked = statusPump;
    }
    document.getElementById("status-pump").innerHTML = statusPump?"ON":"OFF";
    // console.log("trạng thái của đèn là: " + statusPump);
}

function sendStatus(){
    let checkbox = document.getElementById("flexSwitchCheckDefault").checked;
    let data = checkbox?"ON":"OFF";
    document.getElementById("status-pump").innerHTML = checkbox?"ON":"OFF";
    stompClient.send("/app/data.humidity-percentage", {}, JSON.stringify(data))
}