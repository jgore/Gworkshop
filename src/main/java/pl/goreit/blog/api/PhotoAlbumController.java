package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.goreit.api.generated.photo_album.PhotoAlbumModel;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.model.PhotoAlbum;
import pl.goreit.blog.domain.service.PhotoAlbumService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotoAlbumController {

    @Autowired
    private PhotoAlbumService photoService;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    @ApiOperation(value = "add new photo album")
    public PhotoAlbumModel AddPhotoAlbum(@RequestParam("name") String name,
                                         @RequestParam("files") MultipartFile[] files) throws DomainException, IOException {

        //FIXME -- move to converter
        List<List<Byte>> list = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            byte[] bytes = multipartFile.getBytes();
            List<Byte> byteList = new ArrayList<>();
            for (byte aByte : bytes) {
                byteList.add(aByte);
            }
            list.add(byteList);
        }
         return photoService.create(name, list);

    }

    @GetMapping()
    @ApiOperation(value = "find all by userId")
    public List<PhotoAlbumModel> getAllByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return photoService.findByUser(userId);
    }

}
