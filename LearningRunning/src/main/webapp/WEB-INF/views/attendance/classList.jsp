<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/kims/header.jsp"%>
<%@ include file="/WEB-INF/views/include/kims/sidebar.jsp"%>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
       	출결 관리
        <small>반별 &middot; 개별 출석율 확인 및 관리</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">출결 관리</a></li>
        <li class="active">반별 &middot; 개별 출석율 확인 및 관리</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-md-6">
        
          <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">과정(학급)별 출석율</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table class="table table-bordered">
                <tr>
                  <th style="width: 10px">#</th>
                  <th>과정(학급)명</th>
                  <th>과정 진행율</th>
                  <th style="width: 60px">출석율</th>
                </tr>
                <tr>
                  <td>1.</td>
                  <td>2016 사물인터넷 3기</td>
                  <td>
                    <div class="progress progress-xs">
                      <div class="progress-bar progress-bar-danger" style="width: 80%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-red">80%</span></td>
                </tr>
                <tr>
                  <td>2.</td>
                  <td>2017 사물인터넷 1기</td>
                  <td>
                    <div class="progress progress-xs">
                      <div class="progress-bar progress-bar-yellow" style="width: 50%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-yellow">85%</span></td>
                </tr>
                <tr>
                  <td>3.</td>
                  <td>2017 사물인터넷 2기</td>
                  <td>
                    <div class="progress progress-xs progress-striped active">
                      <div class="progress-bar progress-bar-primary" style="width: 30%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-light-blue">90%</span></td>
                </tr>
                <tr>
                  <td>4.</td>
                  <td>2017 사물인터넷 3기</td>
                  <td>
                    <div class="progress progress-xs progress-striped active">
                      <div class="progress-bar progress-bar-success" style="width: 10%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-green">95%</span></td>
                </tr>
              </table>
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix">
              <ul class="pagination pagination-sm no-margin pull-right">
                <li><a href="#">&laquo;</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">&raquo;</a></li>
              </ul>
            </div>
          </div>
          <!-- /.box -->

 
      </div>
      
      
        <!-- /.col (LEFT) -->
        <div class="col-md-6">
          <!-- LINE CHART -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Line Chart</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body">
              <div class="chart">
                <canvas id="lineChart" style="height:250px"></canvas>
              </div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->



        </div>
        <!-- /.col (RIGHT) -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->


<!-- jQuery 2.2.3 -->
<script src="<%=request.getContextPath() %>/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<!-- ChartJS 1.0.1 -->
<script src="<%=request.getContextPath() %>/plugins/chartjs/Chart.min.js"></script>
<!-- FastClick -->
<script src="<%=request.getContextPath() %>/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=request.getContextPath() %>/dist/js/demo.js"></script>
<!-- page script -->

<script>
  $(function () {

	    var areaChartData = {
	      labels: ["January", "February", "March", "April", "May", "June", "July"],
	      datasets: [
	        {
	          label: "Electronics",
	          fillColor: "rgba(210, 214, 222, 1)",
	          strokeColor: "rgba(210, 214, 222, 1)",
	          pointColor: "rgba(210, 214, 222, 1)",
	          pointStrokeColor: "#c1c7d1",
	          pointHighlightFill: "#fff",
	          pointHighlightStroke: "rgba(220,220,220,1)",
	          data: [65, 59, 80, 81, 56, 55, 40]
	        },
	        {
	          label: "Digital Goods",
	          fillColor: "rgba(60,141,188,0.9)",
	          strokeColor: "rgba(60,141,188,0.8)",
	          pointColor: "#3b8bba",
	          pointStrokeColor: "rgba(60,141,188,1)",
	          pointHighlightFill: "#fff",
	          pointHighlightStroke: "rgba(60,141,188,1)",
	          data: [28, 48, 40, 19, 86, 27, 90]
	        }
	      ]
	    };
	    
	    var areaChartOptions = {
	    	      //Boolean - If we should show the scale at all
	    	      showScale: true,
	    	      //Boolean - Whether grid lines are shown across the chart
	    	      scaleShowGridLines: false,
	    	      //String - Colour of the grid lines
	    	      scaleGridLineColor: "rgba(0,0,0,.05)",
	    	      //Number - Width of the grid lines
	    	      scaleGridLineWidth: 1,
	    	      //Boolean - Whether to show horizontal lines (except X axis)
	    	      scaleShowHorizontalLines: true,
	    	      //Boolean - Whether to show vertical lines (except Y axis)
	    	      scaleShowVerticalLines: true,
	    	      //Boolean - Whether the line is curved between points
	    	      bezierCurve: true,
	    	      //Number - Tension of the bezier curve between points
	    	      bezierCurveTension: 0.3,
	    	      //Boolean - Whether to show a dot for each point
	    	      pointDot: false,
	    	      //Number - Radius of each point dot in pixels
	    	      pointDotRadius: 4,
	    	      //Number - Pixel width of point dot stroke
	    	      pointDotStrokeWidth: 1,
	    	      //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
	    	      pointHitDetectionRadius: 20,
	    	      //Boolean - Whether to show a stroke for datasets
	    	      datasetStroke: true,
	    	      //Number - Pixel width of dataset stroke
	    	      datasetStrokeWidth: 2,
	    	      //Boolean - Whether to fill the dataset with a color
	    	      datasetFill: true,
	    	      //String - A legend template
	    	      legendTemplate: "",
	    	      //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
	    	      maintainAspectRatio: true,
	    	      //Boolean - whether to make the chart responsive to window resizing
	    	      responsive: true
	    	    };

    //-------------
    //- LINE CHART -
    //--------------
    var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
    var lineChart = new Chart(lineChartCanvas, {
        type: 'line',
        data: {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [
                {
                    label: "My First dataset",
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: "rgba(75,192,192,0.4)",
                    borderColor: "rgba(75,192,192,1)",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "rgba(75,192,192,1)",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "rgba(75,192,192,1)",
                    pointHoverBorderColor: "rgba(220,220,220,1)",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [65, 59, 80, 81, 56, 55, 40],
                    spanGaps: false,
                }
            ]
        },
        options: {
            scales: {
                xAxes: [{
                    type: 'linear',
                    position: 'bottom'
                }]
            }
        }
    });
    /*
    var lineChartOptions = areaChartOptions;
    lineChartOptions.datasetFill = false;
    lineChart.Line(areaChartData, lineChartOptions);
    */
  });
</script>

  <%@ include file="/WEB-INF/views/include/kims/footer.jsp"%>
