package utez.edu.mx.pwa_narvaez_hoteles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Camarera;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Recepcionista;
import utez.edu.mx.pwa_narvaez_hoteles.repository.CamareraRepository;
import utez.edu.mx.pwa_narvaez_hoteles.repository.RecepcionistaRepository;

@SpringBootApplication
public class PwaNarvaezHotelesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PwaNarvaezHotelesApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RecepcionistaRepository recepRepo,
						   CamareraRepository camareraRepository) {
		return args -> {

			if (recepRepo.count() == 0) {
				Recepcionista r = new Recepcionista();
				r.setNombre("Luis");
				r.setApellidoPaterno("Meza");
				r.setApellidoMaterno("Adan");
				r.setTelefono("735246237");
				r.setUsuario("recepcion");
				r.setPassword("1234");
				recepRepo.save(r);
			}

			if (camareraRepository.count() == 0) {
				Camarera c1 = new Camarera();
				c1.setNombre("Ana");
				c1.setApellidoPaterno("Lopez");
				c1.setApellidoMaterno("Diaz");
				c1.setTelefono("1111111111");
				c1.setUsuario("analopez");
				c1.setPassword("Lopez1@");
				camareraRepository.save(c1);

				Camarera c2 = new Camarera();
				c2.setNombre("Maria");
				c2.setApellidoPaterno("Perez");
				c2.setApellidoMaterno("Soto");
				c2.setTelefono("2222222222");
				c2.setUsuario("mariasoto");
				c2.setPassword("Soto1@");
				camareraRepository.save(c2);
			}
		};
	}

}
