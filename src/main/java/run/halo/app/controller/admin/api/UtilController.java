package run.halo.app.controller.admin.api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Controller
public class UtilController {
    private final FreeMarkerConfigurer freeMarker;

    public UtilController(FreeMarkerConfigurer freeMarker) {
        this.freeMarker = freeMarker;
    }

    @GetMapping("randomPic")
    public String randomPic() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/random/img.txt");
        String urlString = IOUtils.toString(classPathResource.getInputStream(), Charset.defaultCharset());
        List<String> urls = urlString.lines().collect(Collectors.toList());
        return "redirect:"+urls.get((int) (Math.random()*urls.size()-1));
    }

    @GetMapping(value = "silian.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String silian() throws TemplateException, IOException {
        Template template = freeMarker.getConfiguration().getTemplate("common/web/silian_xml.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, new Object());
    }
}
