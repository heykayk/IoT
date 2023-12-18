function getData(){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/time/all-schedule", requestOptions)
        .then(response => response.json())
        .then(result => {
            console.log(result);
            showData(result);
        })
        .catch(error => console.log('error', error));
}
getData();
function showData(data){
    let tmp = ``;
    let stt = 0;
    for(let s of data){
        tmp += `<tr>
                    <td>
                        ${stt++}
                    </td>
                    <td>${s.thoigian}</td>
                    <td>${s.thoiluong} phút</td>
                    <td style="text-align:center;">
                        <a href="/time/delete/${s.id}">Delete</a>
                    </td>
                </tr>`
    }
    document.getElementById("schedule-row").innerHTML = tmp;
}
