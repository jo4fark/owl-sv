package com.athena.project.monitor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.athena.framework.web.controller.BaseController;
import com.athena.framework.web.domain.ResResult;
import com.athena.framework.web.domain.Server;

/**
 * 服务器监控
 * 
 * @author athena
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController extends BaseController
{
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public ResResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return ResResult.success(server);
    }
}
