package pe.edu.cibertec.patittas_frontend_wc_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.cibertec.patittas_frontend_wc_a.dto.LoginRequestDTO;
import pe.edu.cibertec.patittas_frontend_wc_a.dto.LoginResponseDTO;
import pe.edu.cibertec.patittas_frontend_wc_a.viewmodel.LoginModel;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/login")
public class LoginControllerAsync {

    @Autowired
    WebClient webClientAutenticacion;

    @PostMapping("/autenticar-async")
    public Mono<LoginResponseDTO> autenticar(@RequestBody LoginRequestDTO loginRequestDTO){

        //validar campos de entrada
        if(loginRequestDTO.tipoDocumento() == null || loginRequestDTO.tipoDocumento().trim().length() == 0 ||
                loginRequestDTO.numeroDocumento() == null || loginRequestDTO.numeroDocumento().trim().length() == 0 ||
                loginRequestDTO.password() == null || loginRequestDTO.password().trim().length() == 0){
            return Mono.just(new LoginResponseDTO("99", "Error: Debe completar correctamente sus credenciales", "", ""));
        }

        try {
            //consumir servicio de autenticacion
            return webClientAutenticacion.post()
                    .uri("/login")
                    .body(Mono.just(loginRequestDTO), LoginRequestDTO.class)
                    .retrieve()
                    .bodyToMono(LoginResponseDTO.class)
                    .flatMap(response -> {
                        if(response.codigo().equals("00")){
                            System.out.println(response.nombreUsuario());
                            return Mono.just(new LoginResponseDTO("00", "", response.nombreUsuario(), ""));
                        }else {
                            return Mono.just(new LoginResponseDTO("02", "Error: autentication fallida", "", ""));
                        }
                    });

        } catch (Exception e) {
            return Mono.just(new LoginResponseDTO("99", "Error: " + e.getMessage(), "", ""));
        }
    }
}
