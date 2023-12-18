function drawGraph(){
    // let data = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100];

    // Lấy thẻ canvas và context
    const canvas = document.getElementById('graph');
    const ctx = canvas.getContext('2d');

    // Khởi tạo đồ thị đường
    myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Dữ liệu',
                data: humidities,
                fill: false,
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 2
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}