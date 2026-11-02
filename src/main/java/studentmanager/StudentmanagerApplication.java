package studentmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import studentmanager.service.AlunoService;

@SpringBootApplication
public class StudentmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentmanagerApplication.class, args);
    }

    // TEMPORÁRIO: dados de exemplo para testar a listagem (removemos na Etapa 5)
    @Bean
    CommandLineRunner dadosDeExemplo(AlunoService service) {
        return args -> {
            service.cadastrar("Maria Silva", 20, "2024001", "Informática");
            service.registrarNota("2024001", 8.5);
            service.registrarNota("2024001", 7.0);

            service.cadastrar("João Souza", 22, "2024002", "Informática");
            service.registrarNota("2024002", 4.0);
            service.registrarNota("2024002", 5.0);

            service.cadastrar("Ana Lima", 19, "2024003", "Administração");
        };
    }
}