### controller.java.ftl模板的参数
```
${package.Controller} 作用：package名称
<#if restControllerStyle></#if>  作用：作用1)引入RestController还是Controller的类路径; 作用2) 是引入@RestController注解还是引入@Controller注解
${superControllerClassPackage}   作用：引入父类的Controller
${table.comment}                 作用：引入数据表的注释
<#if package.ModuleName??>/${package.ModuleName}</#if> 作用：作用于Controller类前面的URL注解@RequestMapping，可应用于某些数据表生成某个模块的代码，另外一些数据表生成其他模块的代码，在代码生成器中把数据表按模块划分出来，每个模块的数据表单独执行一些代码生成器的功能
${author}                        作用：作者
${date}                          作用：日期
<#if controllerMappingHyphenStyle??></#if>  作用：驼峰表名是否转连字符，为true时，@RequestMapping("/managerUserActionHistory") -> @RequestMapping("/manager-user-action-history")
${controllerMappingHyphen}  作用：驼峰表名转连字符，即@RequestMapping("/managerUserActionHistory") -> @RequestMapping("/manager-user-action-history")
${table.entityPath} 作用：表名的驼峰命名作为Controller类的@RestMapping的URL路径
${table.controllerName} 作用：Controller类名
```

### entity.java.ftl模板的参数
```
${package.Entity} 作用：package名称
<#list table.importPackages as pkg>import ${pkg};</#list>
    上句table.importPackages对应的Java代码是
    tableInfo.setImportPackages(Model.class.getCanonicalName());// 开启 ActiveRecord 模式
    tableInfo.setImportPackages(TableName.class.getCanonicalName());// 表注解
    tableInfo.setImportPackages(TableLogic.class.getCanonicalName());// 逻辑删除注解
    tableInfo.setImportPackages(Version.class.getCanonicalName());// 乐观锁注解
    tableInfo.setImportPackages(config.getSuperEntityClass());// 父实体
    tableInfo.setImportPackages(Serializable.class.getCanonicalName());
    效果是在实体类引入 Model, TableName, TableLogic, Version, Serializable这些类
    import com.baomidou.mybatisplus.activerecord.Model;
    import com.baomidou.mybatisplus.annotations.TableLogic;
    import java.io.Serializable;
    
<#if entityLombokModel></#if>
${table.comment}                 作用：引入数据表的注释
${author}                        作用：作者
${date}                          作用：日期

```


### mybatis-plus生成MySQL代码的例子
```java
import java.util.Collections;

import com.mybatisplus.generator.AutoGenerator;
import com.mybatisplus.generator.InjectionConfig;
import com.mybatisplus.generator.config.DataSourceConfig;
import com.mybatisplus.generator.config.FileOutConfig;
import com.mybatisplus.generator.config.GlobalConfig;
import com.mybatisplus.generator.config.PackageConfig;
import com.mybatisplus.generator.config.StrategyConfig;
import com.mybatisplus.generator.config.TemplateConfig;
import com.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.mybatisplus.generator.config.po.TableInfo;
import com.mybatisplus.generator.config.rules.DbColumnType;
import com.mybatisplus.generator.config.rules.DbType;
import com.mybatisplus.generator.config.rules.NamingStrategy;
import com.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**代码生成器演示*/
public class MysqlGenerator {

    public static String javaFileOutPath = "/home/hgp/workspace/idea/code-gengerate/";
    public static String mapperXmlFileOutPath = "/home/hgp/workspace/idea/code-gengerate/xml/";
    /**MySQL生成演示*/
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
            // 全局配置
            new GlobalConfig()
                .setIdType(IdType.UUID)         // 设置数据库表逆向生成的实体类的主键都是UUID类型，除非该主键是MySQL自增类型的主键
                .setOutputDir(javaFileOutPath)  // 输出目录
                .setFileOverride(true)          // 是否覆盖文件
                .setActiveRecord(true)          // 开启 activeRecord 模式
                .setEnableCache(false)          // XML 二级缓存
                .setBaseResultMap(false)        // XML ResultMap
                .setBaseColumnList(false)       // XML columList
                .setKotlin(false)               // 是否生成 kotlin 代码
                .setAuthor("作者")
        ).setDataSource(
            new DataSourceConfig()       // 数据源配置
                .setDbType(DbType.MYSQL) // 数据库类型
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public DbColumnType processTypeConvert(String fieldType) {
                        if (fieldType.toLowerCase().contains("tinyint"))
                            return DbColumnType.BOOLEAN;
                        return super.processTypeConvert(fieldType);
                    }
                })
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root")
                .setPassword("root")
                .setUrl("jdbc:mysql://127.0.0.1:3306/rbac?characterEncoding=utf8&useUnicode=true&verifyServerCertificate=false&useSSL=false")
        ).setStrategy(
            new StrategyConfig()             // 策略配置
                // .setCapitalMode(false)    // 全局大写命名
                .setDbColumnUnderline(true)  // 全局下划线命名
                // .setTablePrefix(new String[]{"bmd_", "mp_"})// 此处可以修改为您的表前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setLogicDeleteFieldName("del") //逻辑删除的列名
        ).setPackageInfo(                    //设施
            new PackageConfig()
                .setParent("tech.ascs")      // 自定义包路径
                .setController("controller") // 这里是控制器包名，默认 web
        ).setCfg(
            new InjectionConfig() {
                @Override
                public void initMap() { }
            }.setFileOutConfigList(Collections.singletonList(new FileOutConfig("/templates/mapper.xml.ftl") {
                // 自定义输出文件目录
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return mapperXmlFileOutPath + tableInfo.getEntityName() + ".xml";
                }
            }))
        ).setTemplate(
            new TemplateConfig().setXml(null)  // 关闭默认 xml 生成，调整生成 至 根目录
        );
        // 执行生成
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
```

ConfigBuilder.processTable()方法定义了每个模板文件生成类名和文件名的规则,
修改的地方：
1) tableInfo.setServiceName("I" + tableInfo.getEntityName() + ConstVal.SERVICE); ==>>> tableInfo.setServiceName(tableInfo.getEntityName() + ConstVal.SERVICE);
2) controller.java.ftl 模板添加@Autowired注解，注入service对象

