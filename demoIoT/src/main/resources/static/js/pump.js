function sendStatus(){
    let checkbox = document.getElementById("flexSwitchCheckDefault").checked;
    let data = checkbox?"ON":"OFF";
    document.getElementById("status-pump").innerHTML = checkbox?"ON":"OFF";
    stompClient.send("/app/data.humidity-percentage", {}, JSON.stringify(data))
}