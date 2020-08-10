package com.tcl.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/10 11:33
 */
@Component
@WebEndpoint(id="my-endpoints", enableByDefault = true)
public class MyEndpoints {

    @ReadOperation
    public String readOps() {
        return "read ops";
    }


    @WriteOperation
    public String writeOps() {
        return "write ops";
    }

    @DeleteOperation
    public String deleteOps() {
        return "delete ops";
    }
}
