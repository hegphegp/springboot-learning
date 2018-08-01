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
     *     response : 返回的类型
     *
     *     暂时没用到的参数
     *     position : 该接口的位置,该参数的功能暂时还没实现     *
     *
     * @ApiParam注解的扯蛋参数的描述(@ApiParam有太多傻逼参数，很多参数是用于控制swagger页面对参数的展示,隐藏或者是否可读可写,对后端没意义的参数)
     *     规定该注解只用下面这几个参数，用多就是错，用多了，死都不知道
     *     value : 参数的描述(中文描述)
     *     defaultValue : 在swagger页码显示该参数的默认值
     *     required : 是否必传(默认不写是false，非必传)
     *     type : 3种类型(query,body,formData)，默认不写是query，有个参数是formData其它参数就不能是body
     *
     *     下面几个参数只需要了解
     *     readOnly : 用于控制该参数在swagger页面是否可读可写
     *     hidden : 用于控制该参数在swagger页面是否隐藏
     * @ApiParam只作用于swagger,不作用于接口实际传参时参数的非空校验，时间参数的格式校验
     * @RequestParam只作用于接口的实际传参，不会作用与swagger
     * @RequestParam有默认值参数不用非空约束，注解内required的标签
     */
    /**
     * 我写的逻辑:
     *     @RequestParam：除了要默认值和必须的限制，其它时候都不写，但是同一个接口的有些参数写，有些不写，排版看起来乱
     */
    /**formData格式的接口开发方式*/
    @ApiOperation(value = "接口2的描述，在@ApiOperation注解的value配置", position = 2, response = Domain.class, tags={ "Controller下再对接口分类的类别1" })
    @PostMapping("/alipay/v1/{typeId}/post")
    public ResponseEntity<Map> post(@ApiParam(value = "类型ID", type = "path") @PathVariable("typeId") String typeId,
                                    @ApiParam(value = "文件", type = "formData") @RequestParam(value="file") MultipartFile file,
                                    @ApiParam(value = "参数1", type = "formData") @RequestParam(defaultValue = "1") int param1,
                                    @ApiParam(value = "参数2", type = "formData") @RequestParam(defaultValue = "1") int param2) {
        System.out.println();
        return null;
    }

    /**query格式的接口开发方式*/
    @ApiOperation(value = "接口1的描述，在@ApiOperation注解的value配置", position = 1, response = Domain.class, tags={ "Controller下再对接口分类的类别2" })
    @GetMapping("/alipay/v1/{typeId}/query")
    public ResponseEntity<List<Map>> query(@ApiParam(value = "分页参数-页码", defaultValue = "10") @RequestParam(defaultValue = "10") int page,
                                           @ApiParam(value = "分页参数-每页大小", defaultValue = "1") @RequestParam(defaultValue = "1") int size,
                                           @ApiParam(value = "path路径参数", required = true) @PathVariable String typeId) {
        return null;
    }
}
