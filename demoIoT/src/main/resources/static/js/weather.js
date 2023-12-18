function getWeather(){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/weather", requestOptions)
        .then(response => response.json())
        .then(result => {
            document.getElementById("temperature-result").innerHTML = Math.round(result.temp);
            document.getElementById("humidity-result").innerHTML = Math.round(result.humidity);
            console.log(result);
        })
        .catch(error => console.log('error', error));
}
getWeather();

