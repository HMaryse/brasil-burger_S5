package com.brasilburger.config.factory;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class AppFactory {

    private static Cloudinary cloudinary;

    
    public static Cloudinary getCloudinary() {
        if (cloudinary == null) {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "derru3bz9",
                "api_key",     "162132438588965",
                "api_secret",  "nIor2bZj0Su0Tvfhqz3eFFALVwY"
            ));
        }
        return cloudinary;
    }
}
