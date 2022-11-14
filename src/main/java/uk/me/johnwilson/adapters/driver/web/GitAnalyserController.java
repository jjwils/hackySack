package uk.me.johnwilson.adapters.driver.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GitAnalyserController {

    @GetMapping("/")
    public String analyseGit(){
        return "git";
    }

}
