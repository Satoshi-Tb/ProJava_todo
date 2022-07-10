package jp.gihyo.projava.tasklist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //文字列を返す場合
public class HomeRestController {

	record TaskItem(String id, String task, String deadline, boolean done) {}
	private List<TaskItem> taskList = new ArrayList<TaskItem>();
	
	@RequestMapping(value = "/resthello")
	String hello() {
		return """
				Hello.
				It works!
				現在時刻は%sです
				""".formatted(LocalDateTime.now());
	}
	
	@GetMapping("/restadd")   //@RequestMapping(value = "/restadd", method = RequestMethod.GET) と同じ
	String addItem(@RequestParam("task")  String task,  @RequestParam("deadline") String deadline) {
		String id = UUID.randomUUID().toString().substring(0, 8);
		TaskItem item = new TaskItem(id, task, deadline, false);
		taskList.add(item);
		return "タスクを追加しました。";
	}
	
	@GetMapping("/restlist")
	String listeItems() {
		
		if (taskList.size() == 0) {
			return "タスクが存在しません。";
		}
		
		return taskList.
				stream().
				map((item) -> item.toString()).
				collect(Collectors.joining(", "));
	}
}
