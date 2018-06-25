import com.fasterxml.jackson.databind.JavaType;
import org.springframework.context.annotation.Bean;
import org.springframework.web.util.HtmlUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

@Component
public class XSSMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter () {
        return new XSSMappingJackson2HttpMessageConverter();
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        JavaType javaType = getJavaType(type, contextClass);
        Object obj = readJavaType(javaType, inputMessage);
        String json = super.getObjectMapper().writeValueAsString(obj);
        return super.getObjectMapper().readValue(cleanXSS(json), javaType);
    }

    // 这个就是父类的readJavaType方法，由于父类该方法是private的，所以我们copy一个用
    private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
        try {
            return super.getObjectMapper().readValue(inputMessage.getBody(), javaType);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    // 重写writeInternal方法，在返回内容前首先进行加密或者过了XSS攻击的html标签
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        // 使用Jackson的ObjectMapper将Java对象转换成Json String
        String json = super.getObjectMapper().writeValueAsString(object);
        System.out.println(cleanXSS(json));
        outputMessage.getBody().write(cleanXSS(json).getBytes()); // 输出
    }

    private String cleanXSS(String value) {
        String result = HtmlUtils.htmlEscape(value).replaceAll("&quot;", "\"");
        return result;
    }
}