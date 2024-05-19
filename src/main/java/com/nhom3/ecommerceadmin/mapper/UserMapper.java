package com.nhom3.ecommerceadmin.mapper;

import com.nhom3.ecommerceadmin.dto.UserDto;
import com.nhom3.ecommerceadmin.models.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto){
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .dateWork(userDto.getDatework())
                .phone(userDto.getPhone())
                .identification(userDto.getIdentification())
                .address(userDto.getAddress())
                .sales(userDto.getSales())
                .build();
        return user;
    }

    public static UserDto mapToUserDto(User user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .datework(user.getDateWork())
                .phone(user.getPhone())
                .identification(user.getIdentification())
                .address(user.getAddress())
                .sales(user.getSales())
                .build();
        return userDto;
    }
}
