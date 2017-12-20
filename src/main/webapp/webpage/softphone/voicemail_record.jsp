<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="col-md-12">
	<form id="recordForm" method="get">	
      <div class="row m-t-10">
		<div class="information-form">
			<div class="row">	
				<div class="col-md-12">
					<object height=350px width=400px id="Player"
							classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6>
							<param name='URL' value='${subject}' />
							<param name='rate' value='1' />
							<param name='balance' value='0' />
							<param name='currentPosition' value='0' />
							<param name='defaultFrame' value='' />
							<param name='playCount' value='1' />
							<param name='autoStart' value='0' />
							<param name='currentMarker' value='0' />
							<param name='invokeURLs' value='-1' />
							<param name='baseURL' value='' />
							<param name='volume' value='50' />
							<param name='mute' value='0' />
							<param name='uiMode' value='FULL' />
							<param name='stretchToFit' value='0' />
							<param name='windowlessVideo' value='0' />
							<param name='enabled' value='-1' />
							<param name='enableContextMenu' value='-1' />
							<param name='fullScreen' value='0' />
							<param name='SAMIStyle' value='' />
							<param name='SAMILang' value='' />
							<param name='SAMIFilename' value='' />
							<param name='captioningID' value='' />
							<param name='enableErrorDialogs' value='0' />
							<param name='_cx' value='13229' />
							<param name='_cy' value='10583' />
						</object>
						<%-- <i class='fa fa-download green'></i><span><a href='javascript:return false'
						onclick="downloadRecord('${orderRecord.id}')">下载 </a>	</span>	 --%>				
				</div>
			</div>
			<%-- <div class="row">
				<div class="col-md-12">
                	<chr:perm code="business_accept_recordDownload"><button type="button"  class="btn form-btn-sm certain-btn  m-r-10" onclick="downloadRecord('${orderRecord.id}')">下载</button></chr:perm> 
                	<button type="button" class="btn form-btn-sm back-btn  m-r-10" onclick="javascript:art.dialog.list['recordDialog'].close();">返回</button>
                </div>
            </div> --%>
		</div>
		</div>
		</form>		
			
        
	</div>
<script type="text/javascript">

/**
 * 下载录音文件
 */
function downloadRecord(url) {
	$.fileDownload( "${subject}", {
		httpMethod : "POST",
	}).done(function() {
		art.dialog({
			title : '下载提示',
			icon : 'succeed',
			lock : false,
			content : '下载成功，2秒后会自动关闭……',
			time : 2
		});
	}).fail(function() {
		art.dialog({
			title : '下载提示',
			icon : 'error',
			lock : false,
			content : '下载失败，2秒后会自动关闭……',
			time : 2
		});
	});
}
</script>
