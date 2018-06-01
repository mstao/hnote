select n.id,
	   n.title,
	   n.uid,
	   n.is_deleted deleted,
	   n.gmt_create gmtCreate,
	   n.gmt_modified gmtModified,
	   nt.id nt_id,
	   nt.name nt_name,
	   f.id folder_id,
	   f.label folder_name,
	   f.level folder_level,
	   f.pid folder_pid
from hn_note n
left join hn_note_type nt on nt.id = n.type_id
left join hn_folder f on f.id = n.folder_id      
WHERE n.is_deleted = 0 AND n.gmt_modified >= :sql_last_value