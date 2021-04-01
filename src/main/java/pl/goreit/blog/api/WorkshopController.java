package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.workshop.AddWorkshopRequest;
import pl.goreit.api.generated.workshop.WorkshopView;
import pl.goreit.blog.domain.service.WorkshopService;

import java.util.List;

@RestController
@RequestMapping("/workshops")
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @GetMapping()
    @ApiOperation(value = "pobiera warsztaty" )
    public List<WorkshopView> getAll() {
        return workshopService.findAll();
    }

    @GetMapping("/{title}")
    @ApiOperation(value = "pobiera jeden warsztat", notes = "pobiera 1 warsztat")
    public WorkshopView get(@PathVariable String name) {
        return workshopService.findByByName(name);
    }

    @PostMapping()
    @ApiOperation(value = "dodaje 1 warsztat")
    public WorkshopView add(@RequestBody AddWorkshopRequest addWorkshopRequest) {
        return workshopService.add(addWorkshopRequest);
    }
}
