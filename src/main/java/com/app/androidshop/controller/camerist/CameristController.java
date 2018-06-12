package com.app.androidshop.controller.camerist;


import ch.qos.logback.access.servlet.TeeHttpServletResponse;
import com.app.androidshop.controller.Response;
import com.app.androidshop.po.Camerist;
import com.app.androidshop.po.Order;
import com.app.androidshop.po.Production;
import com.app.androidshop.service.CameristService;
import com.app.androidshop.service.OrderService;
import com.app.androidshop.service.ProductionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@Slf4j
public class CameristController {

    @Autowired
    private CameristService cameristService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductionService productionService;

    /**
     * 摄影师注册
     * @param name
     * @param password
     * @param note
     * @param location
     * @param phone
     * @return
     */
    @GetMapping("/camerist/register")
    @ResponseBody
    public Response<Boolean> register(@Param("name") String name, @Param("password") String password, @Param("note") String note, @Param("location") String location, @Param("phone") String phone) {
        Camerist camerist = new Camerist();
        if(cameristService.findCameristByName(name)) {
            camerist.setName(name);
            camerist.setPassword(password);
            camerist.setLocation(location);
            camerist.setNote(note);
            camerist.setPhone(phone);
            cameristService.save(camerist);
            return new Response<>("200","注册成功",true);
        } else {
            return new Response<>("200","用户名已存在",false);
        }
    }

    /**
     * 摄影师登陆
     * @param name
     * @param password
     * @param request
     * @return
     */
    @GetMapping("/camerist/login")
    @ResponseBody
    public Response<Boolean> login(@Param("name") String name, @Param("password") String password, HttpServletRequest request) {
        Boolean bool = cameristService.login(name,password,request);
        if(bool == true) {
            return new Response<>("200", "登陆成功", true);
        } else {
            return new Response<>("200","用户名或密码错误",false);
        }
    }

    /**
     * 获取订单订单列表
     * @param request
     * @return
     */
    @GetMapping("/camerist/getOrderList")
    @ResponseBody
    public Response<List<Order>> findOrderList(HttpServletRequest request) {
        return new Response("200","订单列表获取成功",orderService.findAllByCameristId(request));
    }

    /**
     * 接受订单
     * @param orderId
     * @return
     */
    @GetMapping("/camerist/receiveOrder")
    @ResponseBody
    public Response<Boolean> receiveOrder(@RequestParam("orderId") Long orderId) {
        return new Response<>("200","接单成功",orderService.updateOrderStatus(orderId,"2"));
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/camerist/deleteOrder")
    @ResponseBody
    public Response<Boolean> deleteOrder(@RequestParam("orderId") Long orderId) {
        return new Response<>("200","取消订单成功",orderService.updateOrderStatus(orderId,"1"));
    }

    /**
     * 订单查询
     * @param orderId
     * @return
     */
    @GetMapping("/camerist/findOrder")
    @ResponseBody
    public Response<Order> findOrderById(@RequestParam("orderId") Long orderId) {
        return new Response<>("200","查询成功",orderService.findOrderById(orderId));
    }

    /**
     * 上传作品
     * @param file
     * @param fileName
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/camerist/uploadWord")
    @ResponseBody
    public Response<Boolean> uploadWork(@RequestParam("file") MultipartFile file,@RequestParam("fileName") String fileName, HttpServletRequest request) throws Exception {
        String address = "";
        try {
            Camerist camerist = (Camerist) request.getSession().getAttribute("user");
            address = uploadFile(file,fileName,camerist.getName());
            Production production = new Production();
            production.setCameristId(camerist.getId());
            production.setProduction(address);
            productionService.save(production);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response<>("200","上传成功",true);
    }

    private String uploadFile(MultipartFile partFile, String fileName,String name) throws Exception {
        if (partFile != null) {
            String filePath = "C:\\picture";
            String str[] = partFile.getOriginalFilename().split("\\.");
            String type = str[1];
            if(type == null) {
                throw new Exception("文件类型错误");
            }
            File dir = new File(filePath);
            if (!dir.isDirectory()) {
                dir.mkdir();
            }
            String fileOriginalName = fileName;
            String uuId = name+"-"+String.valueOf(UUID.randomUUID())+fileName+"."+type;
            String newFileName = filePath+"\\"+uuId;
            File file = new File(newFileName);
            partFile.transferTo(file);
            return uuId;
        } else
            return null;
    }

    /**
     * 查询作品
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/camerist/getProductions")
    @ResponseBody
    public Response<List<String>> getProductions(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        Long id = null;
        if(request != null) {
            Camerist camerist = (Camerist) request.getSession().getAttribute("user");
            id = camerist.getId();
        }
        List<String> roots = productionService.findAllByCameristId(id);
        List<String> rootStrs = new ArrayList<>();
        roots.stream()
                .forEach(v -> {
                    rootStrs.add("http://10.80.104.60:8080/"+v);
                });
        return new Response<>("200","作品查询成功",rootStrs);
    }
}
