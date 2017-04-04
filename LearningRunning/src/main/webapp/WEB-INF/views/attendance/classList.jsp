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

  <%@ include file="/WEB-INF/views/include/kims/footer.jsp"%>
