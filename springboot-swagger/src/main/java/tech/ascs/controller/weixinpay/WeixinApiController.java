package tech.ascs.controller.weixinpay;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "weixinpay", description = "微信支付类的接口")
@RestController
public class WeixinApiController {
    @ApiOperation(value = "微信支付类的查询接口", nickname = "微信支付类的查询接口", tags={ "weixinpay" })
    @PostMapping("/weixinpay/v1/{typeId}/query")
    public ResponseEntity<Void> query(String typeId) {
        return null;
    }
}
