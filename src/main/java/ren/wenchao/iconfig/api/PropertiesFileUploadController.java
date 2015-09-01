package ren.wenchao.iconfig.api;

import org.springframework.stereotype.Controller;
import ren.wenchao.iconfig.core.service.PropertiesFileUploadService;

import javax.annotation.Resource;

/**
 * @author rollenholt
 */
@Controller
public class PropertiesFileUploadController {

    @Resource
    private PropertiesFileUploadService propertiesFileUploadService;
}
