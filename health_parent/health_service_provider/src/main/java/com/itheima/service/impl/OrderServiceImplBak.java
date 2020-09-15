/*
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

*/
/**
 * @author LLaamar
 * @date 2020/9/14 19:17
 *//*



@Service(interfaceClass = OrderService.class)
@Transactional(rollbackFor = {})
public class OrderServiceImplBak implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result order(Map map) {

*/
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
 *//*



        // 准备需要使用的数据
        String orderDate = (String) map.get("orderDate");

        // 1.用户选择的日期能进行体检的预约
        // 在OrderSetting表中根据设置日期查询日期当天的设置信息
        OrderSetting orderSetting = orderSettingDao.getOrderSettingByDate(orderDate);

        // 能否预约的标记
        Boolean canOrder = true;

        if(orderSetting == null){
            // 没有当天的信息,不能进行体检预约
            canOrder = false;

            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else{
            // 有当天的信息,判断当天预约人数是否已满
            // 获取当天的可预约人数
            int number = orderSetting.getNumber();
            // 获取当天的已预约人数
            int reservations = orderSetting.getReservations();
            if(number <= reservations){
                // 当天的预约人数已满,无法预约
                return new Result(false,MessageConstant.ORDER_FULL);
            }else{
                // 当天的预约人数未满,可以【在用户没有预约过的前提下预约】
                // 根据用户的手机号在t_member表中判断用户是否是会员
                String phoneNumber = (String) map.get("telephone");
                Member member = memberDao.findByPhoneNumber(phoneNumber);

                if(member != null){

                    System.out.println(member);

                    // 用户存在,根据用户的id来查询t_order表中的订单,判断在同一天内用户是否已经有了一个一样的套餐
                    int setmealId = Integer.parseInt( (String) map.get("setmealId"));

                    int setmealIdInTable = orderDao.findSetmealIdByMemberId(member.getId());

                    if(setmealId == setmealIdInTable){
                        return new Result(false,MessageConstant.HAS_ORDERED);
                    }else{
                        // 可以预约
                        try{
                            Order order = new Order();

                            Date date = DateUtils.parseString2Date(orderDate);
                            Integer memberId = member.getId();
                            order.setMemberId(memberId);
                            order.setOrderDate(date);
                            order.setOrderType((String) map.get("orderType"));
                            order.setSetmealId(Integer.parseInt( (String) map.get("setmealId") ));
                            order.setOrderStatus(Order.ORDERSTATUS_NO);

                            orderDao.add(order);

                            // 预约成功,记得将t_ordersetting表中当天的已预约人数+1
                            orderSettingDao.upOneReservations(date,reservations + 1);

                            return new Result(true,MessageConstant.ORDER_SUCCESS);
                        }catch (Exception e){
                            return new Result(false,MessageConstant.ORDER_FAIL);
                        }
                    }

                }else{
                    try{
                        // 不存在该用户
                        // 直接预约,并且将用户信息添加到t_member表中
                        String name = (String) map.get("name");
                        String sex = (String) map.get("sex");
                        String idCard = (String) map.get("idCard");

                        Member newMember = new Member();
                        newMember.setName(name);
                        newMember.setSex(sex);
                        newMember.setPhoneNumber(phoneNumber);
                        newMember.setIdCard(idCard);

                        // 新增用户,并且用到LAST_INSERT_ID,获取member_id
                        memberDao.add(newMember);
                        Integer memberId = newMember.getId();

                        Order order = new Order();

                        Date date = DateUtils.parseString2Date(orderDate);

                        order.setMemberId(memberId);
                        order.setOrderDate(date);
                        order.setOrderType((String) map.get("orderType"));
                        order.setSetmealId(Integer.parseInt( (String) map.get("setmealId") ));
                        order.setOrderStatus(Order.ORDERSTATUS_NO);

                        orderDao.add(order);

                        // 预约成功,记得将t_ordersetting表中当天的已预约人数+1
                        orderSettingDao.upOneReservations(date,reservations + 1);

                        return new Result(true,MessageConstant.ORDER_SUCCESS);

                    }catch(Exception e){
                        e.printStackTrace();
                        return new Result(false,MessageConstant.ORDER_FAIL);
                    }

                }

            }
        }

    }
}
*/
