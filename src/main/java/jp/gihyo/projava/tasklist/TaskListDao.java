package jp.gihyo.projava.tasklist;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import jp.gihyo.projava.tasklist.HomeController.TaskItem;

@Service
public class TaskListDao {

	private final JdbcTemplate jdbcTemplate;
	
	public TaskListDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void add(TaskItem taskItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
		
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tasklist");
		
		insert.execute(param);
	}
	
	public List<TaskItem> findAll() {
		String query = "select * from tasklist order by deadline";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
		List<TaskItem> taskItems = result.stream()
				.map((r) -> new TaskItem(
						r.get("id").toString(),
						r.get("task").toString(),
						r.get("deadline").toString(),
						(Boolean)r.get("done")
						))
				.toList();
		
		return taskItems;
	}
	
	public int delete(String id) {
		return jdbcTemplate.update("delete from tasklist where id = ?",  id);
	}
	
	
}
