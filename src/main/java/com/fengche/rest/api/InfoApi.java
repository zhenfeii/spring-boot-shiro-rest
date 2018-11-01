package com.fengche.rest.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by  Administrator on  2018/10/29
 */
@RestController
public class InfoApi {

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/public/info")
    public void publicinfo(HttpServletResponse response) throws IOException {
        Map<String,Object> map = new LinkedHashMap<>();

        map.put("info","公开api");

        response.getOutputStream().write(gson.toJson(map).getBytes("utf-8"));
    }

    @RequestMapping("/rest/info")
    @ResponseBody
    public String authinfo(){
        Map<String,Object> map = new LinkedHashMap<>();

        map.put("info","rest api");

        return gson.toJson(map);
    }

}
