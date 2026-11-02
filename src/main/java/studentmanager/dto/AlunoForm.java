package studentmanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Dados digitados no formulário de cadastro de aluno.
 */
public class AlunoForm {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotNull(message = "A idade é obrigatória.")
    @Min(value = 1, message = "A idade deve ser de no mínimo 1.")
    @Max(value = 120, message = "A idade deve ser de no máximo 120.")
    private Integer idade;

    @NotBlank(message = "A matrícula é obrigatória.")
    private String matricula;

    @NotBlank(message = "O curso/turma é obrigatório.")
    private String curso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}