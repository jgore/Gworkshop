package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.workshop.AddWorkshopRequest;
import pl.goreit.api.generated.workshop.WorkshopView;
import pl.goreit.blog.domain.model.Workshop;
import pl.goreit.blog.domain.service.WorkshopService;
import pl.goreit.blog.infrastructure.mongo.WorkshopRepo;

import java.util.List;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    @Autowired
    private WorkshopRepo workshopRepo;

    @Override
    public WorkshopView findByByName(String name) {
        Workshop workshop = workshopRepo.findByName(name);

        return null;
    }

    @Override
    public WorkshopView add(AddWorkshopRequest addWorkshopRequest) {
        return null;
    }

    @Override
    public List<WorkshopView> findAll() {
        return null;
    }
}
