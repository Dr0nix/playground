package playground.apps.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Awake", description = "RENDER 서버 다운 방지용 임시 API")
@RestController
public class KeepAliveCtl {
    @GetMapping("/system/awake")
    public ResponseEntity<?> awake() {
        return ResponseEntity.ok("ok");
    }
}
