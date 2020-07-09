package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class questionController {
    @Autowired
    private RestTemplate restTemplate;


    //登陆
    @GetMapping("/login")
    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    //添加问题
    @RequestMapping("addquestion")
    public String addquestion(HttpServletRequest request, Map<String,Object> map)
    {
        String id = request.getParameter("id");
        String item = request.getParameter("item");
        String value1 = request.getParameter("value1");
        String value2 = request.getParameter("value2");

        String s = restTemplate.postForObject("http://localhost:9000/rest/addquestion?id="
                + id + "&item=" + item + "&value1=" + value1 + "&value2=" + value2,
                null, String.class);

        if("add".equals(s))     //添加成功
        {
            map.put("msg","add");
        }
        else
        {
            map.put("msg1","fault");
        }
        return "login";
    }

    //删除问题
    @RequestMapping("/deletequestion")
    public String deletequestion(HttpServletRequest request,Map<String,Object> map)
    {
        String id = request.getParameter("id");
        String s = restTemplate.postForObject("http://localhost:9000/rest/deletequestion?id="
                + id, null, String.class);

        if("delete".equals(s))    //删除成功
        {
            map.put("msg2","delete");
        }
        else
        {
            map.put("msg3","fault");
        }
        return "login";
    }


    //修改问题
    @RequestMapping("/updatequestion")
    public String updatequestion(HttpServletRequest request,Map<String,Object> map)
    {
        String id = request.getParameter("id");
        String item = request.getParameter("item");
        String value1 = request.getParameter("value1");
        String value2 = request.getParameter("value2");

        String s = restTemplate.postForObject("http://localhost:9000/rest/updatequestion?id="
                + id + "&item=" + item + "&value1=" + value1 + "&value2=" + value2,
                null, String.class);

        if("update".equals(s))   //修改成功
        {
            map.put("msg4","update");
        }
        else
        {
            map.put("msg5","fault");
        }
        return "login";
    }


    //得到一个问题，即查询一个问题
    @RequestMapping("/getquestion")
    public String getquestion(HttpServletRequest request,Map<String,Object> map)
    {
        String id = request.getParameter("id");
        Question question = restTemplate.postForObject("http://localhost:9000/rest/getquestion?id="
                + id, null, Question.class);


        if(question!=null)     //有该问题
        {
            System.out.println(question);
            map.put("msg6","get");
        }
        else
        {
            map.put("msg7","fault");
        }
        return "login";
    }


    //查询所有问题，即得到所有问题
    @RequestMapping("/getallquestion")
    public String getallquestion()
    {
        //得到json字符串
        List list = restTemplate.postForObject("http://localhost:9000/rest/getallquestion",
                null, List.class);

        for(Object o:list)
        {
            JSON json=(JSON) JSON.toJSON(o);    //json字符串转成json对象
            Question question = JSON.toJavaObject(json, Question.class);  //json对象转换成question对象
            System.out.println(question);
        }
        return "login";
    }
}
