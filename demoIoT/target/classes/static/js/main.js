var stompClient;
var socket = new SockJS("/data-socket");
var myChart;
const humidities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
const labels = ['loading', 'loading', 'loading', 'loading', 'loading', 'loading', 'loading', 'loading', 'loading', 'loading'];
stompClient = Stomp.over(socket);
stompClient.connect({}, onConnected, onError);

function onConnected()
{
    //Subcrible đến topic /topic/data-receive để lấy dữ liệu mới nhất
    stompClient.subscribe("/topic/data-receive", onNewDataReceived);
}
function onNewDataReceived(payload)
{
    var newData = JSON.parse(payload.body);
    humidities.shift();
    labels.shift();

    labels.push(newData.time.substring(11));
    humidities.push(newData.humidityPercentage);

    setStatusPump(newData.status);
    myChart.update();
}
function onError()
{
    alert("Không thể kết nối tới WebSocket, vui lòng kiểm lại kết nối mạng");
}



function  getData(){
    drawGraph();
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/get-ten-result", requestOptions)
        .then(response => response.json())
        .then(result => {
            // console.log(result);
            if(result != null){
                for(let i=0; i<10; i++){
                    humidities[i] = result[i].humidityPercentage;
                    labels[i] = result[i].time.substring(11);
                }
            }
            setStatusPump(result[9].status);
            updateChart();
        })
        .catch(error => console.log('error', error));
}

function updateChart(){
    myChart.update()
}


getData();