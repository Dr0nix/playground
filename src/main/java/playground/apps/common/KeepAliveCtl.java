package playground.apps.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeepAliveCtl {
    @GetMapping("/system/awake")
    public ResponseEntity<?> awake() {
        return ResponseEntity.ok("ok");
    }
}
