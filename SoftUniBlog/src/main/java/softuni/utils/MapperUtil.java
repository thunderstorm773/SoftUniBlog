package softuni.utils;

import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;

public class MapperUtil {

    private static MapperUtil mapperUtil;

    private ModelMapper modelMapper;

    private MapperUtil() {
        this.modelMapper = new ModelMapper();
    }

    public static MapperUtil getInstance() {
        if (mapperUtil == null) {
            mapperUtil = new MapperUtil();
        }

        return mapperUtil;
    }

    public ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    public <S, D> List<D> convertAll(List<S> source, Class<D> destination) {
        List<D> convertedItems = new ArrayList<>();
        for (S s : source) {
            D convertedItem = this.getModelMapper().map(s, destination);
            convertedItems.add(convertedItem);
        }

        return convertedItems;
    }
}
