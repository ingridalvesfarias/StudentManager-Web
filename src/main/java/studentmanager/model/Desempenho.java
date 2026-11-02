package studentmanager.model;

/**
 * Resultado acadêmico de um aluno: média e situação.
 */
public record Desempenho(Aluno aluno, double media, SituacaoAcademica situacao) {
}