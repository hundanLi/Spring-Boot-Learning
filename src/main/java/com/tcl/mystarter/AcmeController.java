package com.tcl.mystarter;

import com.tcl.acme.spring.boot.autoconfigure.Acme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/10 15:36
 */
@RestController
public class AcmeController {
    @Autowired
    Acme acme;

    @GetMapping("/starter/acme")
    public Acme acme() {
        return acme;
    }
}
