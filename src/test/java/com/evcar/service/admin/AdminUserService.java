<<<<<<< HEAD:src/main/java/com/evcar/service/admin/AdminUserService.java
package com.evcar.service.admin;

import com.evcar.dto.admin.AdminUserDetailResponseDto;
import com.evcar.dto.admin.AdminUserListResponseDto;

import java.util.List;

public interface AdminUserService {

    List<AdminUserListResponseDto> getUserList(String status, String keyword, int page, int size);

    long getUserCount(String status, String keyword);

    AdminUserDetailResponseDto getUserDetail(String userId);
=======
package com.evcar.service.admin;

import com.evcar.dto.admin.AdminUserDetailResponseDto;
import com.evcar.dto.admin.AdminUserListResponseDto;

import java.util.List;

public interface AdminUserService {

    List<AdminUserListResponseDto> getUserList(String status, String keyword);

    AdminUserDetailResponseDto getUserDetail(String userId);
>>>>>>> e06a9eee787df2abec55b4d86412bd8d6084a4e8:src/test/java/com/evcar/service/admin/AdminUserService.java
}