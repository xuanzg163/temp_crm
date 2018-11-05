package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangxuan
 * @date 2018/10/31
 * @time 22:31
 */


@Service
public class SaleChanceService extends BaseService<SaleChance> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    @Autowired
    private UserMapper userMapper;


    /**
     * 添加或更新营销机会
     * @param saleChance
     * @param userId
     */
    public void saveOrUpdateSaleChance(SaleChance saleChance,Integer userId){

        //参数校验
        checkSaleChanceParams(saleChance.getCustomerName()
                ,saleChance.getLinkMan(),
                saleChance.getLinkPhone());

        //补全参数
        saleChance.setUpdateDate(new Date());
         Integer id= saleChance.getId();

        /**
         * 判断添加或更新操作
         */
        if (id == null){
            //添加

            //未分配
            saleChance.setState(0);
            //未开发
            saleChance.setDevResult(0);
            //有效数据
            saleChance.setIsValid(1);
            // 创建事件
            saleChance.setCreateDate(new Date());
            User user = userMapper.queryById(userId);
            // 创建人
            saleChance.setCreateMan(user.getUserName());

            //添加
            AssertUtil.isTrue(saleChanceMapper.save(saleChance) < 1,
                    CrmConstant.OPS_FAILED_MSG);

        } else {
            //更新
        }

    }

    /**
     * 营销机会参数校验
     * @param customerName
     * @param linkMan
     */
    private void checkSaleChanceParams(String customerName,
                                       String linkMan,
                                       String linkPhone) {
        //简单的参数非空校验
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话为空");

    }

}
