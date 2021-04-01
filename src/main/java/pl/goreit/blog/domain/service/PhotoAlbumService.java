package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.photo_album.PhotoAlbumModel;

import java.util.List;

public interface PhotoAlbumService {

    List<PhotoAlbumModel> findByUser(String userId);

    PhotoAlbumModel create(String name, List<List<Byte>> byteList);
}
