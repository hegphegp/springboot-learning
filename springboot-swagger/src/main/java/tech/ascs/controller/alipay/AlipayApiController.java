package tech.ascs.controller.alipay;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Api(description = "在Controller的接口定义的@Api注解的description")
public class AlipayApiController {
    /**
     * @Api注解作用于Controller类前面
     *     规定该注解只用下面这一个参数，用多就是错，用多了，死都不知道
     *     description : 某类接口总的描述
     *
     * @ApiOperation注解的tags标签表示在该Controller下接口再次分类
     *     规定该注解只用下面这几个参数，用多就是错，用多了，死都不知道
     *     value : 接口描述
     *     tags : Controller下接口划分
     *     position : 该接口的位置,暂时还是无效的还没实现实际功能的参数
     *     response : 返回的类型
     *     type : 3种类型(type,query,formData)
     *
     * @ApiParam注解的扯蛋参数的描述(@ApiParam有太多傻逼参数，很多参数是用于控制swagger页面对参数的展示,隐藏或者是否可读可写,对后端没意义的参数)
     *     规定该注解只用下面这几个参数，用多就是错，用多了，死都不知道
     *     value : 参数的描述(中文描述)
     *     defaultValue : 在swagger页码显示该参数的默认值
     *     required : 是否必传
     *     allowMultiple : 控制该参数是否是接收文件的参数
     *
     *     下面几个参数只需要了解
     *     readOnly : 用于控制该参数在swagger页面是否可读可写
     *     hidden : 用于控制该参数在swagger页面是否隐藏
     * @ApiParam只作用于swagger,不作用于接口实际传参时参数的非空校验，时间参数的格式校验
     * @RequestParam只作用于接口的实际传参，不会作用与swagger
     * @RequestParam有默认值参数不用非空约束required
     */
    @ApiOperation(value = "接口2的描述，在@ApiOperation注解的value配置", position = 2, response = Domain.class, tags={ "Controller下再对接口分类的类别1" })
    @PostMapping("/alipay/v1/{typeId}/post")
    public ResponseEntity<Map> post(@ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") int page,
                                    @ApiParam(value = "类型ID", type = "path") @PathVariable("typeId") String typeId,
                                    @ApiParam(value = "文件", type = "formData") @RequestParam(value="file") MultipartFile file) {
        return null;
    }
    
    @ApiOperation(value = "接口1的描述，在@ApiOperation注解的value配置", position = 1, response = Domain.class, tags={ "Controller下再对接口分类的类别2" })
    @GetMapping("/alipay/v1/{typeId}/query")
    public ResponseEntity<List<Map>> query(@ApiParam(value = "分页参数-页码", defaultValue = "10") @RequestParam(defaultValue = "10") int page,
                                           @ApiParam(value = "分页参数-每页大小", defaultValue = "1") @RequestParam(defaultValue = "1") int size,
                                           @ApiParam(value = "path路径参数", required = true) @PathVariable String typeId) {
        System.out.println(page);
        System.out.println(typeId);
        return null;
    }
}
