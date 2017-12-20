select DISTINCT ${dicCode} as TYPECODE , ${dicText} as TYPENAME from  ${dicTable} 
where 1=1 
and delflag = '0'
<#if whereStr ?? && whereStr != ''>
and ${whereStr}
</#if>
order by ${dicCode}