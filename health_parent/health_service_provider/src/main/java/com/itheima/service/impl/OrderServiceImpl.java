package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.service.impl.utils.DateUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author LLaamar
 * @date 2020/9/14 19:17
 */
@Service(interfaceClass = OrderService.class)
@Transactional(rollbackFor = {})
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 1.用户选择的日期能否进行体检的预约
     *      不能：直接返回失败
     * 2.用户预约的日期,【预约人数是否已满】
     *      已满: 直接返回失败
     * 3.用户在【该】日期,是否已经有了【该项】体检的预约
     *      已有: 直接返回失败
     * 4.进行预约
     *      系统导致的预约失败: 直接返回失败
     * 5.预约成功
     * 用户走到底5步才能返回【成功】
     */
    @Override
    public Result order(Map map) {
        // 用户预约的时间
        String order_Date = (String) map.get("orderDate");
        // 用户的预约类型
        String orderType = (String) map.get("orderType");
        // 用户想要预约的套餐ID
        Integer setmealId = Integer.parseInt( (String) map.get("setmealId") );
        // 预约状态
        String orderStatus = Order.ORDERSTATUS_NO;
        // 用户姓名
        String name = (String) map.get("name");
        // 用户性别
        String sex = (String) map.get("sex");
        // 用户身份证号
        String idCard = (String) map.get("idCard");
        // 用户预约的电话号码
        String phoneNumber = (String) map.get("telephone");
        Date orderDate = null;
        try {
            // 转换预约日期格式为Date
            orderDate = DateUtils.parseString2Date(order_Date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1.用户选择的日期能进行体检的预约
        // 在OrderSetting表中根据设置日期查询日期当天的设置信息
        OrderSetting orderSetting = orderSettingDao.getOrderSettingByDate(orderDate);

        if(orderSetting == null){
            // 没有当天的信息,不能进行体检预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        // 有当天的信息,判断当天预约人数是否已满
        // 获取当天的【总的可预约人数】
        int number = orderSetting.getNumber();
        // 获取当天的【已预约人数】
        int reservations = orderSetting.getReservations();

        if(number <= reservations){
            // 当天的预约人数已满,无法预约
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        // 当天的预约人数未满,可以【在用户没有预约过的前提下预约】
        // 根据用户的手机号在t_member表中判断用户是否是会员
        Member member = memberDao.findByPhoneNumber(phoneNumber);

        if(member != null){
            // 用户存在,查询t_order表中所有的用户的订单,判断在同一天内用户是否已经有了一个一样的套餐
            Order order = new Order(member.getId(),orderDate,null,null,setmealId);

            List<Order> orderList = orderDao.findByCondition(order);

            if(orderList != null && orderList.size() > 0){
                // 已有用户的订单
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else{
            // 用户不存在,将用户信息添加到t_member表中
            member = new Member();
            member.setName(name);
            member.setSex(sex);
            member.setPhoneNumber(phoneNumber);
            member.setIdCard(idCard);

            // 新增用户,并且用到LAST_INSERT_ID,获取方便之后member_id
            memberDao.add(member);
        }

       /* if(member == null){
            // 用户不存在,将用户信息添加到t_member表中
            // 不存在该用户,直接预约,并且将用户信息添加到t_member表中
            member = new Member();
            member.setName(name);
            member.setSex(sex);
            member.setPhoneNumber(phoneNumber);
            member.setIdCard(idCard);

            // 新增用户,并且用到LAST_INSERT_ID,获取方便之后member_id
            memberDao.add(member);
        }*/

        /**
         * 代码执行到这里:
         * 1.用户填写的预约日期可以进行预约
         * 2.预约当天人数未满
         * 3.用户不是在进行过重复预约
         * 4.member表中已经有了用户的信息
         * 接下来就只需要进行预约就行了
         */

        try{
            Order order = new Order(member.getId(),orderDate,orderType,orderStatus,setmealId);
            orderDao.add(order);

            // 预约成功,记得将t_ordersetting表中当天的已预约人数+1
            // 这里最好是使用Edit方法,将order对象作为参数,可以提高代码的复用性
            orderSetting.setReservations(reservations + 1);
            orderSettingDao.edit(orderSetting);

//            orderSettingDao.upOneReservations(order_date,reservations + 1);

            return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
        }catch (Exception e){
            return new Result(false,MessageConstant.ORDER_FAIL);
        }

    }

    @Override
    public Result findById(Integer id) {
        try{
            /*Order order = orderDao.findById(id);*/

            Map map = orderDao.findById(id);
            if(map != null){
                //处理日期格式
                Date orderDate = (Date) map.get("orderDate");
                map.put("orderDate",DateUtils.parseDate2String(orderDate));
            }

            /*return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,order);*/
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }
}
