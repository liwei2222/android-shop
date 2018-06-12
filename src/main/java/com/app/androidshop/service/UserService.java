package com.app.androidshop.service;

import com.app.androidshop.Util.MapUtil;
import com.app.androidshop.model.LatLng;
import com.app.androidshop.po.Camerist;
import com.app.androidshop.po.Comments;
import com.app.androidshop.po.User;
import com.app.androidshop.respository.CameristRespository;
import com.app.androidshop.respository.CommentsRespository;
import com.app.androidshop.respository.UserRespository;
import com.app.androidshop.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRespository respository;

    @Autowired
    private CameristRespository cameristRespository;

    @Autowired
    private CommentsRespository commentsRespository;

    public User save(User user) {
        return respository.save(user);
    }

    public Boolean findUserByName(String name) {
        if(respository.findAllByName(name) == null) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean login(String name, String password,HttpServletRequest request) {
        User user = respository.findAllByNameAndPassword(name,password);
        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return true;
        }
        return false;
    }

    public List<Camerist> findCameristsOrderByLocation(LatLng latLng) {
        List<Camerist> camerists = cameristRespository.findAll();
        Collections.sort(camerists, new Comparator<Camerist>() {
            @Override
            public int compare(Camerist o1, Camerist o2) {
                LatLng latLng1 = convertCameristToLatlng(o1);
                LatLng latLng2 = convertCameristToLatlng(o2);
                Double distance1 = MapUtil.getLatLngDistance(latLng1,latLng);
                Double distance2 = MapUtil.getLatLngDistance(latLng2,latLng);
                if(distance1 > distance2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return camerists;
    }

    public List<Camerist> findCamerists(LatLng latLng) {
        List<Camerist> camerists = cameristRespository.findAll();
        Collections.sort(camerists, new Comparator<Camerist>() {
            @Override
            public int compare(Camerist o1, Camerist o2) {
                LatLng latLng1 = convertCameristToLatlng(o1);
                LatLng latLng2 = convertCameristToLatlng(o2);
                Double distance1 = MapUtil.getLatLngDistance(latLng1,latLng);
                Double distance2 = MapUtil.getLatLngDistance(latLng2,latLng);
                Double factor1 = distance1/distance2;
                Double factor2 = Double.valueOf(o1.getPrice())/Double.valueOf(o2.getPrice());
                if(factor1/factor2 > 1) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return camerists;
    }

    public List<Camerist> findCameristsOrderByPrice() {
        List<Camerist> camerists = cameristRespository.findAll();
        Collections.sort(camerists, new Comparator<Camerist>() {
            @Override
            public int compare(Camerist o1, Camerist o2) {
                if(Double.valueOf(o1.getPrice()) > Double.valueOf(o2.getPrice())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return camerists;
    }


    private LatLng convertCameristToLatlng(Camerist camerist) {
        if(camerist.getLocation() != null) {
            String[] str = camerist.getLocation().split(",");
            String lngStr = str[0];
            String latStr = str[1];
            Number lng = Double.valueOf(lngStr);
            Number lat = Double.valueOf(latStr);
            LatLng latLng = new LatLng();
            latLng.setLat(lat);
            latLng.setLng(lng);
            return  latLng;
        } else {
            return new LatLng();
        }
    }

    public Camerist findCameristById(Long id) {
        return cameristRespository.findAllById(id);
    }

    public List<CommentVo> findCommentsById(Long id) {
        List<Comments> commentsList = commentsRespository.findAllByCameristId(id);
        List<CommentVo> commentVos = new ArrayList<>();
        commentsList.stream()
                    .forEach(v -> {
                        CommentVo vo = new CommentVo();
                        vo.setUserName(v.getUserName());
                        vo.setContext(v.getComment());
                        vo.setCommentDate(v.getCommentTime());
                        commentVos.add(vo);
                    });
        return commentVos;
    }
}
