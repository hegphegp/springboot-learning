package tech.ascs.controller.alipay;

import io.swagger.annotations.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Api(value = "在Controller的接口定义的@Api注解的value", description = "在Controller的接口定义的@Api注解的description")
public interface AlipayApi {
    @ApiOperation(value = "在每个restful接口方法定义的@ApiOperation注解的value", nickname = "阿里支付类的查询接口", response = Domain.class, tags={ "在每个restful接口方法定义的@ApiOperation注解的tags" })
//    @ApiResponse(code = 200, message = "successful operation", response = Domain.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "book's name", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "typeId", value = "book's name", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "date", value = "book's date", required = false, dataType = "string", paramType = "query")})
    @PostMapping("/alipay/v1/{typeId}/query")
    ResponseEntity<Void> query(@PathVariable("typeId") String typeId);
}
