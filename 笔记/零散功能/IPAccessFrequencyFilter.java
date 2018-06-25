import com.hgp.util.HttpRequestUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/** 黑名单拦截 */
@Component
public class IPAccessFrequencyFilter extends ZuulFilter {

    @Value("${ip.access.frequency.prex-key:ip_access_frequency_}")
    private String ipAccessFrequencyPrexKey;

    @Value("${ip.access.frequency:500}")
    private Long ipAccessFrequency;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0; // 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getThrowable() == null
                && ctx.getResponseBody() == null ; // 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String ip = HttpRequestUtil.getIpAddr(request);
        if (ip!=null) {
            String key = ipAccessFrequencyPrexKey +ip;
            long count = redisTemplate.opsForValue().increment(key, 1); //加1后看看值
            if (count == 1)  //刚创建,设置1分钟过期
                redisTemplate.expire(key, ipAccessFrequency, TimeUnit.MINUTES);
            if (count > ipAccessFrequency) {
                ctx.setSendZuulResponse(false); //过滤该请求，不对其进行路由
                ctx.setResponseStatusCode(404);// 返回错误码
//                ctx.set("isSuccess", false);
                System.out.println("用户IP[" + ip + "]访问超过了限定的次数[" + ipAccessFrequency + "]");
            }
        } else {
            ctx.setSendZuulResponse(false); //过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(404);// 返回错误码
//            ctx.set("isSuccess", false);
        }
        return null;
    }
}