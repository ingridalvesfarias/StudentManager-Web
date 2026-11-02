package studentmanager.util;

import java.util.List;
import studentmanager.model.SituacaoAcademica;

/**
 * Classe utilitária: cálculo de média e definição da situação acadêmica.
 */
public final class CalculadoraMedia {

    public static final double MEDIA_APROVACAO = 7.0;
    public static final double MEDIA_RECUPERACAO = 5.0;

    // Construtor privado: ninguém precisa (nem deve) criar objetos desta classe
    private CalculadoraMedia() {
    }

    public static double calcularMedia(List<Double> notas) {
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("Não há notas para calcular a média.");
        }
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.size();
    }

    public static double calcularMediaGeral(List<Double> medias) {
        return calcularMedia(medias);
    }

    public static SituacaoAcademica definirSituacao(double media) {
        if (media >= MEDIA_APROVACAO) {
            return SituacaoAcademica.APROVADO;
        }
        if (media >= MEDIA_RECUPERACAO) {
            return SituacaoAcademica.RECUPERACAO;
        }
        return SituacaoAcademica.REPROVADO;
    }
}