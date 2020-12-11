package org.wtm.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PremissFilter extends ZuulFilter {
    //过滤器的类型  权限一般是pre
    @Override
    public String filterType() {
        return "pre";
    }

    //过滤器的优先级
    @Override
    public int filterOrder() {
        return 0;
    }

    //是否过滤
    @Override
    public boolean shouldFilter() {
        return true;
    }


    //核心的过滤逻辑  返回值 无所谓
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();//获取当前请求
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(!"wtm".equals(username) || !"123".equals(password)){
            //如果不满足条件，直接这里给出响应
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            currentContext.addZuulResponseHeader("content-type","text/html;charset=utf-8");
            currentContext.setResponseBody("非法访问");
        }

        return null;
    }
}
