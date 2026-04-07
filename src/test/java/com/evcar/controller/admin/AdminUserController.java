<<<<<<< HEAD:src/main/java/com/evcar/controller/admin/AdminUserController.java
package com.evcar.controller.admin;

import com.evcar.dto.admin.AdminUserDetailResponseDto;
import com.evcar.dto.admin.AdminUserListResponseDto;
import com.evcar.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final int PAGE_BLOCK_SIZE = 10;

    private final AdminUserService adminUserService;

    @GetMapping("")
    public String userList(
            @RequestParam(name = "status", defaultValue = "ALL") String status,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "selectedUserId", required = false) String selectedUserId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ) {
        int currentPage = Math.max(page, DEFAULT_PAGE);

        List<AdminUserListResponseDto> userList =
                adminUserService.getUserList(status, keyword, currentPage, DEFAULT_SIZE);

        long totalCount = adminUserService.getUserCount(status, keyword);
        int totalPages = (int) Math.ceil((double) totalCount / DEFAULT_SIZE);

        if (totalPages == 0) {
            totalPages = 1;
        }

        if (currentPage > totalPages) {
            currentPage = totalPages;
            userList = adminUserService.getUserList(status, keyword, currentPage, DEFAULT_SIZE);
        }

        int startPage = ((currentPage - 1) / PAGE_BLOCK_SIZE) * PAGE_BLOCK_SIZE + 1;
        int endPage = Math.min(startPage + PAGE_BLOCK_SIZE - 1, totalPages);

        boolean hasPreviousBlock = startPage > 1;
        boolean hasNextBlock = endPage < totalPages;

        int previousBlockPage = Math.max(startPage - 1, 1);
        int nextBlockPage = Math.min(endPage + 1, totalPages);

        String resolvedSelectedUserId = selectedUserId;
        if (resolvedSelectedUserId == null && !userList.isEmpty()) {
            resolvedSelectedUserId = userList.get(0).getUserId();
        }

        AdminUserDetailResponseDto selectedUser = null;
        if (resolvedSelectedUserId != null) {
            selectedUser = adminUserService.getUserDetail(resolvedSelectedUserId);
        }

        model.addAttribute("userList", userList);
        model.addAttribute("selectedUser", selectedUser);
        model.addAttribute("selectedUserId", resolvedSelectedUserId);

        model.addAttribute("currentStatus", status);
        model.addAttribute("currentKeyword", keyword);
        model.addAttribute("currentPage", currentPage);

        model.addAttribute("pageSize", DEFAULT_SIZE);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("hasPreviousBlock", hasPreviousBlock);
        model.addAttribute("hasNextBlock", hasNextBlock);
        model.addAttribute("previousBlockPage", previousBlockPage);
        model.addAttribute("nextBlockPage", nextBlockPage);

        return "admin/user/list";
    }
=======
package com.evcar.controller.admin;

import com.evcar.dto.admin.AdminUserDetailResponseDto;
import com.evcar.dto.admin.AdminUserListResponseDto;
import com.evcar.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("")
    public String userList(
            @RequestParam(name = "status", defaultValue = "ALL") String status,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "selectedUserId", required = false) String selectedUserId,
            Model model
    ) {
        List<AdminUserListResponseDto> userList = adminUserService.getUserList(status, keyword);

        String resolvedSelectedUserId = selectedUserId;
        if (resolvedSelectedUserId == null && !userList.isEmpty()) {
            resolvedSelectedUserId = userList.get(0).getUserId();
        }

        AdminUserDetailResponseDto selectedUser = null;
        if (resolvedSelectedUserId != null) {
            selectedUser = adminUserService.getUserDetail(resolvedSelectedUserId);
        }

        model.addAttribute("userList", userList);
        model.addAttribute("selectedUser", selectedUser);
        model.addAttribute("currentStatus", status);
        model.addAttribute("currentKeyword", keyword);
        model.addAttribute("selectedUserId", resolvedSelectedUserId);

        return "admin/user/list";
    }
>>>>>>> e06a9eee787df2abec55b4d86412bd8d6084a4e8:src/test/java/com/evcar/controller/admin/AdminUserController.java
}