package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxuan
 * @date 2018/10/31
 * @time 22:33
 */

@Controller
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    /**
     * 删除/批量删除营销机会
     * @param ids
     * @return
     */
    @RequestMapping("deleteSaleChanceBatch")
    @ResponseBody
    public ResultInfo deleteSaleChanceBatch(Integer[] ids){
        saleChanceService.deleteBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    /**
     * 添加营销机会的时候显示下拉框中的客户经理
     * @return
     */
    @RequestMapping("queryAllCustomerManager")
    @ResponseBody
    public List<Map> queryAllCustomerManager() {
        return saleChanceService.queryAllCustomerManager();
    }

    /**
     * 添加或更新营销机会
     * @param saleChance
     * @param request
     * @return
     */
    @RequestMapping("saveOrUpdateSaleChance")
    @ResponseBody
    public ResultInfo saveOrUpdateSaleChance(SaleChance saleChance,
                                             HttpServletRequest request) {

        //获取用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        saleChanceService.saveOrUpdateSaleChance(saleChance,userId);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    /**
     * 查询营销机会管理信息
     * @param query
     * @return
     */
    @RequestMapping("querySaleChancesByParams")
    @ResponseBody
    public Map<String,Object> querySaleChancesByParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "1") Integer rows,
            SaleChanceQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return saleChanceService.queryForPage(query);
    }

    /**
     * 显示销售机会管理页面
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "sale_chance";
    }


}
