package pe.edu.cibertec.patittas_frontend_wc_a.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.cibertec.patittas_frontend_wc_a.config.FeignConfig;
import pe.edu.cibertec.patittas_frontend_wc_a.dto.LogoutRequestDTO;
import pe.edu.cibertec.patittas_frontend_wc_a.dto.LogoutResponseDTO;

@FeignClient(name = "salida", url = "http://localhost:8090/autenticacion", configuration = FeignConfig.class)
public interface LogoutClient {

    @PostMapping("/logout")
    ResponseEntity<LogoutResponseDTO> logout(@RequestBody LogoutRequestDTO logoutRequestDTO);
}
