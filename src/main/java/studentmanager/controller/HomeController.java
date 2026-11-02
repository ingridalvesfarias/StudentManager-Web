package studentmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studentmanager.service.AlunoService;

@Controller
public class HomeController {

    private final AlunoService alunoService;

    public HomeController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Student Manager");
        model.addAttribute("totalAlunos", alunoService.quantidade());
        return "home";
    }
}