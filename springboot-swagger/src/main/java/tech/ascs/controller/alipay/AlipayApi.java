package tech.ascs.controller.alipay;

import io.swagger.annotations.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;

@Api(value = "alipay", description = "阿里支付类的接口")
public interface AlipayApi {
    @ApiOperation(value = "阿里支付类的查询接口", nickname = "阿里支付类的查询接口", response = Domain.class, tags={ "alipay" })
//    @ApiResponse(code = 200, message = "successful operation", response = Domain.class)
    @PostMapping("/alipay/v1/{typeId}/query")
    ResponseEntity<Void> query(@NotNull @ApiParam(value = "产品类型ID", required=true) @PathVariable("typeId") String typeId);
}
