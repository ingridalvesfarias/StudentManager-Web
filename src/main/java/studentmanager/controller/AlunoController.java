package studentmanager.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import studentmanager.dto.AlunoForm;
import studentmanager.dto.NotaForm;
import studentmanager.exception.MatriculaDuplicadaException;
import studentmanager.model.Aluno;
import studentmanager.service.AlunoService;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    // ===================== LISTAR E CADASTRAR =====================

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

    // ===================== NOTAS =====================

    @GetMapping("/{matricula}/notas")
    public String exibirFormularioNota(@PathVariable String matricula,
                                       Model model,
                                       RedirectAttributes redirect) {
        Aluno aluno = alunoService.buscarPorMatricula(matricula);
        if (aluno == null) {
            redirect.addFlashAttribute("erro", "Aluno não encontrado: " + matricula);
            return "redirect:/alunos";
        }

        model.addAttribute("aluno", aluno);
        model.addAttribute("notaForm", new NotaForm());
        return "alunos/notas";
    }

    @PostMapping("/{matricula}/notas")
    public String registrarNota(@PathVariable String matricula,
                                @Valid @ModelAttribute("notaForm") NotaForm form,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirect) {
        Aluno aluno = alunoService.buscarPorMatricula(matricula);
        if (aluno == null) {
            redirect.addFlashAttribute("erro", "Aluno não encontrado: " + matricula);
            return "redirect:/alunos";
        }

        if (!result.hasErrors()) {
            try {
                alunoService.registrarNota(matricula, form.getNota());
                redirect.addFlashAttribute("mensagem", "Nota registrada com sucesso!");
                return "redirect:/alunos/{matricula}/notas";
            } catch (IllegalArgumentException | IllegalStateException e) {
                result.reject("nota.invalida", e.getMessage());
            }
        }

        model.addAttribute("aluno", aluno);
        return "alunos/notas";
    }

    // ===================== DESEMPENHO =====================

    @GetMapping("/{matricula}/desempenho")
    public String consultarDesempenho(@PathVariable String matricula,
                                      Model model,
                                      RedirectAttributes redirect) {
        try {
            model.addAttribute("desempenho", alunoService.consultarDesempenho(matricula));
            return "alunos/desempenho";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/alunos";
        }
    }
}