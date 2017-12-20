select f.* from cgform_field f,cgform_head h
 where f.table_id = h.id and f.is_show <> 'Y'
 and h.code=:tableName order by f.order_num
  