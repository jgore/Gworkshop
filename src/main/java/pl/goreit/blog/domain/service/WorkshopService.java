package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.workshop.AddWorkshopRequest;
import pl.goreit.api.generated.workshop.WorkshopView;

import java.util.List;

public interface WorkshopService {

    WorkshopView findByName(String name);

    WorkshopView add(AddWorkshopRequest addWorkshopRequest);

    List<WorkshopView> findAll();
}
