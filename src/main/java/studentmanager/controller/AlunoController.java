package studentmanager.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import studentmanager.dto.AlunoForm;
import studentmanager.exception.MatriculaDuplicadaException;
import studentmanager.service.AlunoService;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("alunoForm", new AlunoForm());
        return "alunos/form";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("alunoForm") AlunoForm form,
                            BindingResult result,
                            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "alunos/form";
        }

        try {
            alunoService.cadastrar(form.getNome(), form.getIdade(),
                    form.getMatricula(), form.getCurso());
        } catch (MatriculaDuplicadaException e) {
            result.rejectValue("matricula", "matricula.duplicada", e.getMessage());
            return "alunos/form";
        }

        redirect.addFlashAttribute("mensagem", "Aluno cadastrado com sucesso!");
        return "redirect:/alunos";
    }
}