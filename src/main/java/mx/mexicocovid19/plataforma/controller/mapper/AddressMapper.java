package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.AddressDTO;
import mx.mexicocovid19.plataforma.model.entity.GeoLocation;
import mx.mexicocovid19.plataforma.model.entity.Municipality;
import mx.mexicocovid19.plataforma.model.entity.Province;

public class AddressMapper {
    public static AddressDTO from(GeoLocation location) {
        final AddressDTO address = new AddressDTO();
        address.setId(location.getId());
        address.setCalle(location.getCalle());
        address.setDireccion(location.getDireccion());
        address.setNoExterior(location.getNoExterior());
        address.setNoInterior(location.getNoInterior());
        address.setLatitude(location.getLatitude());
        address.setLongitude(location.getLongitude());
        address.setColonia(location.getColonia());
        address.setCodigoPostal(location.getCodigoPostal());
        if (location.getMunicipality() != null) {
            address.setIdMunicipio(location.getMunicipality().getId());
            address.setMunicipio(location.getMunicipality().getNombre());
            address.setIdEstado(location.getMunicipality().getProvince().getId());
            address.setEstado(location.getMunicipality().getProvince().getNombre());
        }
        return address;
    }

    public static GeoLocation from(AddressDTO address) {
        final GeoLocation location = new GeoLocation();
        location.setId(address.getId() < 1 ? null : address.getId());
        location.setCalle(address.getCalle());
        location.setDireccion(address.getDireccion());
        location.setNoExterior(address.getNoExterior());
        location.setNoInterior(address.getNoInterior());
        location.setLatitude(address.getLatitude());
        location.setLongitude(address.getLongitude());
        location.setColonia(address.getColonia());
        location.setCodigoPostal(address.getCodigoPostal());
        if (address.getIdEstado() > 0 && address.getIdMunicipio() > 0){
            final Province province = new Province();
            province.setId(address.getIdEstado());
            final Municipality municipality = new Municipality();
            municipality.setId(address.getIdMunicipio());
            municipality.setProvince(province);
            location.setMunicipality(municipality);
        }
        return location;
    }
}
