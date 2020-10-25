// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
	             "language": {
                 "lengthMenu": "每頁 _MENU_ 筆資料",
                 "zeroRecords": "沒有找到資料",
                 "info": "第 _PAGE_頁( 總共_PAGES_ 頁 )",
                 "infoEmpty": "無紀錄",
                 "infoFiltered": "(從 _MAX_ 條紀錄過濾)",
                 "search" : "搜尋",
                 "paginate": {
                     "previous": "上一頁",
                     "next": "下一頁"
                   }
             }
	  
  });
  
  $('#dataTableNoSearchChange').DataTable({
      "language": {
      "lengthMenu": "每頁 _MENU_ 筆資料",
      "zeroRecords": "沒有找到資料",
      "info": "第 _PAGE_頁( 總共_PAGES_ 頁 )",
      "infoEmpty": "無紀錄",
      "infoFiltered": "(從 _MAX_ 條紀錄過濾)",
      "search" : "搜尋",
      "paginate": {
          "previous": "上一頁",
          "next": "下一頁"
        }
  },
  "lengthChange": false,
  "searching":false

});
  
});
