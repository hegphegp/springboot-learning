package tech.ascs.controller.weixinpay;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Api(value = "weixinpay", description = "微信支付类的接口")
public interface WeixinApi {
    @ApiOperation(value = "微信支付类的查询接口", nickname = "微信支付类的查询接口", tags={ "weixinpay" })
    @PostMapping("/weixinpay/v1/{typeId}/query")
    ResponseEntity<Void> query(@ApiParam(value = "产品类型ID", required=true) @PathVariable("typeId") String typeId);
}