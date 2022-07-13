package jp.gihyo.projava.tasklist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	record TaskItem(String id, String task, String deadline, boolean done) {}
	private List<TaskItem> taskList = new ArrayList<TaskItem>();
	
	@RequestMapping(value = "/hellohtml")
	@ResponseBody
	String hello() {
		//htmlそのものを返す
		return """
				<html>
					<head><title>Hello</title></head>
					<body>
						<h1>Hello</h1>
						現在時刻は%sです
					</body>
				</html>
				""".formatted(LocalDateTime.now());
	}
	
	@RequestMapping(value = "/hello")
	String hello(Model model) {
		//templateファイルに表示する属性をセット
		model.addAttribute("time", LocalDateTime.now());
		return "hello";  //対応するビュー名を返す。
	}
	
	@GetMapping("/list")
	String  list(Model model) {
		model.addAttribute("taskList", taskList);
		return "home";
	}
	
	@GetMapping("/add")
	String addTask(@RequestParam("task")  String task,  @RequestParam("deadline") String deadline) {
		String id = UUID.randomUUID().toString().substring(0, 8);
		TaskItem item = new TaskItem(id, task, deadline, false);
		taskList.add(item);
		
		return "redirect:/list";
	}
}
