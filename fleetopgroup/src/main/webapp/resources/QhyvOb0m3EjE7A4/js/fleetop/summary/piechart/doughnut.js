$(function(){
	
    //get the doughnut chart canvas
	var ctx = document.getElementById('doughnut-chartcanvas-1').getContext("2d");

	  var gradientStrokeBlue = ctx.createLinearGradient(0, 0, 0, 181);
      gradientStrokeBlue.addColorStop(0, 'rgba(54, 215, 232, 1)');
      gradientStrokeBlue.addColorStop(1, 'rgba(177, 148, 250, 1)');
      var gradientLegendBlue = 'linear-gradient(to right, rgba(54, 215, 232, 1), rgba(177, 148, 250, 1))';

      var gradientStrokeRed = ctx.createLinearGradient(0, 0, 0, 50);
      gradientStrokeRed.addColorStop(0, 'rgba(255, 191, 150, 1)');
      gradientStrokeRed.addColorStop(1, 'rgba(254, 112, 150, 1)');
      var gradientLegendRed = 'linear-gradient(to right, rgba(255, 191, 150, 1), rgba(254, 112, 150, 1))';

      var gradientStrokeGreen = ctx.createLinearGradient(0, 0, 0, 300);
      gradientStrokeGreen.addColorStop(0, 'rgba(6, 185, 157, 1)');
      gradientStrokeGreen.addColorStop(1, 'rgba(132, 217, 210, 1)');
      var gradientLegendGreen = 'linear-gradient(to right, rgba(6, 185, 157, 1), rgba(132, 217, 210, 1))'; 
      
      var gradientStrokeYellow = ctx.createLinearGradient(0, 0, 0, 300);
      gradientStrokeYellow.addColorStop(0, 'rgba(178,238,174,1)');
      gradientStrokeYellow.addColorStop(1, 'rgba(167,233,148,1)');
      var gradientLegendYellow = 'linear-gradient(to right, rgba(178,238,174,1), rgba(167,233,148,1))'; 

     var trafficChartData = {
        datasets: [{
          backgroundColor: [
            gradientStrokeBlue,
            gradientStrokeGreen,
            gradientStrokeRed,
            gradientStrokeYellow
          ],
          hoverBackgroundColor: [
            gradientStrokeBlue,
            gradientStrokeGreen,
            gradientStrokeRed,
            gradientStrokeYellow
          ],
          borderColor: [
            gradientStrokeBlue,
            gradientStrokeGreen,
            gradientStrokeRed,
            gradientStrokeYellow
          ],
          
          borderWidth: [1, 1, 1, 1]
        }],
    
        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: [
          'Open',
          'In Process',
          'Hold',
          'Close'
        ]
      };


     var options = {
    	        responsive: true,
    	        title: {
    	            display: true,
    	            position: "top",
    	            text: "Work Order Status",
    	            fontSize: 18,
    	            fontColor: "#111"
    	        },
    	        legend: {
    	            display: true,
    	            position: "bottom",
    	            labels: {
    	                fontColor: "#333",
    	                fontSize: 15
    	            }
    	        }
    	    };

    //create Chart class object
    var chart1 = new Chart(ctx1, {
        type: "doughnut",
        data: trafficChartData,
        options: options
    });
	
	
    
});