package tech.ascs.controller.weixinpay;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeixinApiController implements WeixinApi {
    @Override
    public ResponseEntity<Void> query(String typeId) {
        return null;
    }
}
