package com.songify;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongsViewController {

    private Map<Integer, String> database = new HashMap<>();

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/view/songs")
    public String songs(Model model){
        database.put(1, "Shawn Menes song");
        database.put(2, "Rihiana kap kap");
        database.put(3, "Shawn Menes song231");
        database.put(4, "Rihiana kap kap231231");

        model.addAttribute("songMap", database);
        return "songs";
    }
}
