package studentmanager.model;

/**
 * Resumo geral da turma.
 */
public record RelatorioTurma(
        int totalAlunos,
        int avaliados,
        int aprovados,
        int emRecuperacao,
        int reprovados,
        double mediaGeral) {
}