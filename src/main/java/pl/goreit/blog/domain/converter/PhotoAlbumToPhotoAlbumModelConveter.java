package pl.goreit.blog.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.photo_album.PhotoAlbumModel;
import pl.goreit.blog.domain.model.PhotoAlbum;

@Component
public class PhotoAlbumToPhotoAlbumModelConveter implements Converter<PhotoAlbum, PhotoAlbumModel> {

    @Override
    public PhotoAlbumModel convert(PhotoAlbum photoAlbum) {
        return new PhotoAlbumModel(photoAlbum.getName(), photoAlbum.getPhotos());
    }
}
