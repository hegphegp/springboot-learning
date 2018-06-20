package tech.ascs.controller.alipay;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlipayApiController implements AlipayApi {
    @Override
    public ResponseEntity<Void> query(String typeId) {
        return null;
    }
}
