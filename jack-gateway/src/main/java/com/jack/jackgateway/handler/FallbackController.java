package com.jack.jackgateway.handler;

import com.jack.api.ResponseCode;
import com.jack.api.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/15 11:07
 * @Description:
 */
@RestController
public class FallbackController  {
    @RequestMapping(value = "/fallback", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseMessage fallback() {
        return new ResponseMessage(ResponseCode.REMOTE_SERVER_ERROR);
    }
}
