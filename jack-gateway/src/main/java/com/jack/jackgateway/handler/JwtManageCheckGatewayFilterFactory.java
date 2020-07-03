package com.jack.jackgateway.handler;

import com.alibaba.fastjson.JSON;
import com.jack.api.ResponseCode;
import com.jack.api.ResponseMessage;
import com.jack.common.jwt.JwtManageTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JwtManageCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtManageCheckGatewayFilterFactory.Config> {

    public JwtManageCheckGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            if (path != null && StringUtils.isNoneEmpty(path) && config.jwtNoCheckPath != null && StringUtils.isNoneEmpty(config.jwtNoCheckPath) &&
                    Arrays.asList(config.jwtNoCheckPath.replace(" ", "").split(",")).contains(path.trim())) {
                return chain.filter(exchange);
            }

            if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(config.jwtNoCheckPath)) {
                List<String> list = Arrays.asList(config.jwtNoCheckPath.replace(" ", "").split(","));
                for (int i = 0; i < list.size(); i++) {
                    if (StringUtils.isNotBlank(list.get(i))) {
                        String pathExt = list.get(i).trim();
                        if (pathExt.substring(pathExt.length() - 1, pathExt.length()).equals("*")) {
                            if (path.length() >= pathExt.length() - 1) {
                                if (path.substring(0, pathExt.length() - 1).equals(pathExt.substring(0, pathExt.length() - 1))) {
                                    return chain.filter(exchange);
                                }
                            }
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(config.callBackPathByRsaSignNoEncode) && Arrays.asList(config.callBackPathByRsaSignNoEncode.replace(" ", "").split(",")).contains(path)) {
                try {
                    ServerHttpRequest request = exchange.getRequest();
                    String urls = request.getURI().toString().split("[?]")[0] + "?";
                    String[] sp = request.getURI().toString().split("[?]")[1].split("&");
                    StringBuffer sb = new StringBuffer();
                    boolean reditryCheck = false;
                    for (int i = 0; i < sp.length; i++) {

                        if (StringUtils.isNotBlank(sp[i]) && sp[i].split("=").length > 1) {
                            String[] sp2 = sp[i].split("=");
                            if (sp2[0].equals("sign")) {
                                sb.append(sp2[0] + "=" + URLEncoder.encode(sp[i].substring(5, sp[i].length()), "UTF-8") + "&");
                            } else {
                                sb.append(sp2[0] + "=" + sp2[1] + "&");
                            }
                            reditryCheck = true;
                        }

                    }
                    String parStr = reditryCheck == true ? sb.toString() + "reditryCheck=true" : sb.toString();
                    System.out.println(parStr);
                    URI uri = new URI(urls + parStr);
                    ServerHttpRequest request2 = request.mutate().uri(uri).build();
                    return chain.filter(exchange.mutate().request(request2).build());
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
            String jwtToken = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (jwtToken != null && StringUtils.isNotEmpty(jwtToken)) {
                String[] sp = jwtToken.split(" ");
                if (sp.length == 2) {
                    String token = sp[1];
                    if (token != null && StringUtils.isNoneEmpty(token)) {
//                        Map<String, Object> map = JwtManageTool.getSysUserInfo(token);
//                        if (map != null && map.size() == 3) {
                            return chain.filter(exchange);
//                        }
                    }
                }
            }
            ServerHttpResponse response = exchange.getResponse();
            ResponseMessage responseMessage = new ResponseMessage(ResponseCode.TOKEN_ERROR);
            String mes = JSON.toJSONString(responseMessage);
            byte[] bits = mes.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //指定编码，否则在浏览器中会中文乱码

            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        };
    }

    public static class Config {
        //可以放过token验证的
        private String jwtNoCheckPath;

        public void setJwtNoCheckPath(String jwtNoCheckPath) {
            this.jwtNoCheckPath = jwtNoCheckPath;
        }

        public String getJwtNoCheckPath() {

            return this.jwtNoCheckPath;

        }


        //可以放过回调验证的
        private String callBackPathByRsaSignNoEncode;

        public void setCallBackPathByRsaSignNoEncode(String callBackPathByRsaSignNoEncode) {
            this.callBackPathByRsaSignNoEncode = callBackPathByRsaSignNoEncode;
        }

        public String getCallBackPathByRsaSignNoEncode() {
            return this.callBackPathByRsaSignNoEncode;
        }

    }
}
