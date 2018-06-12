package com.app.androidshop.controller.user;

import com.app.androidshop.controller.Response;
import com.app.androidshop.model.LatLng;
import com.app.androidshop.po.Camerist;
import com.app.androidshop.po.Order;
import com.app.androidshop.po.User;
import com.app.androidshop.service.CommentsService;
import com.app.androidshop.service.OrderService;
import com.app.androidshop.service.ProductionService;
import com.app.androidshop.service.UserService;
import com.app.androidshop.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductionService productionService;

    /**
     * 用户注册
     * @param name
     * @param password
     * @param phone
     * @return
     */
    @GetMapping("/user/register")
    @ResponseBody
    public Response<Boolean> register(@Param("name") String name, @Param("password") String password, @Param("phone") String phone) {
        if(userService.findUserByName(name)) {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setPhone(phone);
            userService.save(user);
            return new Response<>("200","注册成功",true);
        } else {
            return new Response<>("200","用户名已存在",false);
        }
    }

    /**
     * 摄影师列表根据距离排序
     * @param location
     * @return
     */
    @GetMapping("/user/getCameristListOrderByLocation")
    @ResponseBody
    public Response<List<Camerist>> getCameristListByLocation(@Param("location") String location) {
        String[] strLocation = location.split(",");
        LatLng latLng = new LatLng();
        latLng.setLng(Double.valueOf(strLocation[0]));
        latLng.setLat(Double.valueOf(strLocation[1]));
        return new Response<>("200","查询成功",userService.findCameristsOrderByLocation(latLng));
    }

    /**
     * 摄影师列表根据价格排序
     * @return
     */
    @GetMapping("/user/getCameristListOrderByPrice")
    @ResponseBody
    public Response<List<Camerist>> getCameristListByPrice() {
        return new Response<>("200","查询成功",userService.findCameristsOrderByPrice());
    }

    /**
     * 摄影师综合排行
     * @param location
     * @return
     */
    @GetMapping("/user/getCameristList")
    @ResponseBody
    public Response<List<Camerist>> getCameristList(@Param("location") String location) {
        String[] strLocation = location.split(",");
        LatLng latLng = new LatLng();
        latLng.setLng(Double.valueOf(strLocation[0]));
        latLng.setLat(Double.valueOf(strLocation[1]));
        return new Response<>("200","查询成功",userService.findCamerists(latLng));
    }

    /**
     * 用户登录
     * @param name
     * @param password
     * @param request
     * @return
     */
    @GetMapping("/user/login")
    @ResponseBody
    public Response<Boolean> login(@Param("name") String name, @Param("password") String password, HttpServletRequest request) {
        Boolean bool = userService.login(name,password,request);
        if(bool == true) {
            return new Response<>("200", "登陆成功", true);
        } else {
            return new Response<>("200","用户名或密码错误",false);
        }
    }

    /**
     *根据id获取摄影师详情
     * @param id
     * @return
     */
    @GetMapping("/user/getCameristById")
    @ResponseBody
    public Response<Camerist> findCameristById(@Param("id") Long id) {
        return new Response<>("200","查询成功",userService.findCameristById(id));
    }

    /**
     * 根据摄影师id获取评论
     * @param id
     * @return
     */
    @GetMapping("/user/getComments")
    @ResponseBody
    public Response<CommentVo> findCommentsById(@Param("id") Long id) {
        return new Response("200","查询成功",userService.findCommentsById(id));
    }

    /**
     * 新增评论
     * @param cameristId
     * @param comment
     * @param request
     * @return
     */
    @GetMapping("/user/comment")
    @ResponseBody
    public Response<Boolean> comment (@Param("cameristId") Long cameristId,@Param("comment") String comment,HttpServletRequest request) {
        return new Response("200","新增评论成功",commentsService.save(comment,cameristId,request));
    }

    /**
     * 生成订单
     * @param cameristId
     * @param orderTime
     * @param orderPlace
     * @param request
     * @return
     */
    @GetMapping("/user/orderCamerist")
    @ResponseBody
    public Response<Order> order (@Param("cameristId") Long cameristId,@Param("orderTime") String orderTime,@Param("orderPlace") String orderPlace,HttpServletRequest request) {
        return new Response("200","新增评论成功",orderService.save(cameristId,orderTime,orderPlace,request));
    }

    /**
     * 用户查看订单
     * @param request
     * @return
     */
    @GetMapping("/user/getOrders")
    @ResponseBody
    public Response<List<Order>> findAllOrders(HttpServletRequest request) {
        return new Response<>("200","订单查询成功",orderService.fingAllByUserId(request));
    }

    @GetMapping("/user/getProductions")
    @ResponseBody
    public Response<List<String>> findProductionByCameristId(Long id) {
        List<String> productions = productionService.findAllByCameristId(id);
        productions.stream()
                .map(v -> {
                    return "http://10.80.104.60:8080/"+v;
                }).collect(Collectors.toList());
        return new Response<>("200","作品查询成功",productions);
    }


}
