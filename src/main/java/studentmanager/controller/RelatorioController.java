package studentmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import studentmanager.service.AlunoService;

@Controller
public class RelatorioController {

    private final AlunoService alunoService;

    public RelatorioController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/relatorio")
    public String exibir(Model model) {
        model.addAttribute("relatorio", alunoService.gerarRelatorio());
        return "relatorio";
    }
}