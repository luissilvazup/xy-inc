package br.com.zup.api.facade;

import br.com.zup.api.dto.POIDTO;
import br.com.zup.api.entity.POI;
import br.com.zup.api.service.IPOIService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class POIFacade implements IPOIFacade {

    private final IPOIService service;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<POIDTO> listAll() {
        return service.listAll().stream().map(poi -> convertToDTO(poi)).collect(Collectors.toList());
    }

    @Override
    public POIDTO create(POIDTO dto) {
        POI newPoi = service.create(convertToEntity(dto));
        return convertToDTO(newPoi);
    }

    @Override
    public List<POIDTO> listAllByReferencePoint(int x, int y, double maxDistance) {
        if(maxDistance < 0){
            throw new IllegalArgumentException("the maxDistance must be a non negative number");
        }

        return service.listAllByReferencePoint(new POIDTO(x, y), maxDistance).stream()
                .map(poi -> convertToDTO(poi))
                .collect(Collectors.toList());
    }


    private POIDTO convertToDTO(POI entity){
        return modelMapper.map(entity, POIDTO.class);
    }

    private POI convertToEntity(POIDTO dto) {
        return modelMapper.map(dto, POI.class);
    }
}
