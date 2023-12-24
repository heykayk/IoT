function getHistory(){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/time/history", requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log(result);
            showHistory(result);
        })
        .catch(error => console.log('error', error));
}
getHistory();
function showHistory(data){
    let tmp = ``;
    let stt = 1;
    for(let s of data){
        tmp += `<tr>
                    <td>
                        ${stt++}
                    </td>
                    <td>${s.thoiluong} phút</td>
                    <td>${s.thoigian}</td>


                </tr>`
    }
    document.getElementById("history").innerHTML = tmp;
}
