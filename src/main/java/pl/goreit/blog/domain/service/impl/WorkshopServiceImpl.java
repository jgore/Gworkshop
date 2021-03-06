package pl.goreit.blog.domain.service.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.workshop.AddWorkshopRequest;
import pl.goreit.api.generated.workshop.WorkshopView;
import pl.goreit.blog.domain.model.Company;
import pl.goreit.blog.domain.model.Mechanic;
import pl.goreit.blog.domain.model.Workshop;
import pl.goreit.blog.domain.service.WorkshopService;
import pl.goreit.blog.infrastructure.mongo.WorkshopRepo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    @Autowired
    private WorkshopRepo workshopRepo;

    @Autowired
    private ConversionService conversionService;

    @Override
    public WorkshopView findByName(String name) {
        Workshop workshop = workshopRepo.findByName(name);
        return conversionService.convert(workshop, WorkshopView.class);
    }


    @Override
    public WorkshopView add(AddWorkshopRequest addWorkshopRequest) {
        Workshop workshop = new Workshop(
                UUID.randomUUID().toString(),
                addWorkshopRequest.getName(),
                addWorkshopRequest.getOwner(),
                conversionService.convert( addWorkshopRequest.getCompany(), Company.class ),
                Lists.newArrayList(),
                addWorkshopRequest.getMechanicList()
                        .stream()
                        .map(mechanic -> conversionService.convert(mechanic, Mechanic.class))
                        .collect(Collectors.toList()), null);
        Workshop persisted = workshopRepo.save(workshop);
        return conversionService.convert(persisted, WorkshopView.class);
    }

    @Override
    public List<WorkshopView> findAll() {
        List<Workshop> workshops = workshopRepo.findAll();

        return workshops.stream()
                .map(entity -> conversionService.convert(entity, WorkshopView.class)).
                        collect(Collectors.toList());
    }
}
