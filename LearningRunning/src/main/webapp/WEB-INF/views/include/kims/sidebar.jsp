<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="<%=request.getContextPath()%>/webjars/adminlte/2.3.11/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>Alexander Pierce</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- search form -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
        <li class="active treeview">
          <a href="#">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="active"><a href="index.html"><i class="fa fa-circle-o"></i> Dashboard v1</a></li>
            <li><a href="index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>
          </ul>
        </li>
        
        <!-- ///// 출결 관리 ///// -->
        <li class="treeview">
          <a href="#">
            <i class="fa fa-check-square"></i>
            <span>출결 관리</span>
            <span class="pull-right-container">
              <span class="label label-primary pull-right">3</span>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=request.getContextPath()%>/teacher/attendance/classList"><i class="fa fa-circle-o"></i> 출결 입력</a></li>
            <li><a href="<%=request.getContextPath()%>/attendance/list"><i class="fa fa-circle-o"></i> 반별 현황</a></li>
            <li><a href="<%=request.getContextPath()%>/attendance/person"><i class="fa fa-circle-o"></i> 개인별 현황</a></li>
          </ul>
        </li>
        
        <!-- ///// 상담 관리 ///// -->
        <li class="treeview">
          <a href="#">
            <i class="fa fa-comments"></i>
            <span>상담 관리</span>
            <span class="pull-right-container">
              <span class="label label-primary pull-right">2</span>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=request.getContextPath()%>/teacher/counsel/counselManage"><i class="fa fa-circle-o"></i> 상담 관리</a></li>
            <li><a href="<%=request.getContextPath()%>/teacher/counsel/counselDetail"><i class="fa fa-circle-o"></i> 상담 목록</a></li>
          </ul>
        </li>
        
        <!-- ///// 성적 관리 ///// -->
        <li class="treeview">
          <a href="#">
            <i class="fa fa-line-chart"></i>
            <span>성적 관리</span>
            <span class="pull-right-container">
              <span class="label label-primary pull-right">3</span>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=request.getContextPath()%>/teacher/score/insert"><i class="fa fa-circle-o"></i> 성적 관리</a></li>
            <li><a href="<%=request.getContextPath()%>/teacher/score/listA"><i class="fa fa-circle-o"></i> 반별 성적</a></li>
            <li><a href="<%=request.getContextPath()%>/teacher/score/listB"><i class="fa fa-circle-o"></i> 개인별 성적</a></li>
          </ul>
        </li>
        
        <!-- ///// 관리자 ///// -->
        <li class="treeview">
          <a href="#">
            <i class="fa fa-bank"></i>
            <span>관리자</span>
            <span class="pull-right-container">
              <span class="label label-primary pull-right">4</span>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=request.getContextPath()%>/admin/member"><i class="fa fa-circle-o"></i> 회원 관리</a></li>
            <li><a href="<%=request.getContextPath()%>/admin/classJoinMem"><i class="fa fa-circle-o"></i> 회원 등록</a></li>
            <li><a href="<%=request.getContextPath()%>/admin/auth"><i class="fa fa-circle-o"></i> 권한 관리</a></li>
            <li><a href="<%=request.getContextPath()%>/admin/class"><i class="fa fa-circle-o"></i> 반(과정) 관리</a></li>
          </ul>
        </li>
        
        <li>
          <a href="pages/calendar.html">
            <i class="fa fa-calendar"></i> <span>Calendar</span>
            <span class="pull-right-container">
              <small class="label pull-right bg-red">3</small>
              <small class="label pull-right bg-blue">17</small>
            </span>
          </a>
        </li>
        <li>
          <a href="pages/mailbox/mailbox.html">
            <i class="fa fa-envelope"></i> <span>Mailbox</span>
            <span class="pull-right-container">
              <small class="label pull-right bg-yellow">12</small>
              <small class="label pull-right bg-green">16</small>
              <small class="label pull-right bg-red">5</small>
            </span>
          </a>
        </li>
        
        <li class="header">LABELS</li>
        <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>