package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.photo_album.PhotoAlbumModel;
import pl.goreit.blog.domain.converter.PhotoAlbumToPhotoAlbumModelConveter;
import pl.goreit.blog.domain.model.PhotoAlbum;
import pl.goreit.blog.domain.service.PhotoAlbumService;
import pl.goreit.blog.infrastructure.mongo.PhotoAlbumRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoAlbumServiceImpl implements PhotoAlbumService {

    @Autowired
    private PhotoAlbumRepo photoAlbumRepo;

    @Autowired
    private PhotoAlbumToPhotoAlbumModelConveter conveter;

    @Override
    public List<PhotoAlbumModel> findByUser(String userId) {
        return photoAlbumRepo.findByUserId(userId)
                .stream().map(photoAlbum -> conveter.convert(photoAlbum))
                .collect(Collectors.toList());
    }

    @Override
    public PhotoAlbumModel create(String name, List<List<Byte>> byteList) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return conveter.convert(photoAlbumRepo.save(new PhotoAlbum(userId, name, byteList)));
    }
}
