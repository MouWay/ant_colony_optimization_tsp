package com.lidraw.ant_colony_optimization.Controller;

import com.lidraw.ant_colony_optimization.Model.ACORequest;
import com.lidraw.ant_colony_optimization.Model.ACOResponse;
import com.lidraw.ant_colony_optimization.Service.Interface.IACOService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ACO")
@AllArgsConstructor
public class ACOController {
    private IACOService service;

    @PostMapping("/solve")
    public ACOResponse solve(@RequestBody ACORequest request){
        return service.solve(request);
    }
}
