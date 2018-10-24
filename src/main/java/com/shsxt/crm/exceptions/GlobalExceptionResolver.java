package com.shsxt.crm.exceptions;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author zhangxuan
 * @date 2018/10/24
 * @time 16:42
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object handler,
                                         Exception ex) {

        ModelAndView mv = createDefaultModelAndView(httpServletRequest, ex);

        /***
         * 1. 区分是什么异常
         * 2. 区分是页面请求还是json请求
         * */
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ResponseBody responseBody = method.getAnnotation(ResponseBody.class);

            if (responseBody == null){

                /**
                 * 普通的页面请求
                 */
                if (ex instanceof ParamsException){
                    ParamsException e = (ParamsException) ex;
                    mv.addObject("errorMsg",e.getMsg());
                }
            } else {

                /**
                 * JSON请求
                 */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统繁忙");

                if (ex instanceof ParamsException){
                    ParamsException e = (ParamsException) ex;
                    mv.addObject("errorMsg",e.getMsg());
                }

                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter printWriter = null;

                try {
                    printWriter= httpServletResponse.getWriter();
                    printWriter.write(JSON.toJSONString(resultInfo));
                    printWriter.flush();
                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(null!=printWriter){
                        printWriter.close();
                    }
                }
                return null;
            }
        }
        return mv;
    }

    private ModelAndView createDefaultModelAndView(HttpServletRequest request,
                                                   Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");

        /**
         * // 错误信息
         */
        modelAndView.addObject("errorMsg", "系统繁忙");

        /**
         * // 错误码
         */
        modelAndView.addObject("errorCode", 300);

        /**
         * 上下文路径
         */
        modelAndView.addObject("ctx",request.getContextPath());
        return modelAndView;
    }
}
