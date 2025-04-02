package com.example.AverageCal.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AverageCal.Service.NumService;

@RestController
@RequestMapping("/numbers")
public class NumController {
	    
    @Autowired
    private NumService numService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getNumbers(@PathVariable String id) {
        if (!List.of("p", "e", "r").contains(id.toLowerCase())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid ID! Use 'p', 'e', or 'r'."));
        }
        return ResponseEntity.ok(Map.of("numbers", numService.processRequest(id)));
    }
}
