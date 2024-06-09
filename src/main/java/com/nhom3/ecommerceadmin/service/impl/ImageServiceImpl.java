package com.nhom3.ecommerceadmin.service.impl;

import com.nhom3.ecommerceadmin.models.Image;
import com.nhom3.ecommerceadmin.repository.ImageRepository;
import com.nhom3.ecommerceadmin.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;


    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
