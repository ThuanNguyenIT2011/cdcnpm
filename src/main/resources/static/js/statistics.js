const ctx = $('#myChart');

labels = []
data = []

$(document).ready(function() {
	$.ajax({
        type: "GET",
        url: "/staff/getLengthStaff",
        dataType: 'text',
        success: function (data) {
			$('#totalStaff').text(data);
        },
        error: function (e) {
        }
    });
    
    $.ajax({
        type: "GET",
        url: "/ingredints/check_quantity",
        dataType: 'text',
        success: function (data) {
			$('#notify').text(data);
        },
        error: function (e) {
        }
    });
	
	$.ajax({
		type: "GET",
		url: "/products/statisticDay",
		type: "GET",
		dataType: "json",
		success: function(data) {
			labels = data.map(ele => ele.nameProduct);
			dataQuantity = data.map(ele => ele.quantity);
			dataTotalPrice = data.map(ele => ele.totalPrice);
			
			dailySale = data.reduce((sum, ele) => sum + (ele.quantity * ele.totalPrice), 0);
			$('#dailySales').text(dailySale);
			
			$('#totalProduct').text(data.length);
			
			const data1 = {
				labels: labels,
				datasets: [{
					label: 'Quantity',
					data: dataQuantity,
					backgroundColor: [
						'rgba(255, 26, 104, 0.2)'
					],
					borderColor: [
						'rgba(0, 0, 0, 1)'
					],
					borderWidth: 1,
					yAxisID: 'y'
				}, {
					label: 'Total Price',
					data: dataTotalPrice,
					backgroundColor: [
						'rgba(0, 0, 0, 0.2)'
					],
					borderColor: [
						'rgba(0, 0, 0, 1)'
					],
					type: 'line',
					yAxisID: 'price'
				}]
			};

			// config 
			const config = {
				type: 'bar',
				data: data1,
				options: {
					scales: {
						y: {
							beginAtZero: true,
							type: 'linear',
							position: 'left',
							grid: {
								display: false
							}
						},
						price: {
							beginAtZero: true,
							type: 'linear',
							position: 'right'
						}
					}
				}
			};

			// render init block
			const myChart = new Chart(
				document.getElementById('myChart'),
				config
			);

			// Instantly assign Chart.js version
			const chartVersion = document.getElementById('chartVersion');
			chartVersion.innerText = Chart.version;


		},
		error: function(e) {

		}
	});
});




