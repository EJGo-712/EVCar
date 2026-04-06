'use strict';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('inquiryCreateForm');
    const titleInput = document.getElementById('title');
    const contentInput = document.getElementById('content');
    const passwordInput = document.getElementById('password');

    if (!form) {
        return;
    }

    form.addEventListener('submit', (event) => {
        const title = titleInput.value.trim();
        const content = contentInput.value.trim();
        const password = passwordInput.value.trim();

        if (!title) {
            event.preventDefault();
            alert('문의 제목을 입력해주세요.');
            titleInput.focus();
            return;
        }

        if (!content) {
            event.preventDefault();
            alert('문의 내용을 입력해주세요.');
            contentInput.focus();
            return;
        }

        if (!password) {
            event.preventDefault();
            alert('비밀번호를 입력해주세요.');
            passwordInput.focus();
            return;
        }

        const confirmed = window.confirm('문의를 등록하시겠습니까?');
        if (!confirmed) {
            event.preventDefault();
        }
    });
});