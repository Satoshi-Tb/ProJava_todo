package jp.gihyo.projava.tasklist;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

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
	
}
