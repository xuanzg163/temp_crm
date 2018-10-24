package com.shsxt.crm.exceptions;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.model.ResultInfo;
import constants.CrmConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author zhangxuan
 * @date 2018/10/24
 * @time 16:42
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {

        ModelAndView mv = createDefaultModelAndView(request, ex);

        /**
         * 用户登陆异常错误
         */
        if (ex instanceof LoginException){
            mv.addObject("errorMsg", CrmConstant.USER_NOT_LOGIN_MSG);
            mv.setViewName("login_error");
            return mv;
        }

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

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter printWriter = null;

                try {
                    printWriter= response.getWriter();
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
        modelAndView.addObject("uri",request.getRequestURI());
        return modelAndView;
    }
}
