package com.thoughtworks.orderdemo.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rlxie on 8/10/16.
 */
@Controller
@RequestMapping("/")
public class IndexResource {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
