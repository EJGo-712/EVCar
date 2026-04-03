<<<<<<< HEAD:src/main/java/com/evcar/repository/admin/AdminUserQueryRepository.java
package com.evcar.repository.admin;

import com.evcar.dto.admin.AdminUserDetailResponseDto;
import com.evcar.dto.admin.AdminUserListResponseDto;

import java.util.List;

public interface AdminUserQueryRepository {

    List<AdminUserListResponseDto> findUserList(String status, String keyword, int offset, int size);

    long countUserList(String status, String keyword);

    AdminUserDetailResponseDto findUserDetail(String userId);
=======
package com.evcar.repository.admin;

import com.evcar.dto.admin.AdminUserDetailResponseDto;
import com.evcar.dto.admin.AdminUserListResponseDto;

import java.util.List;

public interface AdminUserQueryRepository {

    List<AdminUserListResponseDto> findUserList(String status, String keyword);

    AdminUserDetailResponseDto findUserDetail(String userId);
>>>>>>> e06a9eee787df2abec55b4d86412bd8d6084a4e8:src/test/java/com/evcar/repository/admin/AdminUserQueryRepository.java
}