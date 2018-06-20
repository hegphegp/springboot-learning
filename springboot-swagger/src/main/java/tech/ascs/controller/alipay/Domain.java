package tech.ascs.controller.alipay;

import io.swagger.annotations.ApiModelProperty;

public class Domain {
    @ApiModelProperty
    private String id;
    @ApiModelProperty
    private Integer age;

    @ApiModelProperty(value = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(value = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
