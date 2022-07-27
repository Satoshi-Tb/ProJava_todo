package jp.gihyo.projava.tasklist;

import java.time.LocalDateTime;
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
	private final TaskListDao dao;
	record TaskItem(String id, String task, String deadline, boolean done) {}
	//private List<TaskItem> taskList = new ArrayList<TaskItem>();
	
	public HomeController(TaskListDao dao) {
		//コンストラクタインジェクションにより、適切に初期化されたTaskListDaoをセット
		this.dao = dao;
	}
	
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
	String  listItems(Model model) {
		List<TaskItem> taskList = dao.findAll();
		model.addAttribute("taskList", taskList);
		return "home";
	}
	
	@GetMapping("/add")
	String addTask(@RequestParam("task")  String task,  @RequestParam("deadline") String deadline) {
		String id = UUID.randomUUID().toString().substring(0, 8);
		TaskItem item = new TaskItem(id, task, deadline, false);
		dao.add(item);
		
		return "redirect:/list";
	}
	
	@GetMapping("/delete")
	String deleteItem(@RequestParam("id") String id) {
		dao.delete(id);
		return "redirect:/list";
	}
	
	@GetMapping("/update")
	String updateItem(@RequestParam("id") String id, 
			@RequestParam("task") String task,
			@RequestParam("deadline") String deadline,
			@RequestParam("done") boolean done) {
		TaskItem item = new TaskItem(id, task, deadline, done);
		dao.update(item);
		return "redirect:/list";
		
	}
}
